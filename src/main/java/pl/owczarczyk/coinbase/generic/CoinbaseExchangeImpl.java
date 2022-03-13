package pl.owczarczyk.coinbase.generic;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.owczarczyk.coinbase.authorization.AuthorizationServiceImpl;
import pl.owczarczyk.coinbase.configuration.RequestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

@Service
public class CoinbaseExchangeImpl implements CoinbaseExchange {

    private final WebClient webClient;
    private final AuthorizationServiceImpl authorizationService;

    public CoinbaseExchangeImpl(WebClient webClient, AuthorizationServiceImpl authorizationService) {
        this.webClient = webClient;
        this.authorizationService = authorizationService;
    }

    @Override
    public <T> T get(String endpoint, ParameterizedTypeReference<T> type) {
        try {
            MultiValueMap<String, String> map = authorizationService.getHeaderPro(endpoint, RequestClient.GET.toString(), "");
            return webClient
                    .get()
                    .uri(endpoint)
                    .headers(httpHeaders -> httpHeaders.addAll(map))
                    .retrieve()
                    .bodyToMono(type).block();
        } catch (WebClientResponseException e) {
            throw new ServiceException(e.getMessage(), e.getRawStatusCode());
        }
    }

    @Override
    public <T> List<T> getAllAsList(String endPoint, ParameterizedTypeReference<T[]> type) {
        T[] response = get(endPoint, type);
        return Objects.nonNull(response) ? Arrays.asList(response) : emptyList();
    }

    public String get2(String endpoint) {
        try {
            MultiValueMap<String, String> map = authorizationService.getHeaderPro(endpoint, RequestClient.GET.toString(), "");
            return webClient
                    .get()
                    .uri(endpoint)
                    .headers(httpHeaders -> httpHeaders.addAll(map))
                    .retrieve()
                    .bodyToMono(String.class).block();
        } catch (WebClientResponseException e) {
            throw new ServiceException(e.getMessage(), e.getRawStatusCode());
        }
    }

}
