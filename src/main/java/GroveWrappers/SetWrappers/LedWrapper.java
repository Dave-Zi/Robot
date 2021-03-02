package GroveWrappers.SetWrappers;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveLed;

import java.io.IOException;
import java.util.logging.Logger;

public class LedWrapper implements IGroveSensorSetWrapper {
    private Logger logger = Logger.getLogger(LedWrapper.class.getName());
    private GroveLed led;

    public LedWrapper(GroveLed led){
        this.led = led;
    }

    @Override
    public void set(boolean value) {
        try {
            led.set(value);
        } catch (IOException e) {
            logger.severe("Error when writing data to port");
        }
    }
}
