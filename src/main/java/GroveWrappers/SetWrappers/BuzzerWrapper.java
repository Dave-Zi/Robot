package GroveWrappers.SetWrappers;

import org.iot.raspberry.grovepi.GroveDigitalOut;

import java.io.IOException;
import java.util.logging.Logger;

public class BuzzerWrapper implements IGroveSensorSetWrapper {

    private Logger logger = Logger.getLogger(BuzzerWrapper.class.getName());
    private GroveDigitalOut buzzer;

    public BuzzerWrapper(GroveDigitalOut buzzer){
        this.buzzer = buzzer;
    }

    @Override
    public boolean set(boolean value) {
        try {
            buzzer.set(value);
            return true;
        } catch (IOException e) {
            logger.severe("Error when writing data to port");
            return false;
        }
    }
}
