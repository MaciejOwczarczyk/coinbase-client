package pl.owczarczyk.coinbase.account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import pl.owczarczyk.coinbase.authorization.AuthorizationServiceImpl;
import pl.owczarczyk.coinbase.configuration.ConfigLoaderServiceImpl;
import pl.owczarczyk.coinbase.timestamp.TimeStampServiceImpl;

import javax.xml.stream.events.EndDocument;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Component
public class AccountServiceImpl implements AccountService{

    private final WebClient webClient;
    private final AuthorizationServiceImpl authorizationService;
    private final TimeStampServiceImpl timeStampService;
    private final String COINBASE_URL = "server.coinbase.url";
    private final String ACCOUNTS_ENDPOINT = "server.coinbase.accounts";
    private final ConfigLoaderServiceImpl configLoaderService;
    private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl(WebClient webClient, AuthorizationServiceImpl authorizationService, TimeStampServiceImpl timeStampService, ConfigLoaderServiceImpl configLoaderService) {
        this.webClient = webClient;
        this.authorizationService = authorizationService;
        this.timeStampService = timeStampService;
        this.configLoaderService = configLoaderService;
    }

    @Override
    public Account getAccountById(String id) {
        String timeStamp = timeStampService.getTimeStamp();
        String url = configLoaderService.getPropertyByName(COINBASE_URL);
        String endpoint = configLoaderService.getPropertyByName(ACCOUNTS_ENDPOINT) + "/" + id;
        String combine = url + endpoint;
        MultiValueMap<String, String> map = authorizationService.getHeaderPro(endpoint, "GET", "", timeStamp);
        URI uri = getUri(combine);
        assert uri != null;
        Account account = webClient.get().uri(uri).headers(httpHeaders -> httpHeaders.addAll(map)).retrieve().bodyToMono(Account.class).block();
        return Objects.nonNull(account) ? account : null;
    }

    @Override
    public Account[] getAllAccounts() {
        String url = configLoaderService.getPropertyByName(COINBASE_URL);
        String endpoint = configLoaderService.getPropertyByName(ACCOUNTS_ENDPOINT);
        String timeStamp = timeStampService.getTimeStamp();
        MultiValueMap<String, String> map = authorizationService.getHeaderPro(endpoint, "GET", "", timeStamp);
        URI uri = getUri(url + endpoint);
        assert uri != null;
        Account[] account = webClient.get().uri(uri).headers(httpHeaders -> httpHeaders.addAll(map)).retrieve().bodyToMono(Account[].class).block();
        return Objects.nonNull(account) ? account : new Account[0];
    }


    private URI getUri(String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }
}
