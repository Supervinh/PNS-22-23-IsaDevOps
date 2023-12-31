package mfc.interfaces;

import mfc.entities.Customer;
import mfc.exceptions.NegativeRefillException;
import mfc.exceptions.NoCreditCardException;
import mfc.exceptions.PaymentException;

public interface Payment {
    Customer refillBalance(Customer user, double amount) throws NoCreditCardException, NegativeRefillException, PaymentException;
}
