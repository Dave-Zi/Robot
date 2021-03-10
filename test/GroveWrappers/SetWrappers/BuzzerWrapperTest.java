package GroveWrappers.SetWrappers;

import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GroveDigitalOut.class})
public class BuzzerWrapperTest {

    private BuzzerWrapper buzzerWrapperMock;
    private GroveDigitalOut buzzerMock;

    @Before
    public void setUp() {
        buzzerMock = PowerMockito.mock(GroveDigitalOut.class);
        buzzerWrapperMock = spy(new BuzzerWrapper(buzzerMock));
    }

    @Test
    public void testSetFailed() throws IOException {
        Mockito.doThrow(new IOException()).when(buzzerMock).set(true);
        boolean actual = buzzerWrapperMock.set(true);
        assertFalse(actual);
    }

    @Test
    public void testSetTrueSuccess() throws IOException {
        Mockito.doNothing().when(buzzerMock).set(true);
        boolean actual = buzzerWrapperMock.set(true);
        assertTrue(actual);
    }

    @Test
    public void testSetFalseSuccess() throws IOException {
        Mockito.doNothing().when(buzzerMock).set(false);
        boolean actual = buzzerWrapperMock.set(false);
        assertTrue(actual);
    }
}