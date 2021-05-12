package Boards;

import Enums.IPortEnums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("unused")
public class MockBoard implements IBoard<IPortEnums> {

    private final Random random = new Random();
    private final Map<IPortEnums, Double> portsMap = new HashMap<>();

    @Override
    public Boolean getBooleanSensorData(IPortEnums port, int mode) {
        if (portsMap.containsKey(port)) {
            return portsMap.get(port) > 0;
        }
        return false;
    }

    @Override
    public Double getDoubleSensorData(IPortEnums port, int mode) {
        if (portsMap.containsKey(port)) {
            return portsMap.get(port);
        }
        return random.nextDouble() * 100;
    }

    @Override
    public Boolean setSensorMode(IPortEnums port, boolean value) {
        if (portsMap.containsKey(port)) {
            portsMap.replace(port, value ? 1.0 : 0.0);
        } else {
            portsMap.put(port, value ? 1.0 : 0.0);
        }
        return true;
    }

    @Override
    public Boolean setActuatorData(IPortEnums port, boolean value) {
        if (portsMap.containsKey(port)) {
            portsMap.replace(port, value ? 1.0 : 0.0);
        } else {
            portsMap.put(port, value ? 1.0 : 0.0);
        }
        return true;
    }

    @Override
    public void disconnect() {
        System.out.println("Disconnected!");
    }

    @Override
    public String myAlgorithm(String json) {
        return json;
    }

    @Override
    public void rotate(List<DriveDataObject> driveData) {

    }

    @Override
    public void drive(List<DriveDataObject> driveData) {

    }
}
