package pl.owczarczyk.coinbase.ledger;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;
import pl.owczarczyk.coinbase.account.Account;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Setter
@Getter
@Table(name = "ledger", schema = "\"coinbaseapi\"")
@ToString
@NoArgsConstructor
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
    private LedgerType type;
    @Convert(converter = LedgerDetailConverter.class)
    private LedgerDetail details;
    @NotEmpty
    @ManyToOne
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ledger ledger = (Ledger) o;
        return id != null && Objects.equals(id, ledger.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
