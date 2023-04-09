package mfc.components.registries;

import mfc.entities.Customer;
import mfc.entities.Payoff;
import mfc.entities.Purchase;
import mfc.entities.Store;
import mfc.exceptions.*;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.explorer.PurchaseFinder;
import mfc.interfaces.modifier.CatalogModifier;
import mfc.repositories.PayoffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class CatalogRegistry implements CatalogExplorer, CatalogModifier {

    /**
     * PayoffRepository
     */
    private final PayoffRepository payoffRepository;
    private final PurchaseFinder purchaseFinder;

    @Autowired
    public CatalogRegistry(PayoffRepository payoffRepository, PurchaseFinder purchaseFinder) {
        this.payoffRepository = payoffRepository;
        this.purchaseFinder = purchaseFinder;
    }

    @Override
    public void isAvailablePayoff(Customer customer, Payoff payoff) throws InsufficientBalanceException, VFPExpiredException, NoPreviousPurchaseException {
        if (payoff.isVfp() && customer.getVfp().isBefore(LocalDate.now())) {
            throw new VFPExpiredException();
        } else if (payoff.getPointCost() > customer.getFidelityPoints()) {
            throw new InsufficientBalanceException();
        }
        purchaseFinder.lookUpPurchasesByCustomer(customer).stream().filter(e -> e.getStore().equals(payoff.getStore())).findAny().orElseThrow(NoPreviousPurchaseException::new);
    }

    @Override
    public Set<Payoff> showAvailablePayoffs(Customer customer) {
        Set<Store> stores = purchaseFinder.lookUpPurchasesByCustomer(customer).stream().map(Purchase::getStore).collect(Collectors.toSet());
        return payoffRepository.findAll().stream().filter(e -> e.getPointCost() <= customer.getFidelityPoints()) //Solde en points suffisants
                .filter(e -> !customer.getVfp().isBefore(LocalDate.now()) || !e.isVfp()) //Soit le client est vfp, soit la payOff n'est pas vfp
                .filter(e -> stores.contains(e.getStore())) //Le client a déjà acheté dans le magasin
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Payoff> exploreCatalogue(Customer customer, String search) {
        return payoffRepository.explore(search);
    }

    @Override
    public Optional<Payoff> findPayoff(String payoffName, String storeName) {
        return payoffRepository.findPayoffByNameAndStore_Name(payoffName, storeName);
    }

    @Override
    public Payoff addPayOff(String name, double cost, int pointCost, Store store, boolean isVfp) throws NegativeCostException, NegativePointCostException, AlreadyExistingPayoffException {
        if (cost < 0) throw new NegativeCostException();
        if (pointCost < 0) throw new NegativePointCostException();
        if (!payoffRepository.explore(name).isEmpty()) throw new AlreadyExistingPayoffException();
        Payoff payOff = new Payoff(name, cost, pointCost, store, isVfp);
        payoffRepository.save(payOff);
        return payOff;
    }

    @Override
    public Payoff editPayOff(Payoff payOff, Optional<Double> cost, Optional<Integer> pointCost, boolean isVfp) throws NegativeCostException, NegativePointCostException, PayoffNotFoundException {
        Payoff payoff = payoffRepository.findPayoffByNameAndStore_Name(payOff.getName(), payOff.getStore().getName()).orElseThrow(PayoffNotFoundException::new);
        payoff.setVfp(isVfp);
        if (cost.isPresent()) {
            if (cost.get() < 0) throw new NegativeCostException();
            payoff.setCost(cost.get());
        }
        if (pointCost.isPresent()) {
            if (pointCost.get() < 0) throw new NegativePointCostException();
            payoff.setPointCost(pointCost.get());
        }
        payoffRepository.save(payoff);
        return payoff;
    }

    @Override
    public void deletePayoff(Payoff payOff) throws PayoffNotFoundException {
        if (payoffRepository.existsById(payOff.getId())) {
            payoffRepository.deleteById(payOff.getId());
        } else throw new PayoffNotFoundException();
    }
}
