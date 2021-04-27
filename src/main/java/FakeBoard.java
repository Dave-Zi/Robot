import Enums.FakeBoardPort;
import Enums.IPortEnums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FakeBoard implements IBoard<FakeBoardPort> {

    private Random random = new Random();
    private Map<IPortEnums, Double> portsMap = new HashMap<>();

    @Override
    public Boolean getBooleanSensorData(FakeBoardPort port, int mode) {
        if (portsMap.containsKey(port)) {
            return portsMap.get(port) > 0;
        }
        return random.nextInt(100) > 50;
    }

    @Override
    public Double getDoubleSensorData(FakeBoardPort port, int mode) {
        if (portsMap.containsKey(port)) {
            return portsMap.get(port);
        }
        return (double) random.nextInt(100);
    }

    @Override
    public Boolean setSensorData(FakeBoardPort port, boolean value) {
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
    public String freeAlgorithm(String json) {
        return json;
    }

    @Override
    public void rotate(List driveData) {

    }

    @Override
    public void drive(List driveData) {

    }
}
