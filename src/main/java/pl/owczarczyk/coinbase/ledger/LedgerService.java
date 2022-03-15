package pl.owczarczyk.coinbase.ledger;

import pl.owczarczyk.coinbase.account.Account;

import java.util.List;

public interface LedgerService {

    List<LedgerDTO> getLedgersByAccount(Account account, String startDate, String endDate, Integer before, Integer after, Integer limit, String profileId);
}
