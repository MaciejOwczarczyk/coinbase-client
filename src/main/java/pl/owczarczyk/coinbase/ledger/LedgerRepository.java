package pl.owczarczyk.coinbase.ledger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LedgerRepository extends JpaRepository<Ledger, UUID> {

    Ledger getLedgerById(String id);

}
