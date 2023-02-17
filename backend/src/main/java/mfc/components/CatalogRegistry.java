package mfc.components;

import mfc.POJO.*;
import mfc.exceptions.CredentialsException;
import mfc.exceptions.NegativeCostException;
import mfc.exceptions.NegativePointCostException;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.modifier.CatalogModifier;
import mfc.repositories.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

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
    public boolean addPayOff(String name, double cost, int pointCost, Store store, StoreOwner authorization) throws NegativeCostException, NegativePointCostException, CredentialsException {
        return false;
    }

    @Override
    public boolean editPayOff(PayOff payOff, Store store, double cost, int pointCost, StoreOwner authorization) throws NegativeCostException, NegativePointCostException, CredentialsException {
        return false;
    }

    @Override
    public boolean editPayOff(PayOff payOff, Store store, double cost, StoreOwner authorization) throws NegativeCostException, CredentialsException {
        return false;
    }

    @Override
    public boolean editPayOff(PayOff payOff, Store store, int pointCost, StoreOwner authorization) throws NegativePointCostException, CredentialsException {
        return false;
    }

    @Override
    public boolean addPayOffAdmin(String name, double cost, int pointCost, Admin authorization) throws NegativeCostException, NegativePointCostException {
        return false;
    }

    @Override
    public boolean editPayOff(PayOff payOff, Store store, double cost, int pointCost, Admin authorization) throws NegativeCostException, NegativePointCostException {
        return false;
    }

    @Override
    public boolean editPayOff(PayOff payOff, Store store, double cost, Admin authorization) throws NegativeCostException {
        return false;
    }

    @Override
    public boolean editPayOff(PayOff payOff, Store store, int pointCost, Admin authorization) throws NegativePointCostException {
        return false;
    }
}
