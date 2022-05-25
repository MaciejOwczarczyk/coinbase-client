package pl.owczarczyk.coinbase.transfer;

import lombok.*;
import org.hibernate.Hibernate;
import pl.owczarczyk.coinbase.account.Account;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "transfer", schema = "\"coinbaseapi\"")
@ToString
@RequiredArgsConstructor
public class Transfer {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String type;
    private String createdAt;
    private String completedAt;
    private String canceledAt;
    private String processedAt;
    @Column(precision = 19, scale = 10)
    private BigDecimal amount;
    private String userNonce;

    @ManyToOne(cascade = CascadeType.MERGE)
    private TransferDetail transferDetail;

    @ManyToOne
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transfer transfer = (Transfer) o;
        return id != null && Objects.equals(id, transfer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
