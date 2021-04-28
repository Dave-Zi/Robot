import Enums.IPortEnums;

import java.util.List;

public interface IBoard<BoardPortType extends IPortEnums> {

    Boolean getBooleanSensorData(BoardPortType port, int mode);

    Double getDoubleSensorData(BoardPortType port, int mode);

    Boolean setSensorData(BoardPortType port, boolean value);

    void drive(List<DriveDataObject> driveData);

    void rotate(List<DriveDataObject> driveData);

    void disconnect();

    String myAlgorithm(String json);
}
