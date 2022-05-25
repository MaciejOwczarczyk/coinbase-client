package pl.owczarczyk.coinbase.ledger;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LedgerDetail {

    @JsonProperty("transfer_id")
    private String transferId;
    @JsonProperty("transfer_type")
    private String transferType;

}
