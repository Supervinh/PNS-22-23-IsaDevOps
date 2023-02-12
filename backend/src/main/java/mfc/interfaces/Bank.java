package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.interfaces.exceptions.PaymentException;

public interface Bank {
    boolean pay(Customer customer, double balance) throws PaymentException;
}

