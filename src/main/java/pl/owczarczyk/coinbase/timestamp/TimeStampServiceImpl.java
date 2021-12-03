package pl.owczarczyk.coinbase.timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Component
public class TimeStampServiceImpl implements TimeStampService {


    private final static String EPOCH = "epoch";
    private final static String PATH = "https://api.coinbase.com/v2/time";
    private static final Logger LOGGER = LogManager.getLogger(TimeStampServiceImpl.class);
    private final WebClient webClient;

    public TimeStampServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public String getTimeStamp() {
        URI uri = getUri();
        String out = "";
        if (Objects.nonNull(uri)) {
            try {
                TimeStamp mono = this.webClient.get().uri(uri)
                        .retrieve()
                        .bodyToMono(TimeStamp.class)
                        .block();
                assert mono != null;
                out = mono.getData().get(EPOCH);
                LOGGER.info("Timestamp from api <{}> ", out);
            } catch (WebClientResponseException | WebClientRequestException e) {
                throw new TimeStampException(e.getMessage());
            }
        }
        return out;
    }

    private URI getUri() {
        try {
            return new URI(PATH);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
