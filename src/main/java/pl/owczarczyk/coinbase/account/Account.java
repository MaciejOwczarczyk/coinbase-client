package pl.owczarczyk.coinbase.account;

import lombok.*;
import pl.owczarczyk.coinbase.hold.Hold;
import pl.owczarczyk.coinbase.ledger.Ledger;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account", schema = "\"coinbaseapi\"")
@ToString
public class Account {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(unique = true)
    private String currency;
    @Column(precision = 19, scale = 10)
    private BigDecimal balance;
    @Column(precision = 19, scale = 10)
    private BigDecimal available;
    @Column(precision = 19, scale = 10)
    private BigDecimal hold;
    private UUID profile_id;
    private boolean trading_enabled;

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<Hold> holds;

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<Ledger> ledgers;


}
