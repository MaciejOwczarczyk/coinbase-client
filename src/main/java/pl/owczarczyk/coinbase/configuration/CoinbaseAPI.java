package pl.owczarczyk.coinbase.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Component;
import pl.owczarczyk.coinbase.account.Account;
import pl.owczarczyk.coinbase.account.AccountRepository;
import pl.owczarczyk.coinbase.account.AccountService;
import pl.owczarczyk.coinbase.account.AccountServiceImpl;
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
    private static final Logger LOGGER = LogManager.getLogger(CoinbaseAPI.class.getName());

    public CoinbaseAPI(AccountRepository accountRepository, AccountServiceImpl accountService, LedgerServiceImpl ledgerService, CoinbaseExchangeImpl coinbaseExchange, HoldServiceImpl holdService, LedgerRepository ledgerRepository, HoldRepository holdRepository, TransferServiceImpl transferService, TransferRepository transferRepository) {
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
    }

    public void test() throws Throwable {

        try {

            var account = accountRepository.findAccountById(UUID.fromString("848e086e-80c0-4f17-888d-e47bbbadc85b"));

            String transfer = transferService.getTransferString(account);

//            List<Ledger> ledgers = ledgerService.getLedgersByAccount(account, null, null, 0, 0, 0, null);
//
//            ledgerRepository.saveAll(ledgers);

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
