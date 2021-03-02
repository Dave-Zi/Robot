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
    private static Map<String, IGroveSensorSetWrapper> SensorSetMap = new HashMap<>();

    private static Map<String, IGroveSensorGetWrapper> SensorGetMap = new HashMap<>();

    private static Map<String, Ev3Board> Ev3Map = new HashMap<>();


    public static void main(String[] args) throws IOException {
        ReadJsonToDictionaries("./classes/Robot.json");
        //your stuff here
        IBoard grove = new GrovePiBoard(SensorGetMap, SensorSetMap);

        Map.Entry<String, Ev3Board> ev = Ev3Map.entrySet().iterator().next();
        Ev3Board ev3B = ev.getValue();
//        System.out.println(ev.getKey());
        while (true) {

            ev3B.drive("A", 0);
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
                                SensorSetMap.put(sensorData.getKey(), new LedWrapper(grovePi, portNumber));
                                continue;

                            case "Ultrasonic":
                                SensorGetMap.put(sensorData.getKey(), new UltrasonicWrapper(grovePi, portNumber));
                                continue;

                            case "Sound":
                                SensorGetMap.put(sensorData.getKey(), new SoundWrapper(grovePi, portNumber));
                                continue;

                            case "Button":
                                SensorGetMap.put(sensorData.getKey(), new ButtonWrapper(grovePi, portNumber));
                                continue;

                            case "Rotary":
                                SensorGetMap.put(sensorData.getKey(), new RotaryWrapper(grovePi, portNumber));
                                continue;

                            case "Relay":
                                SensorSetMap.put(sensorData.getKey(), new RelayWrapper(grovePi, portNumber));
                                continue;

                            case "Light":
                                SensorGetMap.put(sensorData.getKey(), new LightWrapper(grovePi, portNumber));
                                continue;

                            case "Buzzer":
                                SensorSetMap.put(sensorData.getKey(), new BuzzerWrapper(grovePi, portNumber));
                                continue;

                        }
                        if (sensorData.getValue().length() == "Temperature ".length() &&
                                sensorData.getValue().substring(0, "Temperature ".length()).equals("Temperature ")) {

                            switch (sensorData.getValue().substring("Temperature ".length())) {
                                case "DHT11":
                                    SensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(grovePi, portNumber, GroveTemperatureAndHumiditySensor.Type.DHT11));
                                    continue;

                                case "DHT21":
                                    SensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(grovePi, portNumber, GroveTemperatureAndHumiditySensor.Type.DHT21));
                                    continue;

                                case "DHT22":
                                    SensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(grovePi, portNumber, GroveTemperatureAndHumiditySensor.Type.DHT22));
                                    continue;

                                case "AM2301":
                                    SensorGetMap.put(sensorData.getKey(), new TemperatureWrapper(grovePi, portNumber, GroveTemperatureAndHumiditySensor.Type.AM2301));
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





