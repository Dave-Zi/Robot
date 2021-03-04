package GroveWrappers.SetWrappers;

import org.iot.raspberry.grovepi.devices.GroveRelay;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

public class RelayWrapperTest {

    private RelayWrapper relayWrapperMock;

    @Before
    public void setUp() throws Exception {
        GroveRelay relayMock = PowerMockito.mock(GroveRelay.class);
        relayWrapperMock = PowerMockito.mock(RelayWrapper.class);
        whenNew(RelayWrapper.class).withArguments(relayMock).thenReturn(relayWrapperMock);
    }

    @Test
    public void testSetRelay() {
        PowerMockito.doNothing().when(relayWrapperMock).set(true);
        relayWrapperMock.set(true);
        verify(relayWrapperMock, times(1)).set(true);
    }
}