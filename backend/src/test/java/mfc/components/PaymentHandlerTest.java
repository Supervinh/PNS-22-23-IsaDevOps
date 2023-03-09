package mfc.components;

import mfc.POJO.Customer;
import mfc.connectors.BankProxy;
import mfc.exceptions.NegativeRefillException;
import mfc.exceptions.NoCreditCardException;
import mfc.exceptions.PaymentException;
import mfc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class PaymentHandlerTest {

    Customer allowed;
    Customer denied;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PaymentHandler paymentHandler;
    @MockBean
    private BankProxy bankProxy;


    @BeforeEach
    void setUp() {
        try {
            customerRepository.deleteAll();
            String creditCardA = "0123456789";
            String passwordA = "password";
            String nameA = "Mark";
            String mailA = "Mark@pns.fr";
            allowed = new Customer(nameA, mailA, passwordA, creditCardA);
            String mailD = "AMark@pns.fr";
            String nameD = "AMark";
            String passwordD = "apassword";
            String creditCardD = "0786610925";
            denied = new Customer(nameD, mailD, passwordD, creditCardD);
            customerRepository.save(allowed);
            customerRepository.save(denied);
        } catch (Exception e) {
            e.printStackTrace();
        }
        when(bankProxy.pay(eq(allowed), anyDouble())).thenReturn(true);
        when(bankProxy.pay(eq(denied), anyDouble())).thenReturn(false);
    }

    @Test
    void refillBalance() throws NoCreditCardException, PaymentException, NegativeRefillException {
        Customer res = paymentHandler.refillBalance(allowed, 10);
        assertEquals(10, res.getBalance());
    }

    @Test
    void refillNegativeBalance() {
        assertThrows(NegativeRefillException.class, () -> paymentHandler.refillBalance(allowed, -10));
    }

    @Test
    void refillNoCreditCardBalance() {
        allowed.setCreditCard("");
        assertThrows(NoCreditCardException.class, () -> paymentHandler.refillBalance(allowed, 10));
    }

    @Test
    void refillDeniedBalance() {
        assertThrows(PaymentException.class, () -> paymentHandler.refillBalance(denied, 10));
    }
}
