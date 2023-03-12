package mfc.interfaces;

import mfc.exceptions.PaymentException;
import mfc.pojo.Customer;

public interface Bank {
    boolean pay(Customer customer, double balance) throws PaymentException;
}

