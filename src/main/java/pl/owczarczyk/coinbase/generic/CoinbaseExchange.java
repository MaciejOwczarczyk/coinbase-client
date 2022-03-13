package pl.owczarczyk.coinbase.generic;

import org.springframework.core.ParameterizedTypeReference;

import java.util.List;


public interface CoinbaseExchange {

    <T> T get(String endpoint, ParameterizedTypeReference<T> type);
    <T> List<T> getAllAsList(String endPoint, ParameterizedTypeReference<T[]> type);
}
