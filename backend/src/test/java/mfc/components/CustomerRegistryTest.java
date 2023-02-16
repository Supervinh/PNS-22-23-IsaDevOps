package mfc.components;

import mfc.POJO.Customer;
import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.interfaces.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.exceptions.CustomerNotFoundException;
import mfc.interfaces.exceptions.InsufficientBalanceException;
import mfc.interfaces.exceptions.NegativePointCostException;
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

import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private CustomerProfileModifier customerProfileModifier;

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

    @Test
    public void cannotEditBalanceWithNegativeValue() throws Exception {
        Customer customer = customerRegistration.register(mail, name, password);
        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            customerBalancesModifier.editBalance(customer, -100);
        });
    }

    @Test
    public void editBalanceOfUnknownCustomer() throws Exception {
        Customer customer = new Customer(mail, name, password);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerBalancesModifier.editBalance(customer, 100);
        });
    }

    @Test
    public void canEditFidelityPoints() throws Exception {
        Customer customer = customerRegistration.register(mail, name, password);
        customerBalancesModifier.editFidelityPoints(customer, 100);
        assertEquals(100, customer.getFidelityPoints());
    }

    @Test
    public void cannotEditFidelityPointsWithNegativeValue() throws Exception {
        Customer customer = customerRegistration.register(mail, name, password);
        Assertions.assertThrows(NegativePointCostException.class, () -> {
            customerBalancesModifier.editFidelityPoints(customer, -100);
        });
    }

    @Test
    public void editFidelityPointsOfUnknownCustomer() throws Exception {
        Customer customer = new Customer(mail, name, password);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerBalancesModifier.editFidelityPoints(customer, 100);
        });
    }

    @Test
    public void canRecordMatriculation() throws Exception {
        Customer customer = customerRegistration.register(mail, name, password);
        String matriculation = customer.getMatriculation();
        customerProfileModifier.recordMatriculation(customer, "AB-123-CD");
        assertEquals("AB-123-CD", customer.getMatriculation());
        assertNotEquals(matriculation, customer.getMatriculation());
    }

    @Test
    public void cannotRecordMatriculationOfUnknownCustomer() throws Exception {
        Customer customer = new Customer(mail, name, password);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerProfileModifier.recordMatriculation(customer, "AB-123-CD");
        });
    }

    @Test
    public void canRecordCreditCard() throws Exception {
        Customer customer = customerRegistration.register(mail, name, password);
        String creditCard = customer.getCreditCard();
        customerProfileModifier.recordCreditCard(customer, "1234 5678 9012 3456");
        assertEquals("1234 5678 9012 3456", customer.getCreditCard());
        assertNotEquals(creditCard, customer.getCreditCard());
    }

    @Test
    public void cannotRecordCreditCardOfUnknownCustomer() throws Exception {
        Customer customer = new Customer(mail, name, password);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerProfileModifier.recordCreditCard(customer, "1234 5678 9012 3456");
        });
    }

//    @Test
//    public void canRecordFavoriteStore() throws Exception {
//        Customer customer = customerRegistration.register(mail, name, password);
//        List<Store> favoriteStores = customer.getFavoriteStores();
//        StoreOwner storeOwner = new StoreOwner("Owner", "owner@store.com","password");
//        Store store = new Store("Carrefour", new HashMap<String, String>(), storeOwner);
//        customerProfileModifier.recordNewFavoriteStore(customer, store);
//        assertEquals("Carrefour", customer.getFavoriteStores().get(0).getName());
//        assertNotEquals(favoriteStores, customer.getFavoriteStores());
//    }
}