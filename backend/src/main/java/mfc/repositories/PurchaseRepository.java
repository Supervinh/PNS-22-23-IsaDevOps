package mfc.repositories;

import mfc.entities.Customer;
import mfc.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Set<Purchase> findPurchasesByCustomer(Customer customer);
}
