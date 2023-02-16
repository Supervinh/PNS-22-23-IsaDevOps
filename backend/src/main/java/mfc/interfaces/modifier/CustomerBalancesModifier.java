package mfc.interfaces.modifier;

import mfc.POJO.Customer;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.InsufficientBalanceException;
import mfc.exceptions.NegativePointCostException;

public interface CustomerBalancesModifier {
    Customer editBalance(Customer customer, double balanceChange) throws InsufficientBalanceException, CustomerNotFoundException;

    Customer editFidelityPoints(Customer customer, int fidelityPointsBalanceChange) throws NegativePointCostException, CustomerNotFoundException;
}
