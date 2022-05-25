package pl.owczarczyk.coinbase.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class Validator implements Validatable {

    public Validator() {
        // empty constructor
    }

    @Override
    public boolean isValidDate(String date, String datePattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        simpleDateFormat.setLenient(false);
        try {
            simpleDateFormat.parse(date);
        } catch (ParseException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
