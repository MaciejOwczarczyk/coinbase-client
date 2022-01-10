package pl.owczarczyk.coinbase.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account", schema = "\"coinbaseapi\"")
@ToString
public class Account {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(unique = true)
    private String currency;
    private String balance;
    private String available;
    private String hold;
    private UUID profile_id;
    private boolean trading_enabled;


}
