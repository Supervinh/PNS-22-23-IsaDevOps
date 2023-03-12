package mfc.components.registries;

import mfc.exceptions.AlreadyExistingPayoffException;
import mfc.exceptions.NegativeCostException;
import mfc.exceptions.NegativePointCostException;
import mfc.exceptions.PayoffNotFoundException;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.modifier.CatalogModifier;
import mfc.pojo.Customer;
import mfc.pojo.Payoff;
import mfc.pojo.Store;
import mfc.repositories.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class CatalogRegistry implements CatalogExplorer, CatalogModifier {
    private final CatalogRepository catalogRepository;

    @Autowired
    public CatalogRegistry(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public Set<Payoff> availablePayoffs(Customer customer) {
        return catalogRepository.available(customer.getFidelityPoints());
    }

    @Override
    public Set<Payoff> exploreCatalogue(Customer customer, String search) {
        return catalogRepository.explore(search);
    }

    @Override
    public Optional<Payoff> findPayoff(String payoffName, String storeName) throws PayoffNotFoundException {
        return catalogRepository.findPayoff(payoffName, storeName);
    }

    @Override
    public Payoff addPayOff(String name, double cost, int pointCost, Store store) throws NegativeCostException, NegativePointCostException, AlreadyExistingPayoffException {
        if (cost <= 0) throw new NegativeCostException();
        if (pointCost <= 0) throw new NegativePointCostException();
        if (!catalogRepository.explore(name).isEmpty()) throw new AlreadyExistingPayoffException();
        Payoff payOff = new Payoff(name, cost, pointCost, store);
        catalogRepository.save(payOff, payOff.getId());
        return payOff;
    }

    @Override
    public Payoff editPayOff(Payoff payOff, Optional<Double> cost, Optional<Integer> pointCost) throws NegativeCostException, NegativePointCostException, PayoffNotFoundException {
        Payoff payoff = catalogRepository.findPayoff(payOff.getName(), payOff.getStore().getName()).orElseThrow(PayoffNotFoundException::new);
        if (cost.isPresent()) {
            if (cost.get() < 0) throw new NegativeCostException();
            payoff.setCost(cost.get());
        }
        if (pointCost.isPresent()) {
            if (pointCost.get() < 0) throw new NegativePointCostException();
            payoff.setPointCost(pointCost.get());
        }
        catalogRepository.save(payoff, payoff.getId());
        return payoff;
    }

    @Override
    public Payoff deletePayoff(Payoff payOff) throws PayoffNotFoundException {
        if (catalogRepository.existsById(payOff.getId())) {
            catalogRepository.deleteById(payOff.getId());
            return payOff;
        } else throw new PayoffNotFoundException();
    }
}
