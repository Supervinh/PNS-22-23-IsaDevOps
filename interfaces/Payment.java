package interfaces;

import POJO.Customer;
import interfaces.Exceptions.NegativeRefillException;
import interfaces.Exceptions.PaymentException;

public interface Payment {

    boolean refillBalance(Customer user, double amount) throws NegativeRefillException, PaymentException;

}
