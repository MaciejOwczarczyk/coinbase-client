package pl.owczarczyk.coinbase.ledger;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import pl.owczarczyk.coinbase.account.Account;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Entity
@Setter
@Getter
@Table(name = "ledger", schema = "\"coinbaseapi\"")
@ToString
@NoArgsConstructor
public class Ledger {


    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private String id;
    private String amount;
    @JsonProperty("created_at")
    private Timestamp createdAt;
    @Column(precision = 19, scale = 10)
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private LedgerType type;
    @Convert(converter = LedgerDetailConverter.class)
    private LedgerDetail details;
    @NotEmpty
    @ManyToOne
    private Account account;

}
