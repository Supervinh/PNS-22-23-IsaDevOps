package mfc.components.registries;

import mfc.entities.Customer;
import mfc.entities.Purchase;
import mfc.entities.Store;
import mfc.interfaces.explorer.PurchaseFinder;
import mfc.interfaces.modifier.PurchaseRecording;
import mfc.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return purchaseRepository.findAll().stream()
                .filter(purchase -> store.equals(purchase.getStore())).collect(Collectors.toSet());
    }

    @Override
    public Set<Purchase> lookUpPayPurchases() {
        return null;
    }

    @Override
    public Set<Purchase> lookUpPurchasesByCustomer(Customer customer) {
        return purchaseRepository.findAll().stream()
                .filter(purchase -> customer.equals(purchase.getCustomer())).collect(Collectors.toSet());
    }

}