package pl.owczarczyk.coinbase.account;


import java.net.URISyntaxException;
import java.util.List;

public interface AccountService {

    Account getAccountById(String id) throws URISyntaxException;
    List<Account> getAllAccounts();

}
