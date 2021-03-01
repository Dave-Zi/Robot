package Boards;

import java.awt.*;

public interface IBoard {

    Boolean getBooleanSensorData(String port, int mode);

    Double getDoubleSensorData(String port, int mode);

    void setSensorData(String port, boolean value);

    void drive(String port, double speed);

    void disconnect();
}
