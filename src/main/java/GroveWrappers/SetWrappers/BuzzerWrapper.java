package GroveWrappers.SetWrappers;

import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GrovePi;

import java.io.IOException;
import java.util.logging.Logger;

public class BuzzerWrapper implements IGroveSensorSetWrapper {

    private Logger logger = Logger.getLogger(BuzzerWrapper.class.getName());
    private GroveDigitalOut buzzer;

    public BuzzerWrapper(GroveDigitalOut buzzer){
        this.buzzer = buzzer;
    }

    @Override
    public void set(boolean value) {
        try {
            buzzer.set(value);
        } catch (IOException e) {
            logger.severe("Error when writing data to port");
        }
    }
}
