package GroveWrappers.SetWrappers;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveLed;

import java.io.IOException;
import java.util.logging.Logger;

public class LedWrapper implements IGroveSensorSetWrapper {
    private Logger logger = Logger.getLogger(LedWrapper.class.getName());
    private GroveLed led;
    private int port;

    public LedWrapper(GrovePi grovePi, int port) throws IOException {
        led = new GroveLed(grovePi, port);
        this.port = port;
    }

    @Override
    public void set(boolean value) {
        try {
            led.set(value);
        } catch (IOException e) {
            logger.severe(String.format("Error when writing data to port D%d", port));
        }
    }
}
