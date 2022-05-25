package pl.owczarczyk.coinbase.hold;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;
import pl.owczarczyk.coinbase.account.Account;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "hold", schema = "\"coinbaseapi\"")
@ToString
public class Hold {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @JsonProperty("created_at")
    private Timestamp createdAt;
    @Column(precision = 19, scale = 10)
    private BigDecimal amount;
    private String ref;
    @Enumerated(EnumType.STRING)
    private HoldType type;

    @NotEmpty
    @ManyToOne
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Hold hold = (Hold) o;
        return id != null && Objects.equals(id, hold.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
