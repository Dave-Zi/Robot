package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveRotarySensor;
import org.iot.raspberry.grovepi.devices.GroveRotaryValue;

import java.io.IOException;
import java.util.logging.Logger;

public class RotaryWrapper implements IGroveSensorGetWrapper {
    private Logger logger = Logger.getLogger(RotaryWrapper.class.getName());
    private GroveRotarySensor rotarySensor;
    private int port;

    public RotaryWrapper(GrovePi grovePi, int port) throws IOException {
        rotarySensor = new GroveRotarySensor(grovePi, port);
        this.port = port;
    }

    @Override
    public Double get(int mode) {
        try {
            GroveRotaryValue result = rotarySensor.get();
            switch (mode){
                case 0:
                    return result.getSensorValue();

                case 1:
                    return result.getVoltage();

                case 2:
                    return result.getDegrees();
            }
        } catch (IOException e) {
            logger.severe(String.format("Error when reading data from port A%d", port));
        }
        return null;
    }
}
