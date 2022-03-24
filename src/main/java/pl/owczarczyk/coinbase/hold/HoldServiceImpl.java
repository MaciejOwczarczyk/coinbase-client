package pl.owczarczyk.coinbase.hold;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import pl.owczarczyk.coinbase.account.Account;
import pl.owczarczyk.coinbase.config.ConfigLoaderServiceImpl;
import pl.owczarczyk.coinbase.generic.CoinbaseExchangeImpl;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
public class HoldServiceImpl implements HoldService {

    private static final Logger LOGGER = LogManager.getLogger(HoldServiceImpl.class);
    private final CoinbaseExchangeImpl coinbaseExchange;
    private final ConfigLoaderServiceImpl configLoaderService;
    private static final String HOLDS_ENDPOINT = "server.coinbase.holds";

    public HoldServiceImpl(CoinbaseExchangeImpl coinbaseExchange, ConfigLoaderServiceImpl configLoaderService) {
        this.coinbaseExchange = coinbaseExchange;
        this.configLoaderService = configLoaderService;
    }


    @Override
    public List<Hold> getHoldsByAccount(Account account, String before, String after, int limit) {
        UUID id = account.getId();
        LOGGER.debug("Get holds by account id <{}>", id);
        String holds = configLoaderService.getPropertyByName(HOLDS_ENDPOINT);
        StringBuilder sb = new StringBuilder();
        if (limit > 0) {
            sb.append("?limit=").append(limit);
        }
        String endPoint = holds + sb;
        URI uri1 = UriComponentsBuilder.fromUriString(endPoint).build(id);
        LOGGER.debug("Request path <{}>", uri1);
        List<Hold> holdsTemp = coinbaseExchange.getAllAsList(uri1.toString(), new ParameterizedTypeReference<>() {});
        holdsTemp.forEach(o -> o.setAccount(account));
        return holdsTemp;
    }
}
