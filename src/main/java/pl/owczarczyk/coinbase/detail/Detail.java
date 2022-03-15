package pl.owczarczyk.coinbase.detail;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import pl.owczarczyk.coinbase.ledger.Ledger;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "detail", schema = "\"coinbaseapi\"")
@ToString
public class Detail {

//    @Id
//    @Column(name = "id", updatable = false, nullable = false)
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    private UUID id;

    @Id
    @Column(name = "transfer_id", updatable = false, nullable = false)
//    @Column(unique = true)
    @JsonProperty("transfer_id")
    private String transferId;
    @JsonProperty("transfer_type")
    private String transferType;

//    @OneToOne
//    private Ledger ledger;



}
