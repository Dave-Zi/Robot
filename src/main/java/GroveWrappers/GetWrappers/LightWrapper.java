package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveLightSensor;

import java.io.IOException;
import java.util.logging.Logger;

public class LightWrapper implements IGroveSensorGetWrapper{
    private Logger logger = Logger.getLogger(LightWrapper.class.getName());
    private GroveLightSensor lightSensor;

    public LightWrapper(GroveLightSensor lightSensor) {
        this.lightSensor = lightSensor;
    }

    @Override
    public Double get(int mode) {
        try {
            return lightSensor.get();
        } catch (IOException e) {
            logger.severe("Error when reading data from port");
            return null;
        }
    }
}
