public interface IBoard<SensorPortType, MotorPortType> {

    Boolean getBooleanSensorData(SensorPortType port, int mode);

    Double getDoubleSensorData(SensorPortType port, int mode);

    Boolean setSensorData(SensorPortType port, boolean value);

    void drive(MotorPortType[] port, double[] speed);

    void disconnect();
}
