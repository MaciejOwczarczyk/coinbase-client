package pl.owczarczyk.coinbase.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ConfigLoaderServiceImplTest {

    static String KEY1 = "key.1";

    @Mock
    ConfigLoaderServiceImpl configLoaderService;

    @Test
    void getPropertyByName() {
        when(configLoaderService.getPropertyByName(KEY1)).thenReturn("value1");
        String expected = "value1";
        String actual = configLoaderService.getPropertyByName(KEY1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrownPropertyException() {
        ConfigLoaderServiceImpl configLoaderService2 = new ConfigLoaderServiceImpl();
        String name = "property";
        Exception exception = assertThrows(PropertyException.class, () -> configLoaderService2.getPropertyByName(name));
        String expectedMessage = "Property does not exist: " + name;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}