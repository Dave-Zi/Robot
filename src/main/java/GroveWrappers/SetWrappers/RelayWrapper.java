package GroveWrappers.SetWrappers;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveRelay;

import java.io.IOException;
import java.util.logging.Logger;

public class RelayWrapper implements IGroveSensorSetWrapper {
    private Logger logger = Logger.getLogger(RelayWrapper.class.getName());
    private GroveRelay relay;
    private int port;

    public RelayWrapper(GroveRelay relay, int port){
        this.relay = relay;
        this.port = port;
    }

    @Override
    public void set(boolean value) {
        try {
            relay.set(value);
        } catch (IOException e) {
            logger.severe(String.format("Error when writing data to port D%d", port));
        }
    }
}
