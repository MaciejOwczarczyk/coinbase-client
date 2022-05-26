package pl.owczarczyk.coinbase.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class TransferDetail {

    @JsonProperty("coinbase_account_id")
    private String coinbaseAccountId;
    @JsonProperty("coinbase_transaction_id")
    private String coinbaseTransactionId;
    @JsonProperty("coinbase_payment_method_id")
    private String coinbasePaymentMethodId;


}
