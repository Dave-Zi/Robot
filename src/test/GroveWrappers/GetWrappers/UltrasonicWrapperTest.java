package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.devices.GroveSoundSensor;
import org.iot.raspberry.grovepi.devices.GroveUltrasonicRanger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UltrasonicWrapperTest {

    private UltrasonicWrapper ultrasonicWrapper;
    private GroveUltrasonicRanger ultrasonicRanger = mock(GroveUltrasonicRanger.class);

    @Before
    public void setUp(){
        ultrasonicWrapper = new UltrasonicWrapper(ultrasonicRanger);
    }

    @Test
    public void getUltrasonic() throws IOException {
        when(ultrasonicRanger.get()).thenReturn(0.5);
        double actual = ultrasonicWrapper.get(1);
        assertEquals(0.5, actual, 0.01);
    }
}
