import Boards.GrovePiBoard;
import Boards.IBoard;
import Enums.Ev3DrivePort;
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
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Map<String, IGroveSensorSetWrapper> SensorSetMap = new HashMap<>();

    private static final Map<String, IGroveSensorGetWrapper> SensorGetMap = new HashMap<>();

    private static final Map<String, Ev3Board> Ev3Map = new HashMap<>();


    public static void main(String[] args) throws IOException {
        ReadJsonToDictionaries("./classes/Robot.json");
        //your stuff here
        IBoard grove = new GrovePiBoard(SensorGetMap, SensorSetMap);

        Map.Entry<String, Ev3Board> ev = Ev3Map.entrySet().iterator().next();
        Ev3Board ev3B = ev.getValue();
//        System.out.println(ev.getKey());
        while (true) {
            ev3B.drive(Ev3DrivePort.A, 0);
        }


//        //end program forcefully
//        EV3 ev3 = new EV3("rfcomm0");
//        System.exit(0);
    }

    private static void ReadJsonToDictionaries(String path) throws IOException {
        InputStream inputStream = new FileInputStream(path);
        byte[] data = inputStream.readAllBytes();
        String jsonString = new String(data);

        Map<String, Map<String, String>> retMap = new Gson()
                .fromJson(
                        jsonString, new TypeToken<HashMap<String, HashMap<String, String>>>() {
                        }.getType()
                );

        GrovePi grovePi = new GrovePi4J();
        for (Map.Entry<String, Map<String, String>> entry : retMap.entrySet()) {

            switch (entry.getKey()) {
                case "GrovePi":
                    Map<String, String> entryVal = entry.getValue();
                    for (Map.Entry<String, String> sensorData : entryVal.entrySet()) {
                        int portNumber = Integer.valueOf(sensorData.getKey().substring(1));
                        switch (sensorData.getValue()) {
                            case "Led":
                                SensorSetMap.put(sensorData.getKey(), new LedWrapper(new GroveLed(grovePi, portNumber), portNumber));
                                continue;

                            case "Ultrasonic":
                                SensorGetMap.put(sensorData.getKey(), new UltrasonicWrapper(new GroveUltrasonicRanger(grovePi, portNumber), portNumber));
                                continue;

                            case "Sound":
                                SensorGetMap.put(sensorData.getKey(), new SoundWrapper(new GroveSoundSensor(grovePi, portNumber), portNumber));
                                continue;

                            case "Button":
                                SensorGetMap.put(sensorData.getKey(), new ButtonWrapper(grovePi.getDigitalIn(portNumber), portNumber));
                                continue;

                            case "Rotary":
                                SensorGetMap.put(sensorData.getKey(), new RotaryWrapper(new GroveRotarySensor(grovePi, portNumber), portNumber));
                                continue;

                            case "Relay":
                                SensorSetMap.put(sensorData.getKey(), new RelayWrapper(new GroveRelay(grovePi, portNumber), portNumber));
                                continue;

                            case "Light":
                                SensorGetMap.put(sensorData.getKey(), new LightWrapper(new GroveLightSensor(grovePi, portNumber), portNumber));
                                continue;

                            case "Buzzer":
                                SensorSetMap.put(sensorData.getKey(), new BuzzerWrapper(new GroveDigitalOut(grovePi, portNumber), portNumber));
                                continue;

                        }
                        if (sensorData.getValue().length() == "Temperature ".length() &&
                                sensorData.getValue().substring(0, "Temperature ".length()).equals("Temperature ")) {

                            GroveTemperatureAndHumiditySensor.Type dhtType;
                            switch (sensorData.getValue().substring("Temperature ".length())) {
                                case "DHT11":
                                    dhtType = GroveTemperatureAndHumiditySensor.Type.DHT11;
                                    SensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(new GroveTemperatureAndHumiditySensor(grovePi, portNumber, dhtType), portNumber, dhtType));
                                    continue;

                                case "DHT21":
                                    dhtType = GroveTemperatureAndHumiditySensor.Type.DHT21;
                                    SensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(new GroveTemperatureAndHumiditySensor(grovePi, portNumber, dhtType), portNumber, dhtType));
                                    continue;

                                case "DHT22":
                                    dhtType = GroveTemperatureAndHumiditySensor.Type.DHT22;
                                    SensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(new GroveTemperatureAndHumiditySensor(grovePi, portNumber, dhtType), portNumber, dhtType));
                                    continue;

                                case "AM2301":
                                    dhtType = GroveTemperatureAndHumiditySensor.Type.AM2301;
                                    SensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(new GroveTemperatureAndHumiditySensor(grovePi, portNumber, dhtType), portNumber, dhtType));
                            }
                        }
                    }
                    continue;
                case "EV3":
                    Map<String, String> Ev3Val = entry.getValue();
                    for (Map.Entry<String, String> Ev3Data : Ev3Val.entrySet()) {
                        String port = Ev3Data.getValue();
                        Ev3Map.put(port, new Ev3Board(port));
                    }
            }
        }
    }
}
