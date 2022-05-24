package pl.owczarczyk.coinbase.hold;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum HoldType {

    @JsonProperty("order")
    ORDER,
    @JsonProperty("transfer")
    TRANSFER,
    @JsonProperty("funding")
    FUNDING,
    @JsonProperty("profile_transfer")
    PROFILE_TRANSFER,
    @JsonProperty("otc_order")
    OTC_ORDER,
    @JsonProperty("launch_sell")
    LAUNCH_SELL,
    @JsonProperty("launch_buy")
    LAUNCH_BUY,
    @JsonProperty("engine_credit_property")
    ENGINE_CREDIT_OPERATION;


}
