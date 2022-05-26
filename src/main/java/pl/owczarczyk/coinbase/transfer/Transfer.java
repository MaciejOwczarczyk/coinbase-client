package pl.owczarczyk.coinbase.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Enumerated(EnumType.STRING)
    private TransferType type;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("completed_at")
    private String completedAt;
    @JsonProperty("canceled_at")
    private String canceledAt;
    @JsonProperty("processed_at")
    private String processedAt;
    @Column(precision = 19, scale = 10)
    private BigDecimal amount;
    @JsonProperty("user_nonce")
    private String userNonce;
    @Convert(converter = TransferDetailConverter.class)
    private TransferDetail details;
    @JsonProperty("profile_id")
    private String profileId;
    private String currency;
    private String idem;

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
