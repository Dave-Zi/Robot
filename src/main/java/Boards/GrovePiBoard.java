package Boards;

import Enums.GrovePiPort;
import GroveWrappers.GetWrappers.IGroveSensorGetWrapper;
import GroveWrappers.SetWrappers.IGroveSensorSetWrapper;
import org.iot.raspberry.grovepi.pi4j.GrovePi4J;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrovePiBoard extends GrovePi4J implements IBoard<GrovePiPort> {

    private final Logger logger = Logger.getLogger(GrovePiBoard.class.getName());
    /**
     * Map for the sensors that have set function.
     * key - port of the sensor, value - the specific sensor
     */
    private Map<String, IGroveSensorSetWrapper> sensorSetMap;
    /**
     * Map for the sensors that have get function.
     * key - port of the sensor, value - the specific sensor
     */
    private Map<String, IGroveSensorGetWrapper> sensorGetMap;

    public GrovePiBoard(Map<String, IGroveSensorGetWrapper> sensorGetMap, Map<String, IGroveSensorSetWrapper> sensorSetMap) throws IOException {
        super();
        this.sensorGetMap = sensorGetMap;
        this.sensorSetMap = sensorSetMap;
        logger.setLevel(Level.SEVERE);
    }

    /**
     * Take the sensor that is connected to the port 'port' and call its' get function
     *
     * @param port of the sensor
     * @param mode of the sensor
     * @return the result of the get function of the sensor
     */
    @Override
    public Boolean getBooleanSensorData(GrovePiPort port, int mode) {
        return sensorGetMap.get(port.portName).get(mode) > 0.0;
    }

    /**
     * Take the sensor that is connected to the port 'port' and call its' get function
     *
     * @param port of the sensor
     * @param mode of the sensor
     * @return the result of the get function of the sensor
     */
    @Override
    public Double getDoubleSensorData(GrovePiPort port, int mode) {
        return sensorGetMap.get(port.portName).get(mode);
    }

    /**
     * Take the sensor that is connected to the port 'port' and call its' set function
     */
    @Override
    public Boolean setSensorData(GrovePiPort port, boolean value) {
        return sensorSetMap.get(port.portName).set(value);
    }

    @Override
    public void drive(List<DriveDataObject> driveData) {

    }

    @Override
    public void rotate(List<DriveDataObject> driveData) {

    }

    @Override
    public void disconnect() {
        logger.log(Level.FINE, "disconnected");
    }

    @Override
    public String myAlgorithm(String json) {
        return "";
    }

    //------------- getters and setters -------------//

    Map<String, IGroveSensorSetWrapper> getSensorSetMap() {
        return sensorSetMap;
    }

    Map<String, IGroveSensorGetWrapper> getSensorGetMap() {
        return sensorGetMap;
    }

    void setSensorSetMap(Map<String, IGroveSensorSetWrapper> sensorSetMap){ this.sensorSetMap = sensorSetMap;}

    void setSensorGetMap(Map<String, IGroveSensorGetWrapper> sensorGetMap){ this.sensorGetMap = sensorGetMap;}

}
