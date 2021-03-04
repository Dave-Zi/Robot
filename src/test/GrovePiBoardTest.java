import Enums.GrovePiPort;
import GroveWrappers.GetWrappers.IGroveSensorGetWrapper;
import GroveWrappers.SetWrappers.IGroveSensorSetWrapper;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

public class GrovePiBoardTest {
    private Map sensorSetMap;
    private GrovePiBoard grovePiBoard;

    @Before
    public void setUp() throws Exception {
        Map sensorGetMap = mock(Map.class);
        sensorSetMap = mock(Map.class);
//        GrovePi grovePiMock = PowerMockito.mock(GrovePi.class);
        grovePiBoard = PowerMockito.mock(GrovePiBoard.class);
        whenNew(GrovePiBoard.class).withArguments(sensorGetMap, sensorSetMap).thenReturn(grovePiBoard);
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
        when(sensorSetMap.get(GrovePiPort.D2)).thenReturn(PowerMockito.mock(IGroveSensorSetWrapper.class));
        IGroveSensorSetWrapper setWrapper = (IGroveSensorSetWrapper) sensorSetMap.get(GrovePiPort.D2);
        PowerMockito.doNothing().when(setWrapper).set(true);
        grovePiBoard.setSensorData(GrovePiPort.D2, true);
        verify(grovePiBoard, times(1)).setSensorData(GrovePiPort.D2, true);
    }
}