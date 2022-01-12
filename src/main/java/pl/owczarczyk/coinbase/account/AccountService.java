package pl.owczarczyk.coinbase.account;


import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

@Component
public interface AccountService {

    Account getAccountById(String id) throws URISyntaxException;
    Account[] getAllAccounts();
    Hold[] getHoldsByAccount(Account account, String before, String after, int limit);

}
