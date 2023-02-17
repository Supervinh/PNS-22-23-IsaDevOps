package mfc.components;

import mfc.POJO.Customer;
import mfc.exceptions.*;
import mfc.interfaces.Bank;
import mfc.interfaces.Payment;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentHandler implements Payment {

    @Autowired
    private Bank bank;

    @Autowired
    private CustomerBalancesModifier customerBalancesModifier;

    @Override
    public Customer refillBalance(Customer user, double amount) throws NoCreditCardException, NegativeRefillException, PaymentException {
        Customer res = null;
        if (amount <= 0)
            throw new NegativeRefillException();
        boolean status = bank.pay(user, amount); //TODO Register refill in user
        if (!status)
            throw new PaymentException();
        try {
            res = customerBalancesModifier.editBalance(user, amount);
        } catch (CustomerNotFoundException | InsufficientBalanceException e) {
            e.printStackTrace();
        }
        return res;
    }
}
