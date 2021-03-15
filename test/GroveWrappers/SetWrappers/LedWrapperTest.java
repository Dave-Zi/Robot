package GroveWrappers.SetWrappers;

import org.iot.raspberry.grovepi.devices.GroveLed;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@PrepareForTest({GroveLed.class})
public class LedWrapperTest {

    private LedWrapper ledWrapperMock;
    private GroveLed ledMock;

    @Before
    public void setUp() {
        ledMock = Mockito.mock(GroveLed.class);
        ledWrapperMock = Mockito.spy(new LedWrapper(ledMock));
    }

    @Test
    public void testSetFailed() throws IOException {
        Mockito.doThrow(new IOException()).when(ledMock).set(true);
        boolean actual = ledWrapperMock.set(true);
        assertFalse(actual);
    }

    @Test
    public void testSetTrueSuccess() throws IOException {
        Mockito.doNothing().when(ledMock).set(true);
        boolean actual = ledWrapperMock.set(true);
        assertTrue(actual);
    }

    @Test
    public void testSetFalseSuccess() throws IOException {
        Mockito.doNothing().when(ledMock).set(false);
        boolean actual = ledWrapperMock.set(false);
        assertTrue(actual);
    }
}