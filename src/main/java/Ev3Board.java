import Enums.Ev3DrivePort;
import Enums.Ev3SensorPort;
import Enums.IPortEnums;

import java.util.logging.Logger;

public class Ev3Board implements IBoard {

    private Logger logger = Logger.getLogger(EV3.class.getName());

    private EV3 ev3;

    Ev3Board(EV3 ev3) {
        this.ev3 = ev3;
    }

    @Override

    public void disconnect() {
        ev3.disconnect();
    }

    @Override
    /*
     * Ev3 sensor ports are 1 2 3 4
     */
    public Double getDoubleSensorData(IPortEnums port, int mode) {
        Ev3SensorPort thisPort = (Ev3SensorPort) port;
        return Double.valueOf(ev3.sensor(thisPort.portNumber, mode));
    }

    @Override
    public void setSensorData(IPortEnums port, boolean value) {
        ev3.tone(440, 50, 200);
    }

    @Override
    public Boolean getBooleanSensorData(IPortEnums port, int mode) {
        return null;
    }


    @Override
    /*
      Call spin with the specific port -
      Ev3 motor ports are : A B C D
     */
    public void drive(IPortEnums port, double speed) {
        Ev3DrivePort thisPort = (Ev3DrivePort) port;

        switch (thisPort) {
            case A:
                ev3.spin((int) speed, 0, 0, 0);
                break;

            case B:
                ev3.spin(0, (int) speed, 0, 0);
                break;

            case C:
                ev3.spin(0, 0, (int) speed, 0);
                break;

            case D:
                ev3.spin(0, 0, 0, (int) speed);
                break;
        }
    }
}
