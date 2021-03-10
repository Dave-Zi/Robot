package GroveWrappers.SetWrappers;

import org.iot.raspberry.grovepi.devices.GroveRelay;

import java.io.IOException;
import java.util.logging.Logger;

public class RelayWrapper implements IGroveSensorSetWrapper {
    private Logger logger = Logger.getLogger(RelayWrapper.class.getName());
    private GroveRelay relay;

    public RelayWrapper(GroveRelay relay){
        this.relay = relay;
    }

    @Override
    public boolean set(boolean value) {
        try {
            relay.set(value);
            return true;
        } catch (IOException e) {
            logger.severe("Error when writing data to port");
            return false;
        }
    }
}
