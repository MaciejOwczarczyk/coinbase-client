package pl.owczarczyk.coinbase.account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import pl.owczarczyk.coinbase.config.ConfigLoaderServiceImpl;
import pl.owczarczyk.coinbase.generic.CoinbaseExchangeImpl;

import java.net.URI;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    private final CoinbaseExchangeImpl coinbaseExchange;
    private static final String ACCOUNTS_ENDPOINT = "server.coinbase.accounts";
    private static final String ACCOUNT_ENDPOINT = "server.coinbase.account";
    private final ConfigLoaderServiceImpl configLoaderService;
    private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl(ConfigLoaderServiceImpl configLoaderService, final CoinbaseExchangeImpl coinbaseExchange) {
        this.configLoaderService = configLoaderService;
        this.coinbaseExchange = coinbaseExchange;
    }

    @Override
    public Account getAccountById(String id) {
        String endpoint = configLoaderService.getPropertyByName(ACCOUNT_ENDPOINT);
        LOGGER.debug("Get account from server by id <{}>", id);
        URI uri = UriComponentsBuilder.newInstance().path(endpoint).build(id);
        LOGGER.debug("Request path <{}>", uri);
        return coinbaseExchange.get(uri.toString(), new ParameterizedTypeReference<>() {});
    }

    @Override
    public List<Account> getAllAccounts() {
        LOGGER.debug("Get all accounts from server");
        String endpoint = configLoaderService.getPropertyByName(ACCOUNTS_ENDPOINT);
        LOGGER.debug("Request path <{}>", endpoint);
        return coinbaseExchange.getAllAsList(endpoint, new ParameterizedTypeReference<>() {});
    }

}
