import Enums.IPortEnums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MockBoard implements IBoard {

    private Random random = new Random();
    private Map<IPortEnums, Double> portsMap = new HashMap<>();

    @Override
    public Boolean getBooleanSensorData(IPortEnums port, int mode) {
        if (portsMap.containsKey(port)) {
            return portsMap.get(port) > 0;
        }
        return random.nextInt(100) > 50;
    }

    @Override
    public Double getDoubleSensorData(IPortEnums port, int mode) {
        if (portsMap.containsKey(port)) {
            return portsMap.get(port);
        }
        return (double) random.nextInt(100);
    }

    @Override
    public Boolean setSensorData(IPortEnums port, boolean value) {
        if (portsMap.containsKey(port)) {
            portsMap.replace(port, value ? 1.0 : 0.0);
        } else {
            portsMap.put(port, value ? 1.0 : 0.0);
        }
        return true;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public String myAlgorithm(String json) {
        return json;
    }

    @Override
    public void rotate(List driveData) {

    }

    @Override
    public void drive(List driveData) {

    }
}
