package pl.owczarczyk.coinbase.ledger;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Type {

    @JsonProperty("transfer")
    TRANSFER,
    @JsonProperty("match")
    MATCH,
    @JsonProperty("fee")
    FEE,
    @JsonProperty("rebate")
    REBATE,
    @JsonProperty("conversion")
    CONVERSION;
}
