import Enums.BoardTypeEnum;
import GroveWrappers.GetWrappers.*;
import GroveWrappers.SetWrappers.BuzzerWrapper;
import GroveWrappers.SetWrappers.IGroveSensorSetWrapper;
import GroveWrappers.SetWrappers.LedWrapper;
import GroveWrappers.SetWrappers.RelayWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.*;
import org.iot.raspberry.grovepi.pi4j.GrovePi4J;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Robot {

//    public static void main(String[] args) throws IOException, InterruptedException {
//        HashMap<BoardTypeEnum, List<IBoard>> boards = JsonToRobot("./classes/Robot.json");
//        Ev3Board ev3B = (Ev3Board) boards.get(BoardTypeEnum.EV3).get(0);
//        GrovePiBoard grovePi = (GrovePiBoard) boards.get(BoardTypeEnum.GrovePi).get(0);
//
//        Map<IEv3Port, Double> stop = Map.of();
//        Map<IEv3Port, Double> forward = Map.of(
//                Ev3DrivePort.B, 35.0,
//                Ev3DrivePort.C, 35.0
//        );
//
//        Map<IEv3Port, Double> turn = Map.of(
//                Ev3DrivePort.B, 25.0
//        );
//
//
//        int count = 0;
//        while (count < 2) {
//            ev3B.drive(forward);
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
//            ev3B.drive(stop);
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
//    }

    /**
     * reads a json file with the existing boards and their sensors.
     *
     * @param path of the json file
     * @return HashMap of all the boards from the json
     * @throws IOException in case of IO problem when reading the json file
     */
    public static HashMap<BoardTypeEnum, List<IBoard>> JsonToRobot(String path) throws IOException {
        InputStream inputStream = new FileInputStream(path);
        byte[] data = inputStream.readAllBytes();
        String jsonString = new String(data);

        Map<String, Map<String, String>[]> retMap = new Gson()
                .fromJson(
                        jsonString, new TypeToken<HashMap<String, HashMap<String, String>[]>>() {
                        }.getType()
                );

        HashMap<BoardTypeEnum, List<IBoard>> boards = new HashMap<>();
        GrovePi grovePi = new GrovePi4J();
        for (Map.Entry<String, Map<String, String>[]> entry : retMap.entrySet()) {
            switch (entry.getKey()) {
                case "GrovePi":
                    for (Map<String, String> grove_entry : entry.getValue()) {
                        GrovePiBoard newGrovePiBoard = parseGrovePi(grovePi, grove_entry);
                        if (!boards.containsKey(BoardTypeEnum.GrovePi)) {
                            boards.put(BoardTypeEnum.GrovePi, new ArrayList<>());
                        }

                        boards.get(BoardTypeEnum.GrovePi).add(newGrovePiBoard);
                    }
                    continue;
                case "EV3":
                    for (Map<String, String> ev3_entry : entry.getValue()) {

                        for (Map.Entry<String, String> Ev3Data : ev3_entry.entrySet()) {
                            String port = Ev3Data.getValue();
//                        Ev3Map.put(port, new Ev3Board(port));
                            EV3 ev3 = new EV3(port);
                            Ev3Board newEv3Board = new Ev3Board(ev3);
                            if (!boards.containsKey(BoardTypeEnum.EV3)) {
                                boards.put(BoardTypeEnum.EV3, new ArrayList<>());
                            }

                            boards.get(BoardTypeEnum.EV3).add(newEv3Board);

                        }
                    }


            }
        }
        return boards;
    }

    private static GrovePiBoard parseGrovePi(GrovePi grovePi, Map<String, String> entryVal) throws IOException {
        Map<String, IGroveSensorSetWrapper> sensorSetMap = new HashMap<>();

        Map<String, IGroveSensorGetWrapper> sensorGetMap = new HashMap<>();

        for (Map.Entry<String, String> sensorData : entryVal.entrySet()) {
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
}





