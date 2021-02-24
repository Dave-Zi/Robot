package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveSoundSensor;

import java.io.IOException;
import java.util.logging.Logger;

public class SoundWrapper implements IGroveSensorGetWrapper {

    private Logger logger = Logger.getLogger(SoundWrapper.class.getName());
    private GroveSoundSensor soundSensor;
    private int port;

    public SoundWrapper(GrovePi grovePi, int port) throws IOException {
        soundSensor = new GroveSoundSensor(grovePi, port);
        this.port = port;
    }

    @Override
    public Double get(int mode) {
        try {
            return soundSensor.get();
        } catch (IOException e) {
            logger.severe(String.format("Error when reading data from port A%d", port));
            return null;
        }
    }
}
