import Enums.IPortEnums;

import java.util.HashMap;
import java.util.Map;

public class MockBoard implements IBoard {

    private Map<IPortEnums, Double> portsMap = new HashMap<>();

    @Override
    public Boolean getBooleanSensorData(IPortEnums port, int mode) {
        if (portsMap.containsKey(port)) {
            return portsMap.get(port) > 0;
        }
        return null;
    }

    @Override
    public Double getDoubleSensorData(IPortEnums port, int mode) {
        if (portsMap.containsKey(port)) {
            return portsMap.get(port);
        }
        return null;
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
    public void rotate(int index, int angle, int speed) {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void drive(Map speeds) {

    }
}
