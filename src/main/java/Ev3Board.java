import Enums.Ev3DrivePort;
import Enums.Ev3SensorPort;
import Enums.IEv3Port;

import java.util.Map;
import java.util.logging.Logger;

public class Ev3Board implements IBoard<IEv3Port> {

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
    public Double getDoubleSensorData(IEv3Port port, int mode) {
        Ev3SensorPort newPort;
        try {
            newPort = (Ev3SensorPort) port;
        } catch (Exception e) {
            logger.severe("Wrong port type");
            return null;
        }
        Float value = ev3.sensor(newPort.portNumber, mode);
        if (value == null) {
            return null;
        } else {
            return Double.valueOf(value);
        }
    }

    @Override
    public void setSensorData(IEv3Port port, boolean value) {
        ev3.tone(440, 50, 200);
    }

    @Override
    public Boolean getBooleanSensorData(IEv3Port port, int mode) {
        return null;
    }

    @Override
    /*
      Call spin with the specific port -
      Ev3 motor ports are : A B C D
     */
    public void drive(Map<IEv3Port, Double> speeds) {
        int[] motorSpeed = new int[4];
        Ev3DrivePort[] ports = Ev3DrivePort.values();

        for (int i = 0; i < motorSpeed.length; i++) {
            Double speed = speeds.get(ports[i]);
            motorSpeed[i] = speed == null ? 0 : speed.intValue();
        }
        ev3.spin(motorSpeed[0], motorSpeed[1], motorSpeed[2], motorSpeed[3]);
    }
}
