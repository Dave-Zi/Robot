import java.awt.*;

public interface IBoard {

    Boolean getBooleanSensorData(IPortEnums port, int mode);

    Double getDoubleSensorData(IPortEnums port, int mode);

    void setSensorData(IPortEnums port, boolean value);

    void drive(IPortEnums port, double speed);

    void disconnect();
}
