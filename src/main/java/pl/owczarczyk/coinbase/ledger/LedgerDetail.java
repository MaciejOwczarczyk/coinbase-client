package pl.owczarczyk.coinbase.ledger;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ledger_detail", schema = "\"coinbaseapi\"")
@ToString
public class LedgerDetail {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @JsonProperty("transfer_id")
    private String transferId;
    @JsonProperty("transfer_type")
    private String transferType;

}
