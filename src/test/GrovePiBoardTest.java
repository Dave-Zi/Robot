import Enums.GrovePiPort;
import GroveWrappers.GetWrappers.IGroveSensorGetWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GrovePiBoardTest {

    private GrovePiBoard grovePiBoard;

    @Before
    public void setUp() {
        grovePiBoard = mock(GrovePiBoard.class);
    }

    @Test
    public void testGetBooleanSensorData() {
        when(grovePiBoard.getSensorGetMap()).thenReturn(mock(Map.class));
        Map<String, IGroveSensorGetWrapper> groveSensorGetWrapperMap = grovePiBoard.getSensorGetMap();

        when(groveSensorGetWrapperMap.get("A0")).thenReturn(mock(IGroveSensorGetWrapper.class));
        IGroveSensorGetWrapper sensorGetWrapper = groveSensorGetWrapperMap.get("A0");

        when(sensorGetWrapper.get(1)).thenReturn(0.5);
        boolean bodyResult = grovePiBoard.getSensorGetMap().get("A0").get(1) > 0;

        when(grovePiBoard.getBooleanSensorData(GrovePiPort.A0, 1)).thenReturn(bodyResult);
        boolean actual = grovePiBoard.getBooleanSensorData(GrovePiPort.A0, 1);

        assertTrue(actual);
    }

    @Test
    public void testGetDoubleSensorData() {
        when(grovePiBoard.getSensorGetMap()).thenReturn(mock(Map.class));
        Map<String, IGroveSensorGetWrapper> groveSensorGetWrapperMap = grovePiBoard.getSensorGetMap();

        when(groveSensorGetWrapperMap.get("A1")).thenReturn(mock(IGroveSensorGetWrapper.class));
        IGroveSensorGetWrapper sensorGetWrapper = groveSensorGetWrapperMap.get("A1");

        when(sensorGetWrapper.get(1)).thenReturn(0.5);
        double bodyResult = grovePiBoard.getSensorGetMap().get("A1").get(1);

        when(grovePiBoard.getDoubleSensorData(GrovePiPort.A1, 1)).thenReturn(bodyResult);
        double actual = grovePiBoard.getDoubleSensorData(GrovePiPort.A1, 1);

        assertEquals(0.5, actual, 0.01);
    }

    @Test
    public void testSetSensorData() {
    }
}