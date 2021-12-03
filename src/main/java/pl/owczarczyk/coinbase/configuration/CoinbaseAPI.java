package pl.owczarczyk.coinbase.configuration;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import pl.owczarczyk.coinbase.authorization.AuthorizationServiceImpl;
import pl.owczarczyk.coinbase.timestamp.TimeStampServiceImpl;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class CoinbaseAPI {

    private static final String TEST = "/oracle";
    private static final String TEST_2 = "https://api.exchange.coinbase.com/oracle";
    private final AuthorizationServiceImpl authorizationService;
    private final TimeStampServiceImpl timeStampService;
    private final WebClient webClient;

    public CoinbaseAPI(AuthorizationServiceImpl authorizationService, TimeStampServiceImpl timeStampService, WebClient webClient) {
        this.authorizationService = authorizationService;
        this.timeStampService = timeStampService;
        this.webClient = webClient;
    }

    public void test() throws URISyntaxException {
        String timestamp = timeStampService.getTimeStamp();
        MultiValueMap<String, String> map = authorizationService.getHeaderPro(TEST, "GET", "", timestamp);

        Mono<String> mono = webClient.get().uri(new URI(TEST_2)).headers(httpHeaders -> {
            httpHeaders.addAll(map);
        }).retrieve().bodyToMono(String.class);

        String testtes = mono.block();

        System.out.println(testtes);
    }
}
