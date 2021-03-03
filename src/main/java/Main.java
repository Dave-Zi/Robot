import Enums.BoardTypeEnum;
import Enums.Ev3DrivePort;
import GroveWrappers.GetWrappers.*;
import GroveWrappers.SetWrappers.BuzzerWrapper;
import GroveWrappers.SetWrappers.IGroveSensorSetWrapper;
import GroveWrappers.SetWrappers.LedWrapper;
import GroveWrappers.SetWrappers.RelayWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor;
import org.iot.raspberry.grovepi.pi4j.GrovePi4J;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        HashMap<BoardTypeEnum, List<IBoard>> boards = JsonToRobot("./classes/Robot.json");
        Ev3Board ev3B = (Ev3Board) boards.get(BoardTypeEnum.EV3).get(0);
        ev3B.drive(Ev3DrivePort.A, 100);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ev3B.drive(Ev3DrivePort.A, 0);
    }

    /**
     * reads a json file with the existing boards and their sensors.
     * @param path of the json file
     * @return HashMap of all the boards
     * @throws IOException in case of IO problem when reading the json file
     */
    public static HashMap<BoardTypeEnum, List<IBoard>> JsonToRobot(String path) throws IOException {
        InputStream inputStream = new FileInputStream(path);
        byte[] data = inputStream.readAllBytes();
        String jsonString = new String(data);

        Map<String, Map<String, String>> retMap = new Gson()
                .fromJson(
                        jsonString, new TypeToken<HashMap<String, HashMap<String, String>>>() {
                        }.getType()
                );

        HashMap<BoardTypeEnum, List<IBoard>> boards = new HashMap<>();
        GrovePi grovePi = new GrovePi4J();
        for (Map.Entry<String, Map<String, String>> entry : retMap.entrySet()) {

            switch (entry.getKey()) {
                case "GrovePi":
                    GrovePiBoard newGrovePiBoard = parseGrovePi(grovePi, entry.getValue());
                    if (!boards.containsKey(BoardTypeEnum.GrovePi)) {
                        boards.put(BoardTypeEnum.GrovePi, new ArrayList<>());
                    }

                    boards.get(BoardTypeEnum.GrovePi).add(newGrovePiBoard);
                    continue;
                case "EV3":
                    Map<String, String> Ev3Val = entry.getValue();
                    for (Map.Entry<String, String> Ev3Data : Ev3Val.entrySet()) {
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
        return boards;
    }

    private static GrovePiBoard parseGrovePi(GrovePi grovePi, Map<String, String> entryVal) throws IOException {
        Map<String, IGroveSensorSetWrapper> sensorSetMap = new HashMap<>();

        Map<String, IGroveSensorGetWrapper> sensorGetMap = new HashMap<>();

        for (Map.Entry<String, String> sensorData : entryVal.entrySet()) {
            int portNumber = Integer.valueOf(sensorData.getKey().substring(1));
            switch (sensorData.getValue()) {
                case "Led":
                    sensorSetMap.put(sensorData.getKey(), new LedWrapper(grovePi, portNumber));
                    continue;

                case "Ultrasonic":
                    sensorGetMap.put(sensorData.getKey(), new UltrasonicWrapper(grovePi, portNumber));
                    continue;

                case "Sound":
                    sensorGetMap.put(sensorData.getKey(), new SoundWrapper(grovePi, portNumber));
                    continue;

                case "Button":
                    sensorGetMap.put(sensorData.getKey(), new ButtonWrapper(grovePi, portNumber));
                    continue;

                case "Rotary":
                    sensorGetMap.put(sensorData.getKey(), new RotaryWrapper(grovePi, portNumber));
                    continue;

                case "Relay":
                    sensorSetMap.put(sensorData.getKey(), new RelayWrapper(grovePi, portNumber));
                    continue;

                case "Light":
                    sensorGetMap.put(sensorData.getKey(), new LightWrapper(grovePi, portNumber));
                    continue;

                case "Buzzer":
                    sensorSetMap.put(sensorData.getKey(), new BuzzerWrapper(grovePi, portNumber));
                    continue;

            }
            if (sensorData.getValue().length() == "Temperature ".length() &&
                    sensorData.getValue().substring(0, "Temperature ".length()).equals("Temperature ")) {

                switch (sensorData.getValue().substring("Temperature ".length())) {
                    case "DHT11":
                        sensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(grovePi, portNumber, GroveTemperatureAndHumiditySensor.Type.DHT11));
                        continue;

                    case "DHT21":
                        sensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(grovePi, portNumber, GroveTemperatureAndHumiditySensor.Type.DHT21));
                        continue;

                    case "DHT22":
                        sensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(grovePi, portNumber, GroveTemperatureAndHumiditySensor.Type.DHT22));
                        continue;

                    case "AM2301":
                        sensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(grovePi, portNumber, GroveTemperatureAndHumiditySensor.Type.AM2301));
                }
            }
        }
        return new GrovePiBoard(sensorGetMap, sensorSetMap);
    }
}





