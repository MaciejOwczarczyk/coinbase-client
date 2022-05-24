package pl.owczarczyk.coinbase.hold;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pl.owczarczyk.coinbase.account.Account;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.sql.Timestamp;
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


}
