package pl.owczarczyk.coinbase.hold;


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
    private Timestamp created_at;
    @Column(precision = 19, scale = 10)
    private BigDecimal amount;
    private String ref;
    private String type;

    @NotEmpty
    @ManyToOne
    private Account account;


}
