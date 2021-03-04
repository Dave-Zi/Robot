package GroveWrappers.SetWrappers;

import org.iot.raspberry.grovepi.devices.GroveLed;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

public class LedWrapperTest {
    private LedWrapper ledWrapperMock;

    @Before
    public void setUp() throws Exception {
        GroveLed ledMock = PowerMockito.mock(GroveLed.class);
        ledWrapperMock = PowerMockito.mock(LedWrapper.class);
        whenNew(LedWrapper.class).withArguments(ledMock).thenReturn(ledWrapperMock);
    }

    @Test
    public void testSet() {
        PowerMockito.doNothing().when(ledWrapperMock).set(true);
        ledWrapperMock.set(true);
        verify(ledWrapperMock, times(1)).set(true);
    }

}