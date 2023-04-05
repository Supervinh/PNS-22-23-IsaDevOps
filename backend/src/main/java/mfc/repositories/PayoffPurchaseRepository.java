package mfc.repositories;

import mfc.entities.PayoffPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayoffPurchaseRepository extends JpaRepository<PayoffPurchase, Long> {

}