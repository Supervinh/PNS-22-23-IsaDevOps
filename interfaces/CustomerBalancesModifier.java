package interfaces;

import POJO.Customer;
import interfaces.Exceptions.InsufficientBalanceException;
import interfaces.Exceptions.NegativePointCostException;

public interface CustomerBalancesModifier {
    Customer editBalance(Customer customer, double balanceChange) throws InsufficientBalanceException;

    Customer editFidelityPoints(Customer customer, double balanceChange) throws NegativePointCostException;
}
