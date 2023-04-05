package mfc.repositories;

import mfc.entities.PayoffPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PayoffPurchaseRepository extends JpaRepository<PayoffPurchase, Long> {
    Optional<PayoffPurchase> findPayoffPurchaseById(Long id);

    Set<PayoffPurchase> findPayoffsPurchasesByCustomer_Id(Long id);
}