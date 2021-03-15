import Enums.IPortEnums;

import java.util.Map;

public interface IBoard<BoardPortType extends IPortEnums> {

    Boolean getBooleanSensorData(BoardPortType port, int mode);

    Double getDoubleSensorData(BoardPortType port, int mode);

    void setSensorData(BoardPortType port, boolean value);

    void drive(Map<BoardPortType, Double> speeds);

    void disconnect();
}
