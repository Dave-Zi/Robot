import Enums.GrovePiPort;
import GroveWrappers.GetWrappers.IGroveSensorGetWrapper;
import GroveWrappers.SetWrappers.IGroveSensorSetWrapper;
import org.iot.raspberry.grovepi.pi4j.GrovePi4J;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

public class GrovePiBoard extends GrovePi4J implements IBoard<GrovePiPort, GrovePiPort> {

    private final Logger logger = Logger.getLogger(GrovePiBoard.class.getName());
    /**
     * Map for the sensors that have set function.
     * key - port of the sensor, value - the specific sensor
     */
    private final Map<String, IGroveSensorSetWrapper> sensorSetMap;
    /**
     * Map for the sensors that have get function.
     * key - port of the sensor, value - the specific sensor
     */
    private final Map<String, IGroveSensorGetWrapper> sensorGetMap;

    GrovePiBoard(Map<String, IGroveSensorGetWrapper> sensorGetMap, Map<String, IGroveSensorSetWrapper> sensorSetMap) throws IOException {
        super();
        this.sensorGetMap = sensorGetMap;
        this.sensorSetMap = sensorSetMap;
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
    public void setSensorData(GrovePiPort port, boolean value) {
        sensorSetMap.get(port.portName).set(value);
    }

    @Override
    public void drive(GrovePiPort[] port, double[] speed) {
    }

    @Override
    public void disconnect() {
    }

    //------------- getters -------------//

    public Map<String, IGroveSensorSetWrapper> getSensorSetMap() {
        return sensorSetMap;
    }

    public Map<String, IGroveSensorGetWrapper> getSensorGetMap() {
        return sensorGetMap;
    }

}
