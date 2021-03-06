package Boards;

import Enums.IPortEnums;

import java.util.List;
@SuppressWarnings("unused")
public interface IBoard<BoardPortType extends IPortEnums> {

    Boolean getBooleanSensorData(BoardPortType port);

    Double getDoubleSensorData(BoardPortType port);

    Boolean setSensorMode(BoardPortType port, int value);

    Boolean setActuatorData(BoardPortType port, int value);


    void drive(List<DriveDataObject> driveData);

    void rotate(List<DriveDataObject> driveData);

    void disconnect();

    String myAlgorithm(String json);
}
