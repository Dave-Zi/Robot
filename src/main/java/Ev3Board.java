
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Ev3Board extends EV3 implements IBoard {

    private Logger logger = Logger.getLogger(EV3.class.getName());

    Ev3Board(String portName) throws IOException {
        super(portName);
    }

    @Override
    public void disconnect() {
        super.disconnect();
    }

    @Override
    /*
     * Ev3 sensor ports are 1 2 3 4
     */
    public Double getDoubleSensorData(String port, int mode) {
        if (port.length() != 1) {
            logger.severe("Illegal port index");
            return 0.0;
        }
        int portInt = Integer.getInteger(port);
        if (portInt < 1 || portInt > 4) {
            logger.severe("Illegal port index");
            return 0.0;
        }
        return Double.valueOf(super.sensor(portInt, mode));
    }

    @Override
    public void setSensorData(String port, boolean value) {
        tone(440, 50, 200);
    }

    @Override
    public Boolean getBooleanSensorData(String port, int mode) {
        return null;
    }


    @Override
    public void drive(String port, double speed) {
        if (port.length() != 1) {
            logger.severe("Illegal port index");
            return;
        }


//        int portInt = Integer.getInteger(port);
        switch (port) {
            case "A":
                spin((int) speed, 0, 0, 0);
                break;

            case "B":
                spin(0, (int) speed, 0, 0);
                break;

            case "C":
                spin(0, 0, (int) speed, 0);
                break;

            case "D":
                spin(0, 0, 0, (int) speed);
                break;
        }
    }
}
