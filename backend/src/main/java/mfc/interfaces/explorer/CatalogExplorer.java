package mfc.interfaces.explorer;

import mfc.POJO.Customer;
import mfc.POJO.PayOff;

import java.util.Set;

public interface CatalogExplorer {
    Set<PayOff> availablePayoffs(Customer customer);

    Set<PayOff> exploreCatalogue(Customer customer, String search);
}

