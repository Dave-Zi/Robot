package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor;
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumidityValue;

import java.io.IOException;
import java.util.logging.Logger;

public class TemperatureWrapper implements IGroveSensorGetWrapper {
    private Logger logger = Logger.getLogger(TemperatureWrapper.class.getName());
    private GroveTemperatureAndHumiditySensor temperatureAndHumiditySensor;
    private int port;

    public TemperatureWrapper(GrovePi grovePi, int port, GroveTemperatureAndHumiditySensor.Type dhtType) {
        temperatureAndHumiditySensor = new GroveTemperatureAndHumiditySensor(grovePi, port, dhtType);
        this.port = port;
    }

    @Override
    public Double get(int mode) {
        try {
            GroveTemperatureAndHumidityValue result = temperatureAndHumiditySensor.get();
            switch (mode){
                case 0:
                    return result.getTemperature();

                case 1:
                    return result.getHumidity();
            }
        } catch (IOException e) {
            logger.severe(String.format("Error when reading data from port A%d", port));
        }
        return null;
    }
}
