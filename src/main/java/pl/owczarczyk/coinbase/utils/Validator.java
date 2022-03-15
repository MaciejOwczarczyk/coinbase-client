package pl.owczarczyk.coinbase.utils;

import org.springframework.stereotype.Component;
import pl.owczarczyk.coinbase.config.ConfigLoaderServiceImpl;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;

@Component
public class Validator implements Validatable {

    private static final String DATE_PATTERN = "server.ledger.date.pattern";
    private final String datePattern;
    private final ConfigLoaderServiceImpl configLoaderService;

    public Validator(ConfigLoaderServiceImpl configLoaderService) {
        this.configLoaderService = configLoaderService;
        this.datePattern = configLoaderService.getPropertyByName(DATE_PATTERN);
    }

    @Override
    public boolean isValidDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        simpleDateFormat.setLenient(false);
        try {
            return simpleDateFormat.parse(date, new ParsePosition(0)) != null;
        } catch (NullPointerException ignored) {
            return false;
        }
    }
}
