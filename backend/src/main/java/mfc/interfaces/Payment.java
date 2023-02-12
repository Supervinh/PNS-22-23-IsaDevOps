package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.interfaces.exceptions.NegativeRefillException;
import mfc.interfaces.exceptions.NoCreditCardException;
import mfc.interfaces.exceptions.PaymentException;

public interface Payment {
    boolean refillBalance(Customer user, double amount) throws NoCreditCardException, NegativeRefillException, PaymentException;
}
