package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.GroveDigitalIn;
import org.iot.raspberry.grovepi.GrovePi;

import java.io.IOException;
import java.util.logging.Logger;

public class ButtonWrapper implements IGroveSensorGetWrapper {

    private Logger logger = Logger.getLogger(ButtonWrapper.class.getName());
    private GroveDigitalIn button;
    private int port;

    public ButtonWrapper(GrovePi grovePi, int port) throws IOException {
        button = grovePi.getDigitalIn(port);
        this.port = port;
    }

    @Override
    public Double get(int mode) {
        try {
            return button.get() ? 1.0 : 0;
        } catch (IOException | InterruptedException e) {
            logger.severe(String.format("Error when reading data from port D%d", port));
            return null;
        }
    }
}
