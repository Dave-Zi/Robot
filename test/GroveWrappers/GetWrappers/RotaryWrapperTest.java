package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.devices.GroveRotarySensor;
import org.iot.raspberry.grovepi.devices.GroveRotaryValue;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RotaryWrapperTest {

    private RotaryWrapper rotaryWrapper;
    private GroveRotarySensor rotarySensor = mock(GroveRotarySensor.class);

    @Before
    public void setUp() {
        rotaryWrapper = new RotaryWrapper(rotarySensor);
    }

    @Test
    public void testGetSensorValue() throws IOException {
        double sensorValue = 1.0;
        when(rotarySensor.get()).thenReturn(mock(GroveRotaryValue.class));
        GroveRotaryValue result = rotarySensor.get();
        when(result.getSensorValue()).thenReturn(1.0);
        assertEquals(sensorValue, rotaryWrapper.get(0), 0.01);
    }

    @Test
    public void testGetVoltage() throws IOException {
        double voltage = 1.5;
        when(rotarySensor.get()).thenReturn(mock(GroveRotaryValue.class));
        GroveRotaryValue result = rotarySensor.get();
        when(result.getVoltage()).thenReturn(1.5);
        assertEquals(voltage, rotaryWrapper.get(1), 0.01);
    }

    @Test
    public void testGetDegrees() throws IOException {
        double degrees = 2.0;
        when(rotarySensor.get()).thenReturn(mock(GroveRotaryValue.class));
        GroveRotaryValue result = rotarySensor.get();
        when(result.getDegrees()).thenReturn(2.0);
        assertEquals(degrees, rotaryWrapper.get(2), 0.01);
    }
}