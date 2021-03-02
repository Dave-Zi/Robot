package Boards;

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
import java.util.logging.Logger;

public class GrovePiBoard extends GrovePi4J implements IBoard {

    private Logger logger = Logger.getLogger(GrovePiBoard.class.getName());

    private Map<String, IGroveSensorSetWrapper> SensorSetMap;

    private Map<String, IGroveSensorGetWrapper> SensorGetMap;

    GrovePiBoard(Map<String, IGroveSensorGetWrapper> sensorGetMap, Map<String, IGroveSensorSetWrapper> sensorSetMap) throws IOException {
        super();
        SensorGetMap = sensorGetMap;
        SensorSetMap = sensorSetMap;
    }

    @Override
    public Boolean getBooleanSensorData(String port, int mode) {
        if (isPortIllegal(port)) {
            logger.severe("Illegal port index");
            return null;
        }

        return SensorGetMap.get(port).get(mode) > 0.0;
    }

    @Override
    public Double getDoubleSensorData(String port, int mode) {
        if (isPortIllegal(port)) {
            logger.severe("Illegal port index");
            return null;
        }
        return SensorGetMap.get(port).get(mode);
    }

    @Override
    public void setSensorData(String port, boolean value) {
        if (isPortIllegal(port)) {
            logger.severe("Illegal port index");
            return;
        }
        SensorSetMap.get(port).set(value);
    }

    @Override
    public void drive(String port, double speed) {

    }

    @Override
    public void disconnect() {

    }

    private boolean isPortIllegal(String port) {
        if (port.length() != 2) {
            return true;
        }
        char portChar = port.charAt(0);

        if (portChar < 'A' || portChar > 'D') {
            return true;
        }
        int portNumber = Integer.valueOf(port.substring(1));

        return portNumber < 0 || portNumber > 8;
    }

    private void ReadJsonToDictionaries(String path) throws IOException {
        InputStream inputStream = new FileInputStream(path);
        byte[] data = inputStream.readAllBytes();
        String jsonString = new String(data);

        Map<String, String> retMap = new Gson()
                .fromJson(
                        jsonString, new TypeToken<HashMap<String, String>>() {}.getType()
                );


        GrovePi grovePi = new GrovePi4J();
        for( Map.Entry<String, String> entry : retMap.entrySet() )
        {
            int portNumber = Integer.valueOf(entry.getKey().substring(1));
            switch (entry.getValue()){
                case "Led":
                    SensorSetMap.put(entry.getKey(), new LedWrapper(new GroveLed(grovePi, portNumber), portNumber));
                    continue;

                case "Ultrasonic":
                    SensorGetMap.put(entry.getKey(), new UltrasonicWrapper(new GroveUltrasonicRanger(grovePi, portNumber), portNumber));
                    continue;

                case "Sound":
                    SensorGetMap.put(entry.getKey(), new SoundWrapper(new GroveSoundSensor(grovePi, portNumber), portNumber));
                    continue;

                case "Button":
                    SensorGetMap.put(entry.getKey(), new ButtonWrapper(grovePi.getDigitalIn(portNumber), portNumber));
                    continue;

                case "Rotary":
                    SensorGetMap.put(entry.getKey(), new RotaryWrapper(new GroveRotarySensor(grovePi, portNumber), portNumber));
                    continue;

                case "Relay":
                    SensorSetMap.put(entry.getKey(), new RelayWrapper(new GroveRelay(grovePi, portNumber), portNumber));
                    continue;

                case "Light":
                    SensorGetMap.put(entry.getKey(), new LightWrapper(new GroveLightSensor(grovePi, portNumber), portNumber));
                    continue;

                case "Buzzer":
                    SensorSetMap.put(entry.getKey(), new BuzzerWrapper(new GroveDigitalOut(grovePi, portNumber), portNumber));
                    continue;
            }
            if (entry.getValue().length() == "Temperature ".length() &&
                    entry.getValue().substring(0, "Temperature ".length()).equals("Temperature ")){

                GroveTemperatureAndHumiditySensor.Type dhtType;
                switch (entry.getValue().substring("Temperature ".length())){
                    case "DHT11":
                        dhtType = GroveTemperatureAndHumiditySensor.Type.DHT11;
                        SensorGetMap.put(entry.getKey(), new TemperatureWrapper(new GroveTemperatureAndHumiditySensor(grovePi, portNumber, dhtType), portNumber, dhtType));
                        continue;

                    case "DHT21":
                        dhtType = GroveTemperatureAndHumiditySensor.Type.DHT21;
                        SensorGetMap.put(entry.getKey(), new TemperatureWrapper(new GroveTemperatureAndHumiditySensor(grovePi, portNumber, dhtType), portNumber, dhtType));
                        continue;

                    case "DHT22":
                        dhtType = GroveTemperatureAndHumiditySensor.Type.DHT22;
                        SensorGetMap.put(entry.getKey(), new TemperatureWrapper(new GroveTemperatureAndHumiditySensor(grovePi, portNumber, dhtType), portNumber, dhtType));
                        continue;

                    case "AM2301":
                        dhtType = GroveTemperatureAndHumiditySensor.Type.AM2301;
                        SensorGetMap.put(entry.getKey(), new TemperatureWrapper(new GroveTemperatureAndHumiditySensor(grovePi, portNumber, dhtType), portNumber, dhtType));
                }
            }
        }
    }
}
