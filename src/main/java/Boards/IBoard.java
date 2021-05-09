package Boards;

import Enums.IPortEnums;

import java.util.List;
@SuppressWarnings("unused")
public interface IBoard<BoardPortType extends IPortEnums> {

    Boolean getBooleanSensorData(BoardPortType port, int mode);

    Double getDoubleSensorData(BoardPortType port, int mode);

    Boolean setSensorMode(BoardPortType port, boolean value);

    Boolean setActuatorData(BoardPortType port, boolean value);


    void drive(List<DriveDataObject> driveData);

    void rotate(List<DriveDataObject> driveData);

    void disconnect();

    String myAlgorithm(String json);
}
