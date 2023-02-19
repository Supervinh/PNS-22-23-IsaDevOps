package mfc.components;

import mfc.POJO.Customer;
import mfc.POJO.PayOff;
import mfc.POJO.Store;
import mfc.exceptions.AlreadyExistingPayoffException;
import mfc.exceptions.NegativeCostException;
import mfc.exceptions.NegativePointCostException;
import mfc.exceptions.PayoffNotFoundException;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.modifier.CatalogModifier;
import mfc.repositories.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CatalogRegistry implements CatalogExplorer, CatalogModifier {
    private final CatalogRepository catalogRepository;

    @Autowired
    public CatalogRegistry(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public Set<PayOff> availablePayoffs(Customer customer) {
        return catalogRepository.available(customer.getFidelityPoints());
    }

    @Override
    public Set<PayOff> exploreCatalogue(Customer customer, String search) {
        return catalogRepository.explore(search);
    }

    @Override
    public PayOff addPayOff(String name, double cost, int pointCost, Store store) throws NegativeCostException, NegativePointCostException, AlreadyExistingPayoffException {
        if (cost <= 0) throw new NegativeCostException();
        if (pointCost <= 0) throw new NegativePointCostException();
        if (!catalogRepository.explore(name).isEmpty()) throw new AlreadyExistingPayoffException();
        PayOff payOff = new PayOff(name, cost, pointCost, store);
        catalogRepository.save(payOff, payOff.getId());
        return payOff;
    }

    @Override
    public PayOff editPayOff(PayOff payOff, Store store, double cost, int pointCost) throws NegativeCostException, NegativePointCostException, PayoffNotFoundException {
        if (cost <= 0) throw new NegativeCostException();
        if (pointCost <= 0) throw new NegativePointCostException();
        if (catalogRepository.existsById(payOff.getId())) {
            payOff.setCost(cost);
            payOff.setPointCost(pointCost);
            catalogRepository.save(payOff, payOff.getId());
            return payOff;
        } else throw new PayoffNotFoundException();
    }

    @Override
    public PayOff editPayOff(PayOff payOff, Store store, double cost) throws NegativeCostException, PayoffNotFoundException {
        if (cost <= 0) throw new NegativeCostException();
        if (catalogRepository.existsById(payOff.getId())) {
            payOff.setCost(cost);
            catalogRepository.save(payOff, payOff.getId());
            return payOff;
        } else throw new PayoffNotFoundException();
    }

    @Override
    public PayOff editPayOff(PayOff payOff, Store store, int pointCost) throws NegativePointCostException, PayoffNotFoundException {
        if (pointCost <= 0) throw new NegativePointCostException();
        if (catalogRepository.existsById(payOff.getId())) {
            payOff.setPointCost(pointCost);
            catalogRepository.save(payOff, payOff.getId());
            return payOff;
        } else throw new PayoffNotFoundException();
    }
}
