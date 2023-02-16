package mfc.interfaces;

import mfc.POJO.Customer;
import mfc.exceptions.PaymentException;

public interface Bank {
    boolean pay(Customer customer, double balance) throws PaymentException;
}

