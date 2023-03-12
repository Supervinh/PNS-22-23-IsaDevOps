package mfc.interfaces;

import mfc.exceptions.NegativeRefillException;
import mfc.exceptions.NoCreditCardException;
import mfc.exceptions.PaymentException;
import mfc.pojo.Customer;

public interface Payment {
    Customer refillBalance(Customer user, double amount) throws NoCreditCardException, NegativeRefillException, PaymentException;
}
