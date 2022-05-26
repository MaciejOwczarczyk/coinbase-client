package pl.owczarczyk.coinbase.ledger;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LedgerDetailConverter implements AttributeConverter<LedgerDetail, String> {

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(LedgerDetail ledgerDetail) {
        if (ledgerDetail == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        if (ledgerDetail.getTransferId() != null && !ledgerDetail.getTransferType().isEmpty()) {
            sb.append(ledgerDetail.getTransferId());
            sb.append(SEPARATOR);
            sb.append(ledgerDetail.getTransferType());
        }
        return sb.toString();
    }

    @Override
    public LedgerDetail convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        String[] pieces = s.split(SEPARATOR);
        if (pieces.length == 0) {
            return null;
        }

        LedgerDetail ledgerDetail = new LedgerDetail();
        String ledgerId = !pieces[0].isEmpty() ? pieces[0] : null;
        if (s.contains(SEPARATOR)) {
            ledgerDetail.setTransferId(ledgerId);
            if (pieces.length >= 2 && pieces[1] != null && !pieces[1].isEmpty()) {
                ledgerDetail.setTransferType(pieces[1]);
            }
        } else {
            ledgerDetail.setTransferType(ledgerId);
        }

        return ledgerDetail;
    }
}
