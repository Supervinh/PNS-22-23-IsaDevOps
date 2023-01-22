package interfaces;

import POJO.Customer;
import POJO.PayOff;

import java.util.Set;

public interface CatalogExplorator {

    Set<PayOff> availablePayoffs(Customer customer);
    Set<PayOff> exploreCatalogue(Customer customer);

}
