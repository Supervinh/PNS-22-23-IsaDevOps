package mfc.repositories;

import mfc.entities.Customer;
import mfc.entities.PayoffPurchase;
import mfc.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayoffPurchaseRepository extends JpaRepository<PayoffPurchase, Long> {
    Optional<PayoffPurchase> findByCustomerAndStoreAndName(Customer customer, Store store, String name);

}