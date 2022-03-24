package pl.owczarczyk.coinbase.ledger;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LedgerDetailRepository extends JpaRepository<LedgerDetail, UUID> {
}
