package pl.owczarczyk.coinbase.transfer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "transfer_detail", schema = "\"coinbaseapi\"")
@Entity
public class TransferDetail {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String coinbaseAccountId;
    private String coinbaseTransactionId;
    private String coinbasePaymentMethodId;

}
