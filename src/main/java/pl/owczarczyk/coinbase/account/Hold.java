package pl.owczarczyk.coinbase.account;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    private String amount;
    private String ref;
    private String type;

    @NotEmpty
    @ManyToOne
    private Account account;


}
