package pl.owczarczyk.coinbase.ledger;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.owczarczyk.coinbase.account.Account;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name = "ledger", schema = "\"coinbaseapi\"")
@ToString
public class Ledger {

    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private String id;
    private String amount;
    @JsonProperty("created_at")
    private Timestamp createdAt;
    @Column(precision = 19, scale = 10)
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private Type type;

    @NotEmpty
    @ManyToOne
    private Account account;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ledger_detail_id", referencedColumnName = "id", nullable = false)
    private LedgerDetail ledgerDetail;

    public Ledger(LedgerDTO ledgerDTO) {
        this.id = ledgerDTO.getId();
        this.amount = ledgerDTO.getAmount();
        this.createdAt = ledgerDTO.getCreatedAt();
        this.balance = ledgerDTO.getBalance();
        this.type = ledgerDTO.getType();
    }

    public Ledger() {

    }
}
