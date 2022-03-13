package pl.owczarczyk.coinbase.timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.owczarczyk.coinbase.config.ConfigLoaderServiceImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Component
public class CoinbaseTimeStampServiceImpl implements CoinbaseTimeStampService {


    private static final String EPOCH = "epoch";
    private static final String API_TIMESTAMP_ENDPOINT = "server.coinbase.timestamp.endpoint";
    private static final Logger LOGGER = LogManager.getLogger(CoinbaseTimeStampServiceImpl.class);
    private final WebClient webClient;
    private final ConfigLoaderServiceImpl configLoaderService;

    public CoinbaseTimeStampServiceImpl(WebClient webClient, ConfigLoaderServiceImpl configLoaderService) {
        this.webClient = webClient;
        this.configLoaderService = configLoaderService;
    }

    @Override
    public String getTimeStamp() {
        URI uri = getUri();
        String out = "";
        if (Objects.nonNull(uri)) {
            try {
                CoinbaseTimeStamp mono = this.webClient.get().uri(uri)
                        .retrieve()
                        .bodyToMono(CoinbaseTimeStamp.class)
                        .block();
                assert mono != null;
                out = mono.getData().get(EPOCH);
                LOGGER.info("Timestamp from api <{}> ", out);
            } catch (WebClientResponseException | WebClientRequestException e) {
                throw new CoinbaseTimeStampException(e.getMessage());
            }
        }
        return out;
    }

    private URI getUri() {
        try {
            String endPoint = this.configLoaderService.getPropertyByName(API_TIMESTAMP_ENDPOINT);
            return new URI(endPoint);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
