import GroveWrappers.GetWrappers.*;
import GroveWrappers.SetWrappers.BuzzerWrapper;
import GroveWrappers.SetWrappers.IGroveSensorSetWrapper;
import GroveWrappers.SetWrappers.LedWrapper;
import GroveWrappers.SetWrappers.RelayWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor;
import org.iot.raspberry.grovepi.pi4j.GrovePi4J;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
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
    public Boolean getBooleanSensorData(String port, int mode) {
        if (isPortIllegal(port)) {
            logger.severe("Illegal port index");
            return null;
        }

        return SensorGetMap.get(port).get(mode) > 0.0;
    }

    @Override
    public Double getDoubleSensorData(String port, int mode) {
        if (isPortIllegal(port)) {
            logger.severe("Illegal port index");
            return null;
        }
        return SensorGetMap.get(port).get(mode);
    }

    @Override
    public void setSensorData(String port, boolean value) {
        if (isPortIllegal(port)) {
            logger.severe("Illegal port index");
            return;
        }
        SensorSetMap.get(port).set(value);
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
