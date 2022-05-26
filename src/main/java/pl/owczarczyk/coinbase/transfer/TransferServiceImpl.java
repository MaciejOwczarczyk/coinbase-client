package pl.owczarczyk.coinbase.transfer;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import pl.owczarczyk.coinbase.account.Account;
import pl.owczarczyk.coinbase.config.ConfigLoaderServiceImpl;
import pl.owczarczyk.coinbase.generic.CoinbaseExchangeImpl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class TransferServiceImpl implements TransferService {

    private static final String TRANSFER_ENDPOINT = "server.coinbase.transfer";
    private final ConfigLoaderServiceImpl configLoaderService;
    private final CoinbaseExchangeImpl coinbaseExchange;

    public TransferServiceImpl(ConfigLoaderServiceImpl configLoaderService, CoinbaseExchangeImpl coinbaseExchange) {
        this.configLoaderService = configLoaderService;
        this.coinbaseExchange = coinbaseExchange;
    }

    @Override
    public List<Transfer> getTransferByAccount(Account account) {
        UUID id = account.getId();
        String endpoint = configLoaderService.getPropertyByName(TRANSFER_ENDPOINT);
        URI uri = UriComponentsBuilder.fromUriString(endpoint).build(id);
        List<Transfer> transferList = coinbaseExchange.getAllAsList(uri.toString(), new ParameterizedTypeReference<>() {});
        transferList.forEach(o -> o.setAccount(account));
        return transferList;
    }

    public String getTransferString(Account account) {
        UUID id = account.getId();
        String endpoint = configLoaderService.getPropertyByName(TRANSFER_ENDPOINT);
        URI uri = UriComponentsBuilder.fromUriString(endpoint).build(id);
        return coinbaseExchange.getString(uri.toString());
    }


    @Override
    public List<Transfer> getAllTransfers() {
        return null;
    }
}
