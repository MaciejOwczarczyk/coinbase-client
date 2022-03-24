package pl.owczarczyk.coinbase.transfer;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Data
@ToString
public class TransferDTO {

    private UUID id;
    private String type;
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
    private TransferDetailDTO details;

}
