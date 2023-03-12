package mfc.components.registries;

import mfc.POJO.Customer;
import mfc.POJO.Payoff;
import mfc.POJO.Store;
import mfc.exceptions.AlreadyExistingPayoffException;
import mfc.exceptions.NegativeCostException;
import mfc.exceptions.NegativePointCostException;
import mfc.exceptions.PayoffNotFoundException;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.modifier.CatalogModifier;
import mfc.repositories.PayoffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Component
@Transactional
public class CatalogRegistry implements CatalogExplorer, CatalogModifier {
    private final PayoffRepository payoffRepository;

    @Autowired
    public CatalogRegistry(PayoffRepository payoffRepository) {
        this.payoffRepository = payoffRepository;
    }

    @Override
    public Set<Payoff> availablePayoffs(Customer customer) {
        return payoffRepository.available(customer.getFidelityPoints());
    }

    @Override
    public Set<Payoff> exploreCatalogue(Customer customer, String search) {
        return payoffRepository.explore(search);
    }

    @Override
    public Optional<Payoff> findPayoff(String payoffName, String storeName) throws PayoffNotFoundException {
        return payoffRepository.findPayoffByNameAndStore_Name(payoffName, storeName);
    }

    @Override
    public Payoff addPayOff(String name, double cost, int pointCost, Store store) throws NegativeCostException, NegativePointCostException, AlreadyExistingPayoffException {
        if (cost <= 0) throw new NegativeCostException();
        if (pointCost <= 0) throw new NegativePointCostException();
        if (!payoffRepository.explore(name).isEmpty()) throw new AlreadyExistingPayoffException();
        Payoff payOff = new Payoff(name, cost, pointCost, store);
        payoffRepository.save(payOff);
        return payOff;
    }

    @Override
    public Payoff editPayOff(Payoff payOff, Optional<Double> cost, Optional<Integer> pointCost) throws NegativeCostException, NegativePointCostException, PayoffNotFoundException {
        Payoff payoff = payoffRepository.findPayoffByNameAndStore_Name(payOff.getName(), payOff.getStore().getName()).orElseThrow(PayoffNotFoundException::new);
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
    public Payoff deletePayoff(Payoff payOff) throws PayoffNotFoundException {
        if (payoffRepository.existsById(payOff.getId())) {
            payoffRepository.deleteById(payOff.getId());
            return payOff;
        } else throw new PayoffNotFoundException();
    }
}
