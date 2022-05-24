package pl.owczarczyk.coinbase.ledger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

    private final ObjectMapper objectMapper;

    public HashMapConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        String customerInfoJson = null;
        try {
            customerInfoJson = objectMapper.writeValueAsString(stringObjectMap);
        } catch (final JsonProcessingException e) {
//            logger.error("JSON writing error", e);
        }

        return customerInfoJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String customerInfoJSON) {
        Map<String, Object> customerInfo = null;
        try {
            customerInfo = objectMapper.readValue(customerInfoJSON, Map.class);
        } catch (final IOException e) {
//            logger.error("JSON reading error", e);
        }
        return customerInfo;
    }
}
