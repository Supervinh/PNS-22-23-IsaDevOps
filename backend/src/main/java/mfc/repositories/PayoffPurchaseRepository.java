package mfc.repositories;

import mfc.entities.PayoffPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayoffPurchaseRepository extends JpaRepository<PayoffPurchase, Long> {
    Optional<PayoffPurchase> findPayoffPurchaseById(Long id);
}