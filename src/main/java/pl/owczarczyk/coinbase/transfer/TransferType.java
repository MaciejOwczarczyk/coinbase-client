package pl.owczarczyk.coinbase.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransferType {

    @JsonProperty("deposit")
    DEPOSIT,
    @JsonProperty("withdraw")
    WITHDRAW,
    @JsonProperty("move")
    MOVE
}
