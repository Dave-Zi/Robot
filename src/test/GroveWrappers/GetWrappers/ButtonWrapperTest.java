package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.GroveDigitalIn;
import org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ButtonWrapperTest {

    private ButtonWrapper buttonWrapper;
    private GroveDigitalIn button = mock(GroveDigitalIn.class);

    @Before
    public void setUp() throws Exception {
        buttonWrapper = new ButtonWrapper(button, 2);
    }

    @Test
    public void get() throws IOException, InterruptedException {
        pressed();
        released();
    }

    private void released() throws IOException, InterruptedException {
        when(button.get()).thenReturn(false);
        double actual = buttonWrapper.get(1);
        assertTrue(0.0 == actual);
    }

    private void pressed() throws IOException, InterruptedException {
        when(button.get()).thenReturn(true);
        double actual = buttonWrapper.get(1);
        assertTrue(1.0 == actual);
    }
}