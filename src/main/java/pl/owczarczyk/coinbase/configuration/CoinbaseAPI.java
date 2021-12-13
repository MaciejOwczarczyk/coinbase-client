package pl.owczarczyk.coinbase.configuration;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import pl.owczarczyk.coinbase.account.Account;
import pl.owczarczyk.coinbase.account.AccountRepository;
import pl.owczarczyk.coinbase.authorization.AuthorizationServiceImpl;
import pl.owczarczyk.coinbase.timestamp.TimeStampServiceImpl;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Iterator;

@Component
public class CoinbaseAPI {

    private static final String TEST = "/accounts";
    private static final String TEST_2 = "https://api.exchange.coinbase.com/accounts";
    private static final String TEST_3 = "https://api.exchange.coinbase.com/products";
    private final AuthorizationServiceImpl authorizationService;
    private final TimeStampServiceImpl timeStampService;
    private final WebClient webClient;
    private final AccountRepository accountRepository;

    public CoinbaseAPI(AuthorizationServiceImpl authorizationService, TimeStampServiceImpl timeStampService, WebClient webClient, AccountRepository accountRepository) {
        this.authorizationService = authorizationService;
        this.timeStampService = timeStampService;
        this.webClient = webClient;
        this.accountRepository = accountRepository;
    }

    public void test() throws URISyntaxException {
        String timestamp = timeStampService.getTimeStamp();
        MultiValueMap<String, String> map = authorizationService.getHeaderPro(TEST, "GET", "", timestamp);

        Account[] mono = webClient.get().uri(new URI(TEST_2)).headers(httpHeaders -> {
            httpHeaders.addAll(map);
        }).retrieve().bodyToMono(Account[].class).block();

        if (null != mono) {
            Arrays.stream(mono).forEach(o -> {
                Account account = accountRepository.getAccountByCurrency(o.getCurrency());
                if (null != account) {
                    account.setProfile_id(o.getProfile_id());
                    account.setBalance(o.getBalance());
                    account.setAvailable(o.getAvailable());
                    account.setHold(o.getHold());
                    account.setTradingEnabled(o.isTradingEnabled());
                    accountRepository.save(account);
                } else {
                    accountRepository.save(o);
                }

            });
        }
    }
}
