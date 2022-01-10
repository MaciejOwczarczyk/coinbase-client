package pl.owczarczyk.coinbase.configuration;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import pl.owczarczyk.coinbase.account.Account;
import pl.owczarczyk.coinbase.account.AccountRepository;
import pl.owczarczyk.coinbase.account.AccountServiceImpl;
import pl.owczarczyk.coinbase.authorization.AuthorizationServiceImpl;
import pl.owczarczyk.coinbase.timestamp.TimeStampServiceImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

@Component
public class CoinbaseAPI {

    private static final String TEST = "/accounts/5613ee9c-24f2-4cb6-844a-0a91ee18041d";
    private static final String TEST_2 = "https://api.exchange.coinbase.com/accounts";
    private static final String TEST_3 = "https://api.exchange.coinbase.com/products";
    private static final String TEST_4 = "https://api.exchange.coinbase.com/accounts/5613ee9c-24f2-4cb6-844a-0a91ee18041d";
    private final AuthorizationServiceImpl authorizationService;
    private final TimeStampServiceImpl timeStampService;
    private final WebClient webClient;
    private final AccountRepository accountRepository;
    private final AccountServiceImpl accountService;

    public CoinbaseAPI(AuthorizationServiceImpl authorizationService, TimeStampServiceImpl timeStampService, WebClient webClient, AccountRepository accountRepository, AccountServiceImpl accountService) {
        this.authorizationService = authorizationService;
        this.timeStampService = timeStampService;
        this.webClient = webClient;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    public void test() throws URISyntaxException {

        Account mono = accountService.getAccountById("b3a5b4c2-289c-46e0-af64-4ec55345d2af");

        System.out.println(mono.toString());
    }
}
