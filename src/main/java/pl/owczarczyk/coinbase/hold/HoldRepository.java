package pl.owczarczyk.coinbase.hold;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.owczarczyk.coinbase.hold.Hold;

import java.util.List;
import java.util.UUID;

@Repository
public interface HoldRepository extends JpaRepository<Hold, UUID> {

    List<Hold> getAllByAccount_Id(UUID uuid);

}
