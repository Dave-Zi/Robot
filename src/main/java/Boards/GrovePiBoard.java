package Boards;

import Enums.GrovePiPort;
import Enums.IPortEnums;
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
    public Boolean getBooleanSensorData(IPortEnums port, int mode) {
        GrovePiPort thisPort = (GrovePiPort) port;

        return sensorGetMap.get(thisPort.portName).get(mode) > 0.0;
    }

    @Override
    public Double getDoubleSensorData(IPortEnums port, int mode) {

        GrovePiPort thisPort = (GrovePiPort) port;
        return sensorGetMap.get(thisPort.portName).get(mode);
    }

    @Override
    public void setSensorData(IPortEnums port, boolean value) {
        GrovePiPort thisPort = (GrovePiPort) port;
        sensorSetMap.get(thisPort.portName).set(value);
    }

    @Override
    public void drive(IPortEnums port, double speed) {
    }

    @Override
    public void disconnect() {
    }
}
