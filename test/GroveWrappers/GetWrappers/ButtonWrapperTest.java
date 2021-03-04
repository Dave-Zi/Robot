package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.GroveDigitalIn;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ButtonWrapperTest {

    private ButtonWrapper buttonWrapper;
    private GroveDigitalIn button = mock(GroveDigitalIn.class);

    @Before
    public void setUp() {
        buttonWrapper = new ButtonWrapper(button);
    }

    @Test
    public void testGetReleasedButtonValue() throws IOException, InterruptedException {
        when(button.get()).thenReturn(false);
        double actual = buttonWrapper.get(1);
        assertEquals(0.0, actual, 0.01);
    }

    @Test
    public void testGetPressedButtonValue() throws IOException, InterruptedException {
        when(button.get()).thenReturn(true);
        double actual = buttonWrapper.get(1);
        assertEquals(1.0, actual, 0.01);
    }
}