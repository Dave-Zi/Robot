package GroveWrappers.SetWrappers;

import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

public class BuzzerWrapperTest {

    private BuzzerWrapper buzzerWrapperMock;

    @Before
    public void setUp() throws Exception {
        GroveDigitalOut buzzerMock = PowerMockito.mock(GroveDigitalOut.class);
        buzzerWrapperMock = PowerMockito.mock(BuzzerWrapper.class);
        whenNew(BuzzerWrapper.class).withArguments(buzzerMock).thenReturn(buzzerWrapperMock);
    }

    @Test
    public void testSet() {
        PowerMockito.doNothing().when(buzzerWrapperMock).set(true);
        buzzerWrapperMock.set(true);
        verify(buzzerWrapperMock, times(1)).set(true);
    }
}