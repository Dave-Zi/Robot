import Enums.Ev3SensorPort;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class Ev3BoardTest {

    private EV3 ev3Mock;
    private Ev3Board ev3Board;

    @Before
    public void setUp() {
        ev3Mock = mock(EV3.class);
        ev3Board = new Ev3Board(ev3Mock);
    }

    @Test
    public void testGetDoubleSensorData() {
        when(ev3Mock.sensor(1, 0)).thenReturn((float) 0.5);
        Double actual = ev3Board.getDoubleSensorData(Ev3SensorPort._1, 0);
        assertEquals(0.5, actual, 0.01);
    }

    @Test
    public void testSetSensorData() {
        doNothing().when(ev3Mock).tone(440, 50, 200);
        ev3Board.setSensorData(Ev3SensorPort._1, true);
        verify(ev3Mock, times(1)).tone(440, 50, 200);
    }

    @Test
    public void testGetBooleanSensorData() {
        assertNull(ev3Board.getBooleanSensorData(Ev3SensorPort._1, 0));
    }

    @Test
    public void testDrive() {
        doNothing().when(ev3Mock).spin(100, 0, 0, 0);
        ev3Board.drive(null, new double[]{100, 0, 0, 0});
        verify(ev3Mock, times(1)).spin(100, 0, 0, 0);
    }
}