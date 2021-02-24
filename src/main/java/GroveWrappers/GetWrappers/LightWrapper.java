package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveLightSensor;

import java.io.IOException;
import java.util.logging.Logger;

public class LightWrapper implements IGroveSensorGetWrapper{
    private Logger logger = Logger.getLogger(LightWrapper.class.getName());
    private GroveLightSensor lightSensor;
    private int port;

    public LightWrapper(GrovePi grovePi, int port) throws IOException {
        lightSensor = new GroveLightSensor(grovePi, port);
        this.port = port;
    }

    @Override
    public Double get(int mode) {
        try {
            return lightSensor.get();
        } catch (IOException e) {
            logger.severe(String.format("Error when reading data from port A%d", port));
            return null;
        }
    }
}
