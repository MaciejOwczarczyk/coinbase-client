package pl.owczarczyk.coinbase.ledger;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.id.uuid.LocalObjectUuidHelper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import pl.owczarczyk.coinbase.account.Account;
import pl.owczarczyk.coinbase.config.ConfigLoaderServiceImpl;
import pl.owczarczyk.coinbase.generic.CoinbaseExchangeImpl;
import pl.owczarczyk.coinbase.utils.Validator;

import java.net.URI;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class LedgerServiceImpl implements LedgerService {


    private final ConfigLoaderServiceImpl configLoaderService;
    private final CoinbaseExchangeImpl coinbaseExchange;
    private final Validator validator;
    private static final String LEDGER_ENDPOINT = "server.coinbase.ledger";
    private static final Logger LOG = LogManager.getLogger(LedgerServiceImpl.class);

    public LedgerServiceImpl(ConfigLoaderServiceImpl configLoaderService, CoinbaseExchangeImpl coinbaseExchange, Validator validator) {
        this.configLoaderService = configLoaderService;
        this.coinbaseExchange = coinbaseExchange;
        this.validator = validator;
    }


    @Override
    public List<Ledger> getLedgersByAccount(Account account, String startDate, String endDate, Integer before, Integer after, Integer limit, String profileId) {
        UUID id = account.getId();
        String endpoint = configLoaderService.getPropertyByName(LEDGER_ENDPOINT);
        StringBuilder sb = new StringBuilder();
        endpointBuilder(Objects.nonNull(startDate), validator.isValidDate(startDate), sb, "?start_date=", "&start_date=", startDate);
        endpointBuilder(Objects.nonNull(endDate), validator.isValidDate(endDate), sb, "?end_date=", "&end_date=", endDate);
        endpointBuilder(Objects.nonNull(before), before > 0, sb, "?before=","&before=", String.valueOf(before));
        endpointBuilder(Objects.nonNull(after), after > 0, sb, "?after=", "&after=", String.valueOf(after));
        endpointBuilder(Objects.nonNull(limit), limit > 0, sb, "?limit=", "&limit=", String.valueOf(limit));
        endpointBuilder(Objects.nonNull(profileId), StringUtils.isNotEmpty(profileId), sb,"?profile_id=", "&profile_id=", profileId);
        endpoint += sb.toString();
        LOG.info("Endpoint <{}>", endpoint);
        URI uri = UriComponentsBuilder.fromUriString(endpoint).build(id);
        List<Ledger> dtoList = coinbaseExchange.getAllAsList(uri.toString(), new ParameterizedTypeReference<>() {});
        var result = new ArrayList<Ledger>();
        dtoList.forEach(o -> {
//            Ledger ledger = new Ledger(o);
//            LedgerDetail ledgerDetail = new LedgerDetail();
//            ledgerDetail.setTransferId(o.getDetails().getTransferId());
//            ledgerDetail.setTransferType(o.getDetails().getTransferType());
////            ledger.setLedgerDetail(ledgerDetail);
//            ledger.setAccount(account);
//            result.add(ledger);
        });
        return dtoList;
    }

    public String getLedgersByAccountString(Account account, String startDate, String endDate, Integer before, Integer after, Integer limit, String profileId) {
        UUID id = account.getId();
        String endpoint = configLoaderService.getPropertyByName(LEDGER_ENDPOINT);
        StringBuilder sb = new StringBuilder();
        endpointBuilder(Objects.nonNull(startDate), validator.isValidDate(startDate), sb, "?start_date=", "&start_date=", startDate);
        endpointBuilder(Objects.nonNull(endDate), validator.isValidDate(endDate), sb, "?end_date=", "&end_date=", endDate);
        endpointBuilder(Objects.nonNull(before), before > 0, sb, "?before=","&before=", String.valueOf(before));
        endpointBuilder(Objects.nonNull(after), after > 0, sb, "?after=", "&after=", String.valueOf(after));
        endpointBuilder(Objects.nonNull(limit), limit > 0, sb, "?limit=", "&limit=", String.valueOf(limit));
        endpointBuilder(Objects.nonNull(profileId), StringUtils.isNotEmpty(profileId), sb,"?profile_id=", "&profile_id=", profileId);
        endpoint += sb.toString();
        LOG.info("Endpoint <{}>", endpoint);
        URI uri = UriComponentsBuilder.fromUriString(endpoint).build(id);
        String out = coinbaseExchange.getString(uri.toString());

        return out;
    }

    private void endpointBuilder(boolean val, boolean val1, StringBuilder sb, String str, String str2, String value) {
        if (val && val1) {
            if (sb.length() == 0) {
                sb.append(str).append(value);
            } else {
                sb.append(str2).append(value);
            }
        }
    }


}
