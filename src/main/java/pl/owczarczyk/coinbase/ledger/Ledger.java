package pl.owczarczyk.coinbase.ledger;


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
    private Timestamp created_at;
    @Column(precision = 19, scale = 10)
    private BigDecimal balance;
    private String type;

    @NotEmpty
    @ManyToOne
    private Account account;

    public Ledger(LedgerDTO ledgerDTO) {
        this.id = ledgerDTO.getId();
        this.amount = ledgerDTO.getAmount();
        this.created_at = ledgerDTO.getCreated_at();
        this.balance = ledgerDTO.getBalance();
        this.type = ledgerDTO.getType();
    }

    public Ledger() {

    }
}
