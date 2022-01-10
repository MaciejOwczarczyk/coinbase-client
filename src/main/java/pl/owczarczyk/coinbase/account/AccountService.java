package pl.owczarczyk.coinbase.account;


import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Service
public interface AccountService {

    Account getAccountById(String id);
    Account[] getAllAccounts();

}
