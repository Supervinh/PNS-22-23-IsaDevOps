package mfc.components.registries;

import mfc.entities.Customer;
import mfc.entities.Payoff;
import mfc.entities.PayoffPurchase;
import mfc.entities.Store;
import mfc.interfaces.explorer.PayOffPurchaseFinder;
import mfc.interfaces.modifier.PayOffPurchaseRecording;
import mfc.repositories.PayoffPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class PayoffPurchaseRegistry implements PayOffPurchaseRecording, PayOffPurchaseFinder {

    private final PayoffPurchaseRepository payoffPurchaseRepository;

    @Autowired
    public PayoffPurchaseRegistry(PayoffPurchaseRepository payoffPurchaseRepository) {
        this.payoffPurchaseRepository = payoffPurchaseRepository;
    }

    @Override
    public Set<PayoffPurchase> lookUpPayOffPurchasesByStore(Store store) {
        return payoffPurchaseRepository.findAll().stream()
                .filter(payoffPurchase -> store.equals(payoffPurchase.getStore())).collect(Collectors.toSet());
    }

    @Override
    public Set<PayoffPurchase> lookUpPayOffPurchases() {
        return new HashSet<>(payoffPurchaseRepository.findAll());
    }

    @Override
    public PayoffPurchase recordPayOffPurchase(Payoff transaction, Customer user) {
        return payoffPurchaseRepository.save(new PayoffPurchase(transaction, user));
    }
}
