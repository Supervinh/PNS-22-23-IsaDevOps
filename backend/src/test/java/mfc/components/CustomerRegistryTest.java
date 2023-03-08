package mfc.components;

import mfc.POJO.Customer;
import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.exceptions.*;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.interfaces.modifier.CustomerProfileModifier;
import mfc.interfaces.modifier.CustomerRegistration;
import mfc.repositories.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRegistryTest {

    private final String mail = "Mark@pns.fr";
    private final String name = "Mark";
    private final String password = "password";
    private String[][] schedule = new String[7][2];
    private static boolean setUpIsDone = false;
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


    @BeforeEach
    void setUp() {
        if(setUpIsDone){
            String[][] setupList = new String[7][2];
            for(int i = 0; i <= 6; i++){
                setupList[i][0] = "7h00";
                setupList[i][1] = "19h30";
            }
            schedule = setupList;
        }
        customerRepository.deleteAll();
    }

    @Test
    void unknownCustomer() {
        assertFalse(customerRepository.findByMail(mail).isPresent());
    }

    @Test
    void registerCustomer() throws Exception {
        Customer returned = customerRegistration.register(name, mail, password);
        Optional<Customer> customer = customerFinder.findCustomerByMail(mail);
        assertTrue(customer.isPresent());
        Customer mark = customer.get();
        assertEquals(mark, returned);
        assertEquals(mark, customerFinder.findCustomerById(returned.getId()).get());
        assertEquals(name, mark.getName());
        assertEquals(mail, mark.getMail());
    }

    @Test
    void cannotRegisterTwice() throws Exception {
        customerRegistration.register(name, mail, password);
        Assertions.assertThrows(AlreadyExistingAccountException.class, () -> {
            customerRegistration.register(name, mail, password);
        });
    }

    @Test
    void canFindByMail() throws Exception {
        customerRegistration.register(name, mail, password);
        Optional<Customer> customer = customerFinder.findCustomerByMail(mail);
        assertTrue(customer.isPresent());
        assertEquals(name, customer.get().getName());
    }

    @Test
    void unknownCustomerByMail() {
        assertFalse(customerFinder.findCustomerByMail(mail).isPresent());
    }

    @Test
    void canFindById() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password);
        Optional<Customer> customer2 = customerFinder.findCustomerById(customer.getId());
        assertTrue(customer2.isPresent());
    }

    @Test

    void unknownCustomerById() {
        Customer customer = new Customer(name, mail, password);
        assertFalse(customerFinder.findCustomerById(customer.getId()).isPresent());
    }

    @Test

    void canEditBalance() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password);
        customerBalancesModifier.editBalance(customer, 100);
        assertEquals(100, customer.getBalance());
    }

    @Test
    void cannotEditBalanceWithNegativeValue() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password);
        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            customerBalancesModifier.editBalance(customer, -100);
        });
    }

    @Test
    void editBalanceOfUnknownCustomer() throws Exception {
        Customer customer = new Customer(name, mail,  password);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerBalancesModifier.editBalance(customer, 100);
        });
    }

    @Test
    void canEditFidelityPoints() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password);
        customerBalancesModifier.editFidelityPoints(customer, 100);
        assertEquals(100, customer.getFidelityPoints());
    }

    @Test
    void cannotEditFidelityPointsWithNegativeValue() throws Exception {
        Customer customer = customerRegistration.register(name, mail,password);
        Assertions.assertThrows(NegativePointCostException.class, () -> {
            customerBalancesModifier.editFidelityPoints(customer, -100);
        });
    }

    @Test
    void editFidelityPointsOfUnknownCustomer() throws Exception {
        Customer customer = new Customer(name, mail, password);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerBalancesModifier.editFidelityPoints(customer, 100);
        });
    }

    @Test
    void canRecordMatriculation() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password);
        String matriculation = customer.getMatriculation();
        customerProfileModifier.recordMatriculation(customer, "AB-123-CD");
        assertEquals("AB-123-CD", customer.getMatriculation());
        assertNotEquals(matriculation, customer.getMatriculation());
    }

    @Test
    void cannotRecordMatriculationOfUnknownCustomer() throws Exception {
        Customer customer = new Customer(name, mail, password);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerProfileModifier.recordMatriculation(customer, "AB-123-CD");
        });
    }

    @Test
    void canRecordCreditCard() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password);
        String creditCard = customer.getCreditCard();
        customerProfileModifier.recordCreditCard(customer, "1234 5678 9012 3456");
        assertEquals("1234 5678 9012 3456", customer.getCreditCard());
        assertNotEquals(creditCard, customer.getCreditCard());
    }

    @Test
    void cannotRecordCreditCardOfUnknownCustomer() throws Exception {
        Customer customer = new Customer(name, mail, password);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerProfileModifier.recordCreditCard(customer, "1234 5678 9012 3456");
        });
    }

    @Test
    void canRecordFavoriteStore() throws Exception {
        Customer customer = customerRegistration.register(name, mail,  password);
        List<Store> favoriteStores = customer.getFavoriteStores();
        StoreOwner storeOwner = new StoreOwner("Owner", "owner@store.com", "password");
        Store store = new Store("Carrefour", schedule, storeOwner);
        assertFalse(favoriteStores.contains(store));
        customerProfileModifier.recordNewFavoriteStore(customer, store);
        assertEquals("Carrefour", customer.getFavoriteStores().get(0).getName());
    }

    @Test
    void cannotRecordFavoriteStoreOfUnknownCustomer() {
        Customer customer = new Customer(name, mail, password);
        StoreOwner storeOwner = new StoreOwner("Owner", "owner@store.com", "password");
        Store store = new Store("Carrefour", schedule, storeOwner);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerProfileModifier.recordNewFavoriteStore(customer, store);
        });
    }

    @Test
    void favoriteStoreAlreadyRegistered() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password);
        StoreOwner storeOwner = new StoreOwner("Owner", "owner@store.com", "password");
        Store store = new Store("Carrefour", schedule, storeOwner);
        customerProfileModifier.recordNewFavoriteStore(customer, store);
        Assertions.assertThrows(StoreAlreadyRegisteredException.class, () -> {
            customerProfileModifier.recordNewFavoriteStore(customer, store);
        });
    }

    @Test
    void canRemoveFavoriteStore() throws Exception {
        Customer customer = customerRegistration.register(name, mail,password);
        StoreOwner storeOwner = new StoreOwner("Owner", "owner@store.com", "password");
        Store store = new Store("Carrefour", schedule, storeOwner);
        customerProfileModifier.recordNewFavoriteStore(customer, store);
        assertTrue(customer.getFavoriteStores().contains(store));
        customerProfileModifier.removeFavoriteStore(customer, store);
        assertFalse(customer.getFavoriteStores().contains(store));
    }

    @Test
    void cannotRemoveFavoriteStoreOfUnknownCustomer() {
        Customer customer = new Customer(name, mail, password);
        StoreOwner storeOwner = new StoreOwner("Owner", "owner@store.com", "password");
        Store store = new Store("Carrefour",schedule,  storeOwner);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> {
            customerProfileModifier.removeFavoriteStore(customer, store);
        });
    }

    @Test
    void cannotRemoveFavoriteStoreNotRegistered() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password);
        StoreOwner storeOwner = new StoreOwner("Owner", "owner@store.com", "password");
        Store store = new Store("Carrefour", schedule, storeOwner);
        Assertions.assertThrows(StoreNotFoundException.class, () -> {
            customerProfileModifier.removeFavoriteStore(customer, store);
        });
    }

}