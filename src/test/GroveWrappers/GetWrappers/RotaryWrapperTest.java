package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.devices.GroveRotarySensor;
import org.iot.raspberry.grovepi.devices.GroveRotaryValue;
import org.iot.raspberry.grovepi.devices.GroveSoundSensor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RotaryWrapperTest {

    private RotaryWrapper rotaryWrapper;
    private GroveRotarySensor rotarySensor = mock(GroveRotarySensor.class);

    @Before
    public void setUp() throws Exception {
        rotaryWrapper = new RotaryWrapper(rotarySensor, 0);
    }

    @Test
    public void get() throws IOException {
        sensorValue();
        voltage();
        degrees();
    }

    private void sensorValue() throws IOException {
        double sensorValue = 1.0;
        when(rotarySensor.get()).thenReturn(mock(GroveRotaryValue.class));
        GroveRotaryValue result = rotarySensor.get();
        when(result.getSensorValue()).thenReturn(1.0);
        assertTrue(sensorValue==rotaryWrapper.get(0));
    }

    private void voltage() throws IOException {
        double voltage = 1.5;
        when(rotarySensor.get()).thenReturn(mock(GroveRotaryValue.class));
        GroveRotaryValue result = rotarySensor.get();
        when(result.getVoltage()).thenReturn(1.5);
        assertTrue(voltage==rotaryWrapper.get(1));
    }

    private void degrees() throws IOException {
        double degrees = 2.0;
        when(rotarySensor.get()).thenReturn(mock(GroveRotaryValue.class));
        GroveRotaryValue result = rotarySensor.get();
        when(result.getDegrees()).thenReturn(2.0);
        assertTrue(degrees==rotaryWrapper.get(2));
    }
}