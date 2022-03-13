package pl.owczarczyk.coinbase.ledger;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;


@Setter
@Getter
@ToString
@Data
public class LedgerDTO {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
    private String amount;
    private Timestamp created_at;
    private BigDecimal balance;
    private String type;
    private LinkedHashMap<String, String> details;

}
