package GroveWrappers.SetWrappers;

import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BuzzerWrapperTest {

    private BuzzerWrapper buzzerWrapper;
    private GroveDigitalOut buzzer = mock(GroveDigitalOut.class);

    @Before
    public void setUp(){
        buzzerWrapper = new BuzzerWrapper(buzzer);
    }

    @Test
    public void set() throws IOException {
        doNothing().when(buzzer).set(true);
        verify(buzzer).set(true);
    }
}