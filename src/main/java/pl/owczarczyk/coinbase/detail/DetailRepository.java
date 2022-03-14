package pl.owczarczyk.coinbase.detail;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DetailRepository extends JpaRepository<Detail, UUID> {
}
