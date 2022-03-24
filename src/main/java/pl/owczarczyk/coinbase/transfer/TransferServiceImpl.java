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
        List<TransferDTO> transferDTOList = coinbaseExchange.getAllAsList(uri.toString(), new ParameterizedTypeReference<>() {});
        var out = new ArrayList<Transfer>();
        transferDTOList.forEach( o -> {
            TransferDetail transferDetail = new TransferDetail();
            transferDetail.setCoinbaseTransactionId(o.getDetails().getCoinbaseTransactionId());
            transferDetail.setCoinbaseAccountId(o.getDetails().getCoinbaseAccountId());
            transferDetail.setCoinbasePaymentMethodId(o.getDetails().getCoinbasePaymentMethodId());
            Transfer transfer = new Transfer();
            transfer.setId(o.getId());
            transfer.setType(o.getType());
            transfer.setCreatedAt(o.getCreatedAt());
            transfer.setCompletedAt(o.getCompletedAt());
            transfer.setCanceledAt(o.getCanceledAt());
            transfer.setProcessedAt(o.getProcessedAt());
            transfer.setAmount(o.getAmount());
            transfer.setUserNonce(o.getUserNonce());
            transfer.setAccount(account);
            transfer.setTransferDetail(transferDetail);
            out.add(transfer);
        });
        return out;
    }
}
