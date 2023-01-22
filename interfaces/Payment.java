package interfaces;

import POJO.Customer;
import interfaces.Exceptions.NegativeRefillException;
import interfaces.Exceptions.NoCreditCardException;
import interfaces.Exceptions.PaymentException;

public interface Payment {
    boolean refillBalance(Customer user, double amount) throws NoCreditCardException, NegativeRefillException, PaymentException;
}
