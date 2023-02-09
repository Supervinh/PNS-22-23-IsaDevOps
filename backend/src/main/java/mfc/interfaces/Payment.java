package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.interfaces.Exceptions.NegativeRefillException;
import mfc.interfaces.Exceptions.NoCreditCardException;
import mfc.interfaces.Exceptions.PaymentException;

public interface Payment {
    boolean refillBalance(Customer user, double amount) throws NoCreditCardException, NegativeRefillException, PaymentException;
}
