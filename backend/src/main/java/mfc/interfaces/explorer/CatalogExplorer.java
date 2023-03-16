package mfc.interfaces.explorer;

import mfc.exceptions.PayoffNotFoundException;
import mfc.pojo.Customer;
import mfc.pojo.Payoff;

import java.util.Optional;
import java.util.Set;

public interface CatalogExplorer {
    Set<Payoff> availablePayoffs(Customer customer);

    Set<Payoff> exploreCatalogue(Customer customer, String search);

    Optional<Payoff> findPayoff(String payOffName, String storeName) throws PayoffNotFoundException;
}

