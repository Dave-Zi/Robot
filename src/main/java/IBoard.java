public interface IBoard<SensorPortType, MotorPortType> {

    Boolean getBooleanSensorData(SensorPortType port, int mode);

    Double getDoubleSensorData(SensorPortType port, int mode);

    void setSensorData(SensorPortType port, boolean value);

    void drive(MotorPortType[] port, double[] speed);

    void disconnect();
}
