package mfc.interfaces.modifier;

import mfc.POJO.Customer;
import mfc.interfaces.Exceptions.InsufficientBalanceException;
import mfc.interfaces.Exceptions.NegativePointCostException;

public interface CustomerBalancesModifier {
    Customer editBalance(Customer customer, double balanceChange) throws InsufficientBalanceException;

    Customer editFidelityPoints(Customer customer, double balanceChange) throws NegativePointCostException;
}
