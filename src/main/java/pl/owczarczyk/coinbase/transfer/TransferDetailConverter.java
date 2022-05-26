package pl.owczarczyk.coinbase.transfer;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
public class TransferDetailConverter implements AttributeConverter<TransferDetail, String> {

    private static final String SEPARATOR = ",";


    @Override
    public String convertToDatabaseColumn(TransferDetail transferDetail) {
        if (transferDetail == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        if (!transferDetail.getCoinbaseAccountId().isEmpty() &&
                !transferDetail.getCoinbaseTransactionId().isEmpty() &&
                !transferDetail.getCoinbasePaymentMethodId().isEmpty()) {
            sb.append(transferDetail.getCoinbaseAccountId());
            sb.append(SEPARATOR);
            sb.append(transferDetail.getCoinbaseTransactionId());
            sb.append(SEPARATOR);
            sb.append(transferDetail.getCoinbasePaymentMethodId());
        } else if (transferDetail.getCoinbaseAccountId().isEmpty() &&
                !transferDetail.getCoinbaseTransactionId().isEmpty() &&
                !transferDetail.getCoinbasePaymentMethodId().isEmpty()) {
            sb.append(SEPARATOR);
            sb.append(transferDetail.getCoinbaseTransactionId());
            sb.append(SEPARATOR);
            sb.append(transferDetail.getCoinbasePaymentMethodId());
        } else if (!transferDetail.getCoinbaseAccountId().isEmpty() &&
                transferDetail.getCoinbaseTransactionId().isEmpty() &&
                !transferDetail.getCoinbasePaymentMethodId().isEmpty()) {
            sb.append(transferDetail.getCoinbaseAccountId());
            sb.append(SEPARATOR).append(SEPARATOR);
            sb.append(transferDetail.getCoinbasePaymentMethodId());
        } else if (!transferDetail.getCoinbaseAccountId().isEmpty() &&
                !transferDetail.getCoinbaseTransactionId().isEmpty() &&
                transferDetail.getCoinbasePaymentMethodId().isEmpty()) {
            sb.append(transferDetail.getCoinbaseAccountId());
            sb.append(SEPARATOR);
            sb.append(transferDetail.getCoinbaseTransactionId());
            sb.append(SEPARATOR);
        } else {
            sb.append(SEPARATOR).append(SEPARATOR);
        }
        return sb.toString();
    }

    @Override
    public TransferDetail convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        String[] pieces = s.split(SEPARATOR);
        if (pieces.length == 0) {
            return null;
        }

        TransferDetail transferDetail = new TransferDetail();
        String accountId = !pieces[0].isEmpty() ? pieces[0] : null;
        buildObject(s, pieces, transferDetail, accountId);
        return transferDetail;
    }

    private void buildObject(String s, String[] pieces, TransferDetail transferDetail, String accountId) {
        if (Objects.isNull(accountId)) {
            transferDetail.setCoinbaseAccountId("");
            transferDetail.setCoinbaseTransactionId("");
            transferDetail.setCoinbasePaymentMethodId("");
        }
        if (s.contains(SEPARATOR)) {
            transferDetail.setCoinbaseAccountId(accountId);
            if (pieces.length == 3) {
                if (pieces[1] != null && !pieces[1].isEmpty()) {
                    transferDetail.setCoinbaseTransactionId(pieces[1]);
                } else {
                    transferDetail.setCoinbaseTransactionId("");
                }
                if (pieces[2] != null && !pieces[2].isEmpty()) {
                    transferDetail.setCoinbasePaymentMethodId(pieces[2]);
                } else {
                    transferDetail.setCoinbasePaymentMethodId("");
                }
            } else if (pieces.length == 2) {
                if (pieces[1] != null && !pieces[1].isEmpty()) {
                    transferDetail.setCoinbaseTransactionId(pieces[1]);
                } else {
                    transferDetail.setCoinbaseTransactionId("");
                }
                transferDetail.setCoinbasePaymentMethodId("");
            } else {
                transferDetail.setCoinbaseTransactionId("");
                transferDetail.setCoinbasePaymentMethodId("");
            }
        } else {
            transferDetail.setCoinbaseAccountId(accountId);
            transferDetail.setCoinbaseTransactionId("");
            transferDetail.setCoinbasePaymentMethodId("");
        }
    }
}
