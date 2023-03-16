package mfc.interfaces.modifier;

import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.InsufficientBalanceException;
import mfc.exceptions.NegativePointCostException;
import mfc.entities.Customer;

import java.time.LocalDate;

public interface CustomerBalancesModifier {
    Customer editVFP(Customer customer, LocalDate localDate) throws CustomerNotFoundException;

    Customer editBalance(Customer customer, double balanceChange) throws InsufficientBalanceException, CustomerNotFoundException;

    Customer editFidelityPoints(Customer customer, int fidelityPointsBalanceChange) throws NegativePointCostException, CustomerNotFoundException;
}
