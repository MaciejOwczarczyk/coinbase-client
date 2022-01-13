package pl.owczarczyk.coinbase.account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import pl.owczarczyk.coinbase.authorization.AuthorizationServiceImpl;
import pl.owczarczyk.coinbase.configuration.ConfigLoaderServiceImpl;
import pl.owczarczyk.coinbase.timestamp.CoinbaseTimeStampServiceImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService{

    private final WebClient webClient;
    private final AuthorizationServiceImpl authorizationService;
    private final CoinbaseTimeStampServiceImpl timeStampService;
    private static final String COINBASE_URL = "server.coinbase.url";
    private static final String ACCOUNTS_ENDPOINT = "server.coinbase.accounts";
    private static final String HOLDS_ENDPOINT = "server.coinbase.holds";
    private final ConfigLoaderServiceImpl configLoaderService;
    private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl(WebClient webClient, AuthorizationServiceImpl authorizationService, CoinbaseTimeStampServiceImpl timeStampService, ConfigLoaderServiceImpl configLoaderService, HoldRepository holdRepository) {
        this.webClient = webClient;
        this.authorizationService = authorizationService;
        this.timeStampService = timeStampService;
        this.configLoaderService = configLoaderService;
    }

    @Override
    public Account getAccountById(String id) throws URISyntaxException {
        String timeStamp = timeStampService.getTimeStamp();
        String endpoint = configLoaderService.getPropertyByName(ACCOUNTS_ENDPOINT);
        URI uri = UriComponentsBuilder.newInstance().path(endpoint).build(id);
        MultiValueMap<String, String> map = authorizationService.getHeaderPro(uri, "GET", "", timeStamp);
        Account account = webClient.get().uri(uri.toString()).headers(httpHeaders -> httpHeaders.addAll(map)).retrieve().bodyToMono(Account.class).block();
        return Objects.nonNull(account) ? account : null;
    }

    @Override
    public Account[] getAllAccounts() {
        String endpoint = configLoaderService.getPropertyByName(ACCOUNTS_ENDPOINT);
        String timeStamp = timeStampService.getTimeStamp();
        UriComponents uri = UriComponentsBuilder.newInstance().path(endpoint).build();
        MultiValueMap<String, String> map = authorizationService.getHeaderPro(uri.toUri(), "GET", "", timeStamp);
        Account[] account = webClient.get().uri(uri.toString()).headers(httpHeaders -> httpHeaders.addAll(map)).retrieve().bodyToMono(Account[].class).block();
        return Objects.nonNull(account) ? account : new Account[0];
    }

    @Override
    public Hold[] getHoldsByAccount(Account account, String before, String after, int limit) {
        String holds = configLoaderService.getPropertyByName(HOLDS_ENDPOINT);
        String timeStamp = timeStampService.getTimeStamp();
        URI uri = UriComponentsBuilder.newInstance().path(holds).build(account.getId());
        MultiValueMap<String, String> map = authorizationService.getHeaderPro(uri, "GET", "", timeStamp);
        Hold[] arrayOfHolds = webClient.get().uri(uri.toString()).headers(httpHeaders -> httpHeaders.addAll(map)).retrieve().bodyToMono(Hold[].class).block();
        return Objects.nonNull(arrayOfHolds) ? arrayOfHolds : new Hold[0];
    }

}
