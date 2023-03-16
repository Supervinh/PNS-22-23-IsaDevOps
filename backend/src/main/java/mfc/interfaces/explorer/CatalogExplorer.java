package mfc.interfaces.explorer;

import mfc.exceptions.PayoffNotFoundException;
import mfc.entities.Customer;
import mfc.entities.Payoff;

import java.util.Optional;
import java.util.Set;

public interface CatalogExplorer {
    Set<Payoff> availablePayoffs(Customer customer);

    Set<Payoff> exploreCatalogue(Customer customer, String search);

    Optional<Payoff> findPayoff(String payOffName, String storeName) throws PayoffNotFoundException;
}

