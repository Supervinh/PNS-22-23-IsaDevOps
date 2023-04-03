package mfc.interfaces.explorer;

import mfc.entities.Customer;
import mfc.entities.Payoff;
import mfc.exceptions.InsufficientBalanceException;
import mfc.exceptions.NoPreviousPurchaseException;
import mfc.exceptions.PayoffNotFoundException;
import mfc.exceptions.VFPExpiredException;

import java.util.Optional;
import java.util.Set;

public interface CatalogExplorer {
    void isAvailablePayoff(Customer customer, Payoff store) throws InsufficientBalanceException, VFPExpiredException, NoPreviousPurchaseException;

    Set<Payoff> showAvailablePayoffs(Customer customer);

    Set<Payoff> exploreCatalogue(Customer customer, String search);

    Optional<Payoff> findPayoff(String payOffName, String storeName) throws PayoffNotFoundException;
}

