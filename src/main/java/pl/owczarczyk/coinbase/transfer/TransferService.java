package pl.owczarczyk.coinbase.transfer;

import pl.owczarczyk.coinbase.account.Account;

import java.util.List;

public interface TransferService {

    List<Transfer> getTransferByAccount(Account account);
    List<Transfer> getAllTransfers();
    String getTransferString(Account account);
}
