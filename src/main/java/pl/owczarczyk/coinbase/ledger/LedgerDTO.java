package pl.owczarczyk.coinbase.ledger;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Setter
@Getter
@ToString
@Data
public class LedgerDTO {

    private String id;
    private String amount;
    @JsonProperty("created_at")
    private Timestamp createdAt;
    private BigDecimal balance;
    private LedgerType type;
    private LegerDetailDTO details;

}
