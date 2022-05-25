package pl.owczarczyk.coinbase.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;
import pl.owczarczyk.coinbase.hold.Hold;
import pl.owczarczyk.coinbase.ledger.Ledger;
import pl.owczarczyk.coinbase.transfer.Transfer;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
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
    @JsonProperty("profile_id")
    private UUID profileId;
    @JsonProperty("trading_enabled")
    private boolean tradingEnabled;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Hold> holds;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Ledger> ledgers;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Transfer> transfers;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
