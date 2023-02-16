package mfc.components;

import mfc.POJO.Customer;
import mfc.interfaces.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.interfaces.modifier.CustomerProfileModifier;
import mfc.interfaces.modifier.CustomerRegistration;
import mfc.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CustomerRegistryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerRegistration customerRegistration;

    @Autowired
    private CustomerFinder customerFinder;

    @Autowired
    private CustomerBalancesModifier customerBalancesModifier;

    private String mail = "Mark@pns.fr";
    private String name = "Mark";
    private String password = "password";


    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    public void unknownCustomer() {
        assertFalse(customerRepository.findByMail(mail).isPresent());
    }

    @Test
    public void registerCustomer() throws Exception {
        Customer returned = customerRegistration.register(mail, name, password);
        Optional<Customer> customer = customerFinder.findCustomerByMail(mail);
        assertTrue(customer.isPresent());
        Customer mark = customer.get();
        assertEquals(mark, returned);
        assertEquals(mark, customerFinder.findCustomerById(returned.getId()).get());
        assertEquals(name, mark.getName());
        assertEquals(mail, mark.getMail());
    }

    @Test
    public void cannotRegisterTwice() throws Exception {
        customerRegistration.register(mail, name, password);
        Assertions.assertThrows(AlreadyExistingAccountException.class, () -> {
            customerRegistration.register(mail, name, password);
        });
    }

    @Test
    public void canFindByMail() throws Exception {
        customerRegistration.register(mail, name, password);
        Optional<Customer> customer = customerFinder.findCustomerByMail(mail);
        assertTrue(customer.isPresent());
        assertEquals(name, customer.get().getName());
    }

    @Test
    public void unknownCustomerByMail() {
        assertFalse(customerFinder.findCustomerByMail(mail).isPresent());
    }

    @Test
    public void canFindById() throws Exception {
        Customer customer = customerRegistration.register(mail, name, password);
        Optional<Customer> customer2 = customerFinder.findCustomerById(customer.getId());
        assertTrue(customer2.isPresent());
    }

    @Test
    public void unknownCustomerById() {
        Customer customer = new Customer(mail, name, password);
        assertFalse(customerFinder.findCustomerById(customer.getId()).isPresent());
    }

    @Test
    public void canEditBalance() throws Exception {
        Customer customer = customerRegistration.register(mail, name, password);
        customerBalancesModifier.editBalance(customer, 100);
        assertEquals(100, customer.getBalance());
    }
}