import Enums.Ev3DrivePort;
import Enums.Ev3SensorPort;

import java.util.logging.Logger;

public class Ev3Board implements IBoard<Ev3SensorPort, Ev3DrivePort> {

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
    public Double getDoubleSensorData(Ev3SensorPort port, int mode) {
        Float value = ev3.sensor(port.portNumber, mode);
        if (value == null) {
            return null;
        } else {
            return Double.valueOf(value);
        }
    }

    @Override
    public void setSensorData(Ev3SensorPort port, boolean value) {
        ev3.tone(440, 50, 200);
    }

    @Override
    public Boolean getBooleanSensorData(Ev3SensorPort port, int mode) {
        return null;
    }

    @Override
    /*
      Call spin with the specific port -
      Ev3 motor ports are : A B C D
     */
    public void drive(Ev3DrivePort[] port, double[] speed) {
        ev3.spin((int) speed[0], (int) speed[1], (int) speed[2], (int) speed[3]);
    }
}
