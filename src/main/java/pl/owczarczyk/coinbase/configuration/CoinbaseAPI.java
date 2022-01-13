package pl.owczarczyk.coinbase.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pl.owczarczyk.coinbase.account.*;
import pl.owczarczyk.coinbase.authorization.AuthorizationServiceImpl;
import pl.owczarczyk.coinbase.timestamp.CoinbaseTimeStampServiceImpl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Component
public class CoinbaseAPI {

    private static final String TEST = "/accounts/5613ee9c-24f2-4cb6-844a-0a91ee18041d";
    private static final String TEST_2 = "https://api.exchange.coinbase.com/accounts";
    private static final String TEST_3 = "https://api.exchange.coinbase.com/products";
    private static final String TEST_4 = "https://api.exchange.coinbase.com/accounts/5613ee9c-24f2-4cb6-844a-0a91ee18041d";
    private final AuthorizationServiceImpl authorizationService;
    private final CoinbaseTimeStampServiceImpl timeStampService;
    private final WebClient webClient;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final HoldRepository holdRepository;

    public CoinbaseAPI(AuthorizationServiceImpl authorizationService, CoinbaseTimeStampServiceImpl timeStampService, WebClient webClient, AccountRepository accountRepository, AccountServiceImpl accountService, HoldRepository holdRepository) {
        this.authorizationService = authorizationService;
        this.timeStampService = timeStampService;
        this.webClient = webClient;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.holdRepository = holdRepository;
    }

    public void test() throws Throwable {

//        Account account = accountRepository.findAccountById(UUID.fromString("848e086e-80c0-4f17-888d-e47bbbadc85b"));
//
//        Hold[] holds = accountService.getHoldsByAccount(account, null, null, 0);

        Account account1 = accountService.getAccountById("848e086e-80c0-4f17-888d-e47bbbadc85b");

//        Arrays.stream(holds).forEach(o -> {
//            o.setAccount(account);
//            holdRepository.save(o);
//        });



//        List<Hold> holdList = holdRepository.getAllByAccount_Id(UUID.fromString("848e086e-80c0-4f17-888d-e47bbbadc85b"));
//
//        Account account = accountRepository.findAccountById(UUID.fromString("848e086e-80c0-4f17-888d-e47bbbadc85b"));

//        UUID uuid = UUID.randomUUID();

//        Hold[] holds = webClient.get()
//
//        Hold hold = new Hold();
//        hold.setId(uuid);
//        hold.setAccount(account);
//        holdRepository.save(hold);


//        System.out.println(mono.toString());
    }
}
