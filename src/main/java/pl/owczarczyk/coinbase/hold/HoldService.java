package pl.owczarczyk.coinbase.hold;

import pl.owczarczyk.coinbase.account.Account;

import java.util.List;

public interface HoldService {

    List<Hold> getHoldsByAccount(Account account, String before, String after, int limit);

}
