package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.GroveDigitalIn;

import java.io.IOException;
import java.util.logging.Logger;

public class ButtonWrapper implements IGroveSensorGetWrapper {

    private Logger logger = Logger.getLogger(ButtonWrapper.class.getName());
    private GroveDigitalIn button;

    public ButtonWrapper(GroveDigitalIn button){
        this.button = button;
    }

    @Override
    public Double get(int mode) {
        try {
            return button.get() ? 1.0 : 0;
        } catch (IOException | InterruptedException e) {
            logger.severe("Error when reading data from port");
            return null;
        }
    }

    public void setLogger(Logger logger){ this.logger=logger; }

    public Logger getLogger(){ return this.logger; }
}
