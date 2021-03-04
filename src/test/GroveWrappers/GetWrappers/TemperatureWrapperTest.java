package GroveWrappers.GetWrappers;

import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor;
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumidityValue;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

public class TemperatureWrapperTest {

    private TemperatureWrapper temperatureWrapper;
    private GroveTemperatureAndHumiditySensor temperatureAndHumiditySensor = mock(GroveTemperatureAndHumiditySensor.class);

    @Before
    public void setUp(){
        temperatureWrapper = new TemperatureWrapper(temperatureAndHumiditySensor);
    }

    @Test
    public void testGetTemperature() throws IOException {
        double temperature = 30.5;
        when(temperatureAndHumiditySensor.get()).thenReturn(mock(GroveTemperatureAndHumidityValue.class));
        GroveTemperatureAndHumidityValue result = temperatureAndHumiditySensor.get();
        when(result.getTemperature()).thenReturn(30.5);
        assertEquals(temperature, temperatureWrapper.get(0), 0.01);
    }

    @Test
    public void testGetHumidity() throws IOException {
        double humidity = 20.5;
        when(temperatureAndHumiditySensor.get()).thenReturn(mock(GroveTemperatureAndHumidityValue.class));
        GroveTemperatureAndHumidityValue result = temperatureAndHumiditySensor.get();
        when(result.getHumidity()).thenReturn(20.5);
        assertEquals(humidity, temperatureWrapper.get(1), 0.01);
    }

}