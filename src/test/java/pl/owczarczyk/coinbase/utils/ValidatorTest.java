package pl.owczarczyk.coinbase.utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void isValidDate() {
        Validator validator = new Validator();
        String datePattern = "yyyy/MM/dd";
        assertTrue(validator.isValidDate("2020/01/01", datePattern));
        assertFalse(validator.isValidDate("22/34/212", datePattern));
    }

}