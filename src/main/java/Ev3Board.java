
import Enums.Ev3DrivePort;
import Enums.Ev3SensorPort;
import Enums.IPortEnums;

import java.util.logging.Logger;

public class Ev3Board extends EV3 implements IBoard {

    private Logger logger = Logger.getLogger(EV3.class.getName());

    Ev3Board(String portName) {
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
    public Double getDoubleSensorData(IPortEnums port, int mode) {

        Ev3SensorPort thisPort = (Ev3SensorPort) port;
        return Double.valueOf(super.sensor(thisPort.portNumber, mode));
    }

    @Override
    public void setSensorData(IPortEnums port, boolean value) {
        tone(440, 50, 200);
    }

    @Override
    public Boolean getBooleanSensorData(IPortEnums port, int mode) {
        return null;
    }


    @Override
    public void drive(IPortEnums port, double speed) {
        Ev3DrivePort thisPort = (Ev3DrivePort) port;

//        int portInt = Integer.getInteger(port);
        switch (thisPort) {
            case A:
                spin((int) speed, 0, 0, 0);
                break;

            case B:
                spin(0, (int) speed, 0, 0);
                break;

            case C:
                spin(0, 0, (int) speed, 0);
                break;

            case D:
                spin(0, 0, 0, (int) speed);
                break;
        }
    }
}
