package mfc.components;
import mfc.POJO.Customer;
import mfc.exceptions.*;
import mfc.interfaces.Bank;
import mfc.interfaces.Payment;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class PaymentHandler implements Payment {
    @Autowired
    private Bank bank;
    @Autowired
    private CustomerBalancesModifier customerBalancesModifier;

    @Override
    public Customer refillBalance(Customer user, double amount) throws NoCreditCardException, NegativeRefillException, PaymentException {
        if (amount <= 0)
            throw new NegativeRefillException();
        boolean status = bank.pay(user, amount);
        if (!status)
            throw new PaymentException();
        if (user.getCreditCard().equals(""))
            throw new NoCreditCardException();
        try {
            user = customerBalancesModifier.editBalance(user, amount);
        } catch (CustomerNotFoundException | InsufficientBalanceException e) {
            e.printStackTrace();
        }
        return user;
    }
}