package mfc.interfaces.modifier;

import mfc.POJO.Customer;
import mfc.interfaces.exceptions.CustomerNotFoundException;
import mfc.interfaces.exceptions.InsufficientBalanceException;
import mfc.interfaces.exceptions.NegativePointCostException;

public interface CustomerBalancesModifier {
    Customer editBalance(Customer customer, double balanceChange) throws InsufficientBalanceException, CustomerNotFoundException;

    Customer editFidelityPoints(Customer customer, int fidelityPointsBalanceChange) throws NegativePointCostException, CustomerNotFoundException;
}
