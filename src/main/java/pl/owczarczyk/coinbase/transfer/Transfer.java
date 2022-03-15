package pl.owczarczyk.coinbase.transfer;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ledger", schema = "\"coinbaseapi\"")
public class Transfer {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String type;
    private Timestamp created_at;


}
