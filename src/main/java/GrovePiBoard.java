import Enums.GrovePiPort;
import Enums.IPortEnums;
import GroveWrappers.GetWrappers.IGroveSensorGetWrapper;
import GroveWrappers.SetWrappers.IGroveSensorSetWrapper;
import org.iot.raspberry.grovepi.pi4j.GrovePi4J;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

public class GrovePiBoard extends GrovePi4J implements IBoard {

    private Logger logger = Logger.getLogger(GrovePiBoard.class.getName());

    private Map<String, IGroveSensorSetWrapper> SensorSetMap;

    private Map<String, IGroveSensorGetWrapper> SensorGetMap;

    GrovePiBoard(Map<String, IGroveSensorGetWrapper> sensorGetMap, Map<String, IGroveSensorSetWrapper> sensorSetMap) throws IOException {
        super();
        SensorGetMap = sensorGetMap;
        SensorSetMap = sensorSetMap;
    }

    @Override
    public Boolean getBooleanSensorData(IPortEnums port, int mode) {
        GrovePiPort thisPort = (GrovePiPort) port;

        return SensorGetMap.get(thisPort.portName).get(mode) > 0.0;
    }

    @Override
    public Double getDoubleSensorData(IPortEnums port, int mode) {

        GrovePiPort thisPort = (GrovePiPort) port;
        return SensorGetMap.get(thisPort.portName).get(mode);
    }

    @Override
    public void setSensorData(IPortEnums port, boolean value) {
        GrovePiPort thisPort = (GrovePiPort) port;
        SensorSetMap.get(thisPort.portName).set(value);
    }

    @Override
    public void drive(IPortEnums port, double speed) {
    }

    @Override
    public void disconnect() {

    }
}
