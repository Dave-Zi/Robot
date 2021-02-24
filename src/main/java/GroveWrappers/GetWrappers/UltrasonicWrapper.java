package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveUltrasonicRanger;

import java.io.IOException;
import java.util.logging.Logger;

public class UltrasonicWrapper implements IGroveSensorGetWrapper {

    private Logger logger = Logger.getLogger(UltrasonicWrapper.class.getName());
    private GroveUltrasonicRanger ultrasonicRanger;
    private int port;

    public UltrasonicWrapper(GrovePi grovePi, int port){
        ultrasonicRanger = new GroveUltrasonicRanger(grovePi, port);
        this.port = port;
    }

    @Override
    public Double get(int mode) {
        try {

            return ultrasonicRanger.get();
        } catch (IOException e) {
            logger.severe(String.format("Error when reading data from port D%d", port));
            return null;
        }
    }
}
