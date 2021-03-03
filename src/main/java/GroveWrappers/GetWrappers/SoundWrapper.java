package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.devices.GroveSoundSensor;

import java.io.IOException;
import java.util.logging.Logger;

public class SoundWrapper implements IGroveSensorGetWrapper {

    private Logger logger = Logger.getLogger(SoundWrapper.class.getName());
    private GroveSoundSensor soundSensor;

    public SoundWrapper(GroveSoundSensor soundSensor){
        this.soundSensor = soundSensor;
    }

    @Override
    public Double get(int mode) {
        try {
            return soundSensor.get();
        } catch (IOException e) {
            logger.severe("Error when reading data from port");
            return null;
        }
    }
}
