package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.interfaces.Exceptions.PaymentException;

public interface Bank {
    boolean pay(Customer customer, double balance) throws PaymentException;
}

