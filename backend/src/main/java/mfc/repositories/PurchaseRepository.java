package mfc.repositories;

import mfc.POJO.PayoffPurchase;
import mfc.POJO.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
