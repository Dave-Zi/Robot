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
    /**
     * Map for the sensors that have set function.
     * key - port of the sensor, value - the specific sensor
     */
    private Map<String, IGroveSensorSetWrapper> SensorSetMap;
    /**
     * Map for the sensors that have get function.
     * key - port of the sensor, value - the specific sensor
     */
    private Map<String, IGroveSensorGetWrapper> SensorGetMap;

    GrovePiBoard(Map<String, IGroveSensorGetWrapper> sensorGetMap, Map<String, IGroveSensorSetWrapper> sensorSetMap) throws IOException {
        super();
        SensorGetMap = sensorGetMap;
        SensorSetMap = sensorSetMap;
    }

    /**
     * Take the sensor that is connected to the port 'port' and call its' get function
     * @param port of the sensor
     * @param mode of the sensor
     * @return the result of the get function of the sensor
     */
    @Override
    public Boolean getBooleanSensorData(IPortEnums port, int mode) {
        GrovePiPort thisPort = (GrovePiPort) port;

        return SensorGetMap.get(thisPort.portName).get(mode) > 0.0;
    }

    /**
     * Take the sensor that is connected to the port 'port' and call its' get function
     * @param port of the sensor
     * @param mode of the sensor
     * @return the result of the get function of the sensor
     */
    @Override
    public Double getDoubleSensorData(IPortEnums port, int mode) {

        GrovePiPort thisPort = (GrovePiPort) port;
        return SensorGetMap.get(thisPort.portName).get(mode);
    }

    /**
     * Take the sensor that is connected to the port 'port' and call its' set function
     */
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
