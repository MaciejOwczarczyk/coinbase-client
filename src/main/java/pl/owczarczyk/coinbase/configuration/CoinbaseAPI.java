package pl.owczarczyk.coinbase.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Component;
import pl.owczarczyk.coinbase.account.Account;
import pl.owczarczyk.coinbase.account.AccountRepository;
import pl.owczarczyk.coinbase.account.AccountService;
import pl.owczarczyk.coinbase.account.AccountServiceImpl;
import pl.owczarczyk.coinbase.config.ConfigLoaderService;
import pl.owczarczyk.coinbase.config.ConfigLoaderServiceImpl;
import pl.owczarczyk.coinbase.generic.CoinbaseExchangeImpl;
import pl.owczarczyk.coinbase.generic.ServiceException;
import pl.owczarczyk.coinbase.hold.HoldService;
import pl.owczarczyk.coinbase.ledger.LedgerService;
import pl.owczarczyk.coinbase.transfer.TransferService;
import pl.owczarczyk.coinbase.transfer.TransferServiceImpl;
import pl.owczarczyk.coinbase.hold.Hold;
import pl.owczarczyk.coinbase.hold.HoldRepository;
import pl.owczarczyk.coinbase.hold.HoldServiceImpl;
import pl.owczarczyk.coinbase.ledger.Ledger;
import pl.owczarczyk.coinbase.ledger.LedgerRepository;
import pl.owczarczyk.coinbase.ledger.LedgerServiceImpl;
import pl.owczarczyk.coinbase.transfer.Transfer;
import pl.owczarczyk.coinbase.transfer.TransferRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Component
public class CoinbaseAPI {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final LedgerService ledgerService;
    private final CoinbaseExchangeImpl coinbaseExchange;
    private final HoldService holdService;
    private final LedgerRepository ledgerRepository;
    private final HoldRepository holdRepository;
    private final TransferService transferService;
    private final TransferRepository transferRepository;
    private final ConfigLoaderService configLoaderService;
    private static final Logger LOGGER = LogManager.getLogger(CoinbaseAPI.class.getName());

    public CoinbaseAPI(AccountRepository accountRepository, AccountServiceImpl accountService, LedgerServiceImpl ledgerService, CoinbaseExchangeImpl coinbaseExchange, HoldServiceImpl holdService, LedgerRepository ledgerRepository, HoldRepository holdRepository, TransferServiceImpl transferService, TransferRepository transferRepository, ConfigLoaderServiceImpl configLoaderService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.ledgerService = ledgerService;
        this.coinbaseExchange = coinbaseExchange;
        this.holdService = holdService;
        this.ledgerRepository = ledgerRepository;
//        this.ledgerDetailRepository = ledgerDetailRepository;
        this.holdRepository = holdRepository;
        this.transferService = transferService;
        this.transferRepository = transferRepository;
        this.configLoaderService = configLoaderService;
    }

    public void test() throws Throwable {

        try {

            var accounts = accountService.getAllAccounts();

            accountRepository.saveAll(accounts);

            var endpoint = "server.coinbase.transfers";

            var endpointretrieved = configLoaderService.getPropertyByName(endpoint);

            String test = coinbaseExchange.getString(endpointretrieved);

            var account = accountRepository.findAccountById(UUID.fromString("848e086e-80c0-4f17-888d-e47bbbadc85b"));

            String transfer = transferService.getTransferString(account);

            String ledge = ledgerService.getTransferString(account);

            List<Ledger> ledgers = ledgerService.getLedgersByAccount(account, null, null, 0, 0, 0, null);
//
            ledgers.forEach(o -> {
                String transferId = o.getDetails().getTransferId();
                Optional<Transfer> transfer1 = transferRepository.getTransferById(UUID.fromString(transferId));
                transfer1.ifPresent(o::setTransfer);
                ledgerRepository.save(o);
            });

            List<Transfer> transferList = transferService.getTransferByAccount(account);
            transferList.forEach(o ->  {
                o.setAccount(account);
                transferRepository.save(o);
            });

//            System.out.println(transfer);

        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }
}
