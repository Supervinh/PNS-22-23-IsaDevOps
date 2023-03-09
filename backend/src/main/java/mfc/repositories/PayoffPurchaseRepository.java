package mfc.repositories;

import mfc.POJO.Admin;
import mfc.POJO.PayoffPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PayoffPurchaseRepository extends JpaRepository<PayoffPurchase, Long> {
}