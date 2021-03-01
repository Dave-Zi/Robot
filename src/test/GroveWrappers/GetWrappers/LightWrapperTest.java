package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.GroveDigitalIn;
import org.iot.raspberry.grovepi.devices.GroveLightSensor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LightWrapperTest {

    private LightWrapper lightWrapper;
    private GroveLightSensor lightSensor = mock(GroveLightSensor.class);

    @Before
    public void setUp() throws Exception {
        lightWrapper = new LightWrapper(lightSensor, 0);
    }

    @Test
    public void get() throws IOException {
        when(lightSensor.get()).thenReturn(0.5);
        double actual = lightWrapper.get(0);
        assertTrue(0.5==actual);
    }
}