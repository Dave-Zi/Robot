package Boards;

import GroveWrappers.GetWrappers.IGroveSensorGetWrapper;
import GroveWrappers.SetWrappers.IGroveSensorSetWrapper;
import org.iot.raspberry.grovepi.pi4j.GrovePi4J;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

public class GrovePiBoard extends GrovePi4J implements IBoard {

    private final Logger logger = Logger.getLogger(GrovePiBoard.class.getName());

    private final Map<String, IGroveSensorSetWrapper> sensorSetMap;

    private final Map<String, IGroveSensorGetWrapper> sensorGetMap;

    public GrovePiBoard(Map<String, IGroveSensorGetWrapper> sensorGetMap, Map<String, IGroveSensorSetWrapper> sensorSetMap) throws IOException {
        super();
        this.sensorGetMap = sensorGetMap;
        this.sensorSetMap = sensorSetMap;
    }

    @Override
    public Boolean getBooleanSensorData(String port, int mode) {
        if (isPortIllegal(port)) {
            logger.severe("Illegal port index");
            return null;
        }

        return sensorGetMap.get(port).get(mode) > 0.0;
    }

    @Override
    public Double getDoubleSensorData(String port, int mode) {
        if (isPortIllegal(port)) {
            logger.severe("Illegal port index");
            return null;
        }
        return sensorGetMap.get(port).get(mode);
    }

    @Override
    public void setSensorData(String port, boolean value) {
        if (isPortIllegal(port)) {
            logger.severe("Illegal port index");
            return;
        }
        sensorSetMap.get(port).set(value);
    }

    @Override
    public void drive(String port, double speed) {

    }

    @Override
    public void disconnect() {

    }

    private boolean isPortIllegal(String port) {
        if (port.length() != 2) {
            return true;
        }
        char portChar = port.charAt(0);

        if (portChar < 'A' || portChar > 'D') {
            return true;
        }
        int portNumber = Integer.valueOf(port.substring(1));

        return portNumber < 0 || portNumber > 8;
    }
}
