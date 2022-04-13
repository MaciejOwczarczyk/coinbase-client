package pl.owczarczyk.coinbase.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Component;
import pl.owczarczyk.coinbase.account.Account;
import pl.owczarczyk.coinbase.account.AccountRepository;
import pl.owczarczyk.coinbase.account.AccountService;
import pl.owczarczyk.coinbase.account.AccountServiceImpl;
import pl.owczarczyk.coinbase.ledger.LedgerDetailRepository;
import pl.owczarczyk.coinbase.generic.CoinbaseExchangeImpl;
import pl.owczarczyk.coinbase.generic.ServiceException;
import pl.owczarczyk.coinbase.hold.Hold;
import pl.owczarczyk.coinbase.hold.HoldRepository;
import pl.owczarczyk.coinbase.hold.HoldServiceImpl;
import pl.owczarczyk.coinbase.ledger.Ledger;
import pl.owczarczyk.coinbase.ledger.LedgerRepository;
import pl.owczarczyk.coinbase.ledger.LedgerServiceImpl;
import pl.owczarczyk.coinbase.transfer.*;

import java.util.List;
import java.util.UUID;


@Component
public class CoinbaseAPI {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final LedgerServiceImpl ledgerService;
    private final CoinbaseExchangeImpl coinbaseExchange;
    private final HoldServiceImpl holdService;
    private final LedgerRepository ledgerRepository;
    private final LedgerDetailRepository ledgerDetailRepository;
    private final HoldRepository holdRepository;
    private final TransferService transferService;
    private final TransferRepository transferRepository;
    private static final Logger LOGGER = LogManager.getLogger(CoinbaseAPI.class.getName());

    public CoinbaseAPI(AccountRepository accountRepository, AccountServiceImpl accountService, LedgerServiceImpl ledgerService, CoinbaseExchangeImpl coinbaseExchange, HoldServiceImpl holdService, LedgerRepository ledgerRepository, LedgerDetailRepository ledgerDetailRepository, HoldRepository holdRepository, TransferServiceImpl transferService, TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.ledgerService = ledgerService;
        this.coinbaseExchange = coinbaseExchange;
        this.holdService = holdService;
        this.ledgerRepository = ledgerRepository;
        this.ledgerDetailRepository = ledgerDetailRepository;
        this.holdRepository = holdRepository;
        this.transferService = transferService;
        this.transferRepository = transferRepository;
    }

    public void test() throws Throwable {

        try {
            var accountList = accountService.getAllAccounts();
            accountRepository.saveAll(accountList);
            var account = accountRepository.findAccountById(UUID.fromString("848e086e-80c0-4f17-888d-e47bbbadc85b"));

            var transferList = transferService.getTransferByAccount(account);

//
//            String dateStr = transferList.get(0).getCreatedAt();
//
//            String formatIn = "yyyy-MM-dd HH:mm:ss.SSSSSS+00";
//            String formatOut = "yyyy-MM-dd'T'HH:mm:ss.SSSz";
//
//
//            LocalDateTime ldt = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(formatIn));
//
//            Timestamp timestamp = Timestamp.valueOf(ldt);

            List<Hold> holds2 = holdService.getHoldsByAccount(account, null, null, 0);
            holds2.forEach(o -> {
                o.setAccount(account);
                holdRepository.save(o);
            });

            List<Ledger> ledgers = ledgerService.getLedgersByAccount(account, "2020/11/01", null, 1, 1, 1, "8e3baeea-c5a4-42e8-baf3-733c8f0f7b07");

            ledgerRepository.saveAll(ledgers);

            List<Transfer> transfers = transferService.getTransferByAccount(account);

            transferRepository.saveAll(transfers);
            transfers.forEach(o -> {
                LOGGER.info(o.getId());
            });



            LOGGER.info(ledgers.size());

            Account account1 = accountRepository.findAccountById(UUID.fromString("848e086e-80c0-4f17-888d-e47bbbadc85b"));
            var holds = account1.getHolds();

            LOGGER.info("eee");

        } catch (ServiceException e) {
            e.printStackTrace();
        }

//        List<Account> accountList = accountService.getAllAccounts();

//


//        Mono<Account> mono = accountService.getAccountById("848e086e-80c0-4f17-888d-e47bbbadc85b");

    }
}
