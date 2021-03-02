package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveUltrasonicRanger;

import java.io.IOException;
import java.util.logging.Logger;

public class UltrasonicWrapper implements IGroveSensorGetWrapper {

    private Logger logger = Logger.getLogger(UltrasonicWrapper.class.getName());
    private GroveUltrasonicRanger ultrasonicRanger;

    public UltrasonicWrapper(GroveUltrasonicRanger ultrasonicRanger){
        this.ultrasonicRanger = ultrasonicRanger;
    }

    @Override
    public Double get(int mode) {
        try {
            return ultrasonicRanger.get();
        } catch (IOException e) {
            logger.severe("Error when reading data from port");
            return null;
        }
    }
}
