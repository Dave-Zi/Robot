package GroveWrappers.SetWrappers;

import org.iot.raspberry.grovepi.devices.GroveRelay;
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
import static org.mockito.Mockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GroveRelay.class})
public class RelayWrapperTest {

    private RelayWrapper relayWrapperMock;
    private GroveRelay relayMock;

    @Before
    public void setUp() {
        relayMock = PowerMockito.mock(GroveRelay.class);
        relayWrapperMock = spy(new RelayWrapper(relayMock));
    }

    @Test
    public void testSetFailed() throws IOException {
        Mockito.doThrow(new IOException()).when(relayMock).set(true);
        boolean actual = relayWrapperMock.set(true);
        assertFalse(actual);
    }

    @Test
    public void testSetTrueSuccess() throws IOException {
        Mockito.doNothing().when(relayMock).set(true);
        boolean actual = relayWrapperMock.set(true);
        assertTrue(actual);
    }

    @Test
    public void testSetFalseSuccess() throws IOException {
        Mockito.doNothing().when(relayMock).set(false);
        boolean actual = relayWrapperMock.set(false);
        assertTrue(actual);
    }
}
