package mfc.components.registries;

import mfc.POJO.Customer;
import mfc.POJO.Purchase;
import mfc.POJO.Store;
import mfc.interfaces.explorer.PurchaseFinder;
import mfc.interfaces.modifier.PurchaseRecording;
import mfc.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Component
@Transactional
public class PurchaseRegistry implements PurchaseRecording, PurchaseFinder {
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseRegistry(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public Purchase recordPurchase(Customer customer, double cost, Store store) {
        Purchase newPurchase = new Purchase(cost, customer, store);
        purchaseRepository.save(newPurchase);
        return newPurchase;
    }

    @Override
    public Set<Purchase> lookUpPurchasesByStore(Store store) {
        return null;
    }

    @Override
    public Set<Purchase> lookUpPayPurchases() {
        return null;
    }

    @Override
    public Set<Purchase> lookUpPurchasesByCustomer(Customer customer) {
        return null;
    }

    @Override
    public Optional<Purchase> findById(UUID id) {
        return StreamSupport.stream(purchaseRepository.findAll().spliterator(), false)
                .filter(purchase -> id.equals(purchase.getId())).findAny();
    }

}