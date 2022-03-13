package pl.owczarczyk.coinbase.ledger;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import pl.owczarczyk.coinbase.account.Account;
import pl.owczarczyk.coinbase.authorization.AuthorizationServiceImpl;
import pl.owczarczyk.coinbase.config.ConfigLoaderServiceImpl;
import pl.owczarczyk.coinbase.generic.CoinbaseExchangeImpl;
import pl.owczarczyk.coinbase.timestamp.CoinbaseTimeStampServiceImpl;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
public class LedgerServiceImpl implements LedgerService {


    private final ConfigLoaderServiceImpl configLoaderService;
    private final CoinbaseExchangeImpl coinbaseExchange;
    private static final String LEDGER_ENDPOINT = "server.coinbase.ledger";

    public LedgerServiceImpl(ConfigLoaderServiceImpl configLoaderService, CoinbaseTimeStampServiceImpl timeStampService, CoinbaseExchangeImpl coinbaseExchange) {
        this.configLoaderService = configLoaderService;
        this.coinbaseExchange = coinbaseExchange;
    }


    @Override
    public List<LedgerDTO> getLedgersByAccount(Account account) {
        UUID id = account.getId();
        String endpoint = configLoaderService.getPropertyByName(LEDGER_ENDPOINT);
        URI uri = UriComponentsBuilder.newInstance().path(endpoint).build(id);
        return coinbaseExchange.getAllAsList(uri.toString(), new ParameterizedTypeReference<>() {});
    }

    public String getLedgersByAccount2(Account account) {
        UUID id = account.getId();
        String endpoint = configLoaderService.getPropertyByName(LEDGER_ENDPOINT);
        URI uri = UriComponentsBuilder.newInstance().path(endpoint).build(id);
        return coinbaseExchange.get2(uri.toString());
    }
}
