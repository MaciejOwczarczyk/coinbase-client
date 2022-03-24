package pl.owczarczyk.coinbase.transfer;


import lombok.*;
import pl.owczarczyk.coinbase.account.Account;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "transfer", schema = "\"coinbaseapi\"")
@Data
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

}
