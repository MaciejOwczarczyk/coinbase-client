package pl.owczarczyk.coinbase.ledger;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class LegerDetailDTO {

    @JsonProperty("transfer_id")
    private String transferId;
    @JsonProperty("transfer_type")
    private String transferType;


}
