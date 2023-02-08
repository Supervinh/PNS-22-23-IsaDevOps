package interfaces;

import POJO.Customer;
import interfaces.Exceptions.PaymentException;

public interface Bank {
    boolean pay(Customer customer, double balance) throws PaymentException;
}

