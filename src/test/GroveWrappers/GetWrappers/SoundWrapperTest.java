package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.devices.GroveLightSensor;
import org.iot.raspberry.grovepi.devices.GroveSoundSensor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SoundWrapperTest {

    private SoundWrapper soundWrapper;
    private GroveSoundSensor soundSensor = mock(GroveSoundSensor.class);

    @Before
    public void setUp() throws Exception {
        soundWrapper = new SoundWrapper(soundSensor, 0);
    }

    @Test
    public void get() throws IOException {
        when(soundSensor.get()).thenReturn(0.5);
        double actual = soundWrapper.get(0);
        assertTrue(0.5==actual);
    }
}