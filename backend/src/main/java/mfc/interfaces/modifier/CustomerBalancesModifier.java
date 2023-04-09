package mfc.interfaces.modifier;

import mfc.entities.Customer;
import mfc.exceptions.AccountNotFoundException;
import mfc.exceptions.InsufficientBalanceException;
import mfc.exceptions.NegativePointCostException;

import java.time.LocalDate;

public interface CustomerBalancesModifier {
    Customer editVFP(Customer customer, LocalDate localDate) throws AccountNotFoundException;

    Customer editBalance(Customer customer, double balanceChange) throws InsufficientBalanceException, AccountNotFoundException;

    Customer editFidelityPoints(Customer customer, int fidelityPointsBalanceChange) throws NegativePointCostException, AccountNotFoundException;
}
