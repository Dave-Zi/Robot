import Enums.BoardTypeEnum;
import GroveWrappers.GetWrappers.*;
import GroveWrappers.SetWrappers.BuzzerWrapper;
import GroveWrappers.SetWrappers.IGroveSensorSetWrapper;
import GroveWrappers.SetWrappers.LedWrapper;
import GroveWrappers.SetWrappers.RelayWrapper;
import com.google.gson.Gson;
import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.*;
import org.iot.raspberry.grovepi.pi4j.GrovePi4J;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Robot {

//    public static void main(String[] args) throws IOException, InterruptedException {
//        InputStream inputStream = new FileInputStream("./classes/Robot.json");
//        byte[] data = inputStream.readAllBytes();
//        String jsonString = new String(data);
//        Ev3Board ev3B = (Ev3Board) boards.get(BoardTypeEnum.EV3).get(0);
//        GrovePiBoard grovePi = (GrovePiBoard) boards.get(BoardTypeEnum.GrovePi).get(0);

//
//        List<DriveDataObject> stop = null;
//        List<DriveDataObject> forward =
//                Arrays.asList(new DriveDataObject[]{new DriveDataObject(Ev3DrivePort.B, 35.0, 0),
//                        new DriveDataObject(Ev3DrivePort.C, 35.0, 0)});


//
//        Map<IEv3Port, Double> turn = Map.of(
//                Ev3DrivePort.B, 25.0
//        );
//
//        int count = 0;
//        while (count < 2) {
    // ev3B.drive(forward);
//            grovePi.setSensorData(GrovePiPort.D2, true);
//            grovePi.setSensorData(GrovePiPort.D8, false);
//
//            Double ev3Distance = ev3B.getDoubleSensorData(Ev3SensorPort._2, 0);
//            double groveDistance = grovePi.getDoubleSensorData(GrovePiPort.D4, 0);
//
//            System.out.println("Driving straight!");
//            while (ev3Distance == null || (ev3Distance > 30 && groveDistance > 20)) {
//                ev3Distance = ev3B.getDoubleSensorData(Ev3SensorPort._2, 0);
//                groveDistance = grovePi.getDoubleSensorData(GrovePiPort.D4, 0);
//            }
//            System.out.println("Stopping");
//
    //   Thread.sleep(4000);
    //     ev3B.drive(stop);
//            grovePi.setSensorData(GrovePiPort.D2, false);
//            grovePi.setSensorData(GrovePiPort.D8, true);
//
//            while (groveDistance > 20) {
//                groveDistance = grovePi.getDoubleSensorData(GrovePiPort.D4, 0);
//            }
//            System.out.println("Turning!");
//
//
//            ev3B.drive(turn);
//            Thread.sleep(4000);
//            ev3B.drive(stop);
//            grovePi.setSensorData(GrovePiPort.D8, false);
//
//            count++;
//        }
//        ev3B.drive(stop);
//        grovePi.setSensorData(GrovePiPort.D2, false);
//        grovePi.setSensorData(GrovePiPort.D8, false);
//        System.out.println("End!");
//
//       }

    private static IParser ev3Parser = Robot::ev3Parser;
    private static IParser grovePiParser = Robot::grovePiParser;

    private static Map<String, IParser> boardToParser = Stream.of(new Object[][]{
            {"EV3", ev3Parser},
            {"GrovePi", grovePiParser}

    }).collect(Collectors.toMap(data -> (String) data[0], data -> (IParser) data[1]));

    /**
     * reads a json file with the existing boards and their sensors.
     *
     * @param jsonString of the json file
     * @return HashMap of all the boards from the json
     */
    public static Map<BoardTypeEnum, Map<Integer, IBoard>> JsonToRobot(String jsonString) {

        Map<BoardTypeEnum, Map<Integer, IBoard>> retMap = new HashMap<>();
        Gson gson = new Gson();
        Map element = gson.fromJson(jsonString, Map.class); // json String to Map

        for (Object key : element.keySet()) { // Iterate over board types

            String boardName = (String) key;
            Object value = element.get(key);

            @SuppressWarnings("unchecked")
            ArrayList<Map<String, String>> boardConfigsList = (ArrayList<Map<String, String>>) value;
            Map<Integer, IBoard> boardsMap = new HashMap<>();

            for (int i = 0; i < boardConfigsList.size(); i++) {
                Map<String, String> config = boardConfigsList.get(i);
                IBoard configsBoard;
                try {
                    configsBoard = boardToParser.get(boardName).executeParser(config);
                } catch (IOException e) {
                    System.out.println("Failed to initiate board " + boardName + " configs number " + (i + 1));
                    continue;
                }
                boardsMap.put(i + 1, configsBoard);
            } // Go over each config and create a board from it.

            retMap.put(BoardTypeEnum.valueOf(boardName), boardsMap); // Add board type to map
        }
        return retMap;
    }

    private static Ev3Board ev3Parser(Map<String, String> config) {
        String port = config.get("Port");
        EV3 ev3 = new EV3(port);
        return new Ev3Board(ev3);
    }

    private static GrovePiBoard grovePiParser(Map<String, String> config) throws IOException {
        GrovePi grovePi = new GrovePi4J();

        Map<String, IGroveSensorSetWrapper> sensorSetMap = new HashMap<>();
        Map<String, IGroveSensorGetWrapper> sensorGetMap = new HashMap<>();

        for (Map.Entry<String, String> sensorData : config.entrySet()) {
            int portNumber = Integer.valueOf(sensorData.getKey().substring(1));
            switch (sensorData.getValue()) {
                case "Led":
                    sensorSetMap.put(sensorData.getKey(), new LedWrapper(new GroveLed(grovePi, portNumber)));
                    continue;

                case "Ultrasonic":
                    sensorGetMap.put(sensorData.getKey(), new UltrasonicWrapper(new GroveUltrasonicRanger(grovePi, portNumber)));
                    continue;

                case "Sound":
                    sensorGetMap.put(sensorData.getKey(), new SoundWrapper(new GroveSoundSensor(grovePi, portNumber)));
                    continue;

                case "Button":
                    sensorGetMap.put(sensorData.getKey(), new ButtonWrapper(grovePi.getDigitalIn(portNumber)));
                    continue;

                case "Rotary":
                    sensorGetMap.put(sensorData.getKey(), new RotaryWrapper(new GroveRotarySensor(grovePi, portNumber)));
                    continue;

                case "Relay":
                    sensorSetMap.put(sensorData.getKey(), new RelayWrapper(new GroveRelay(grovePi, portNumber)));
                    continue;

                case "Light":
                    sensorGetMap.put(sensorData.getKey(), new LightWrapper(new GroveLightSensor(grovePi, portNumber)));
                    continue;

                case "Buzzer":
                    sensorSetMap.put(sensorData.getKey(), new BuzzerWrapper(new GroveDigitalOut(grovePi, portNumber)));
                    continue;

            }
            if (sensorData.getValue().length() == "Temperature ".length() &&
                    sensorData.getValue().substring(0, "Temperature ".length()).equals("Temperature ")) {

                GroveTemperatureAndHumiditySensor.Type dhtType;
                switch (sensorData.getValue().substring("Temperature ".length())) {
                    case "DHT11":
                        dhtType = GroveTemperatureAndHumiditySensor.Type.DHT11;
                        sensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(new GroveTemperatureAndHumiditySensor(grovePi, portNumber, dhtType)));
                        continue;

                    case "DHT21":
                        dhtType = GroveTemperatureAndHumiditySensor.Type.DHT21;
                        sensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(new GroveTemperatureAndHumiditySensor(grovePi, portNumber, dhtType)));
                        continue;

                    case "DHT22":
                        dhtType = GroveTemperatureAndHumiditySensor.Type.DHT22;
                        sensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(new GroveTemperatureAndHumiditySensor(grovePi, portNumber, dhtType)));
                        continue;

                    case "AM2301":
                        dhtType = GroveTemperatureAndHumiditySensor.Type.AM2301;
                        sensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(new GroveTemperatureAndHumiditySensor(grovePi, portNumber, dhtType)));
                }
            }
        }
        return new GrovePiBoard(sensorGetMap, sensorSetMap);
    }

    /**
     * Uniform Interface for Board Parser
     */
    @FunctionalInterface
    public interface IParser {
        IBoard executeParser(Map<String, String> config) throws IOException;
    }
}