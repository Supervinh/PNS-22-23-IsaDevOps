package mfc.components.registries;

import mfc.entities.Customer;
import mfc.entities.Store;
import mfc.entities.StoreOwner;
import mfc.exceptions.*;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.interfaces.modifier.CustomerProfileModifier;
import mfc.interfaces.modifier.CustomerRegistration;
import mfc.repositories.CustomerRepository;
import mfc.repositories.StoreOwnerRepository;
import mfc.repositories.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerRegistryTest {

    private final String mail = "Mark@pns.fr";
    private final String name = "Mark";
    private final String password = "password";
    private final Map<String, String> schedule = new HashMap<>();
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
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreOwnerRepository ownerRepository;


    @Test
    void unknownCustomer() {
        assertFalse(customerRepository.findCustomerByMail(mail).isPresent());
    }

    @Test
    void registerCustomer() throws Exception {
        Customer returned = customerRegistration.register(name, mail, password, "");
        Optional<Customer> customer = customerFinder.findCustomerByMail(mail);
        assertTrue(customer.isPresent());
        Customer mark = customer.get();

        assertEquals(mark.getVfp(), returned.getVfp());
        assertEquals(mark.getFavoriteStores(), returned.getFavoriteStores());
        assertEquals(mark, returned);
    }

    @Test
    void cannotRegisterTwice() throws Exception {
        customerRegistration.register(name, mail, password, "");
        Assertions.assertThrows(AlreadyExistingAccountException.class, () -> customerRegistration.register(name, mail, password, ""));
    }

    @Test
    void canFindByMail() throws Exception {
        customerRegistration.register(name, mail, password, "");
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
        Customer customer = customerRegistration.register(name, mail, password, "");
        Optional<Customer> customer2 = customerFinder.findCustomerById(customer.getId());
        assertTrue(customer2.isPresent());
    }

    @Test
    void canEditBalance() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password, "");
        customer = customerBalancesModifier.editBalance(customer, 100);
        assertEquals(100, customer.getBalance());
    }

    @Test
    void cannotEditBalanceWithNegativeValue() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password, "");
        Assertions.assertThrows(InsufficientBalanceException.class, () -> customerBalancesModifier.editBalance(customer, -100));
    }

    @Test
    void editBalanceOfUnknownCustomer() {
        Customer customer = new Customer(name, mail, password, "");
        Assertions.assertThrows(AccountNotFoundException.class, () -> customerBalancesModifier.editBalance(customer, 100));
    }

    @Test
    void canEditFidelityPoints() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password, "");
        customer = customerBalancesModifier.editFidelityPoints(customer, 100);
        assertEquals(100, customer.getFidelityPoints());
    }

    @Test
    void cannotEditFidelityPointsWithNegativeValue() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password, "");
        Assertions.assertThrows(NegativePointCostException.class, () -> customerBalancesModifier.editFidelityPoints(customer, -100));
    }

    @Test
    void editFidelityPointsOfUnknownCustomer() {
        Customer customer = new Customer(name, mail, password, "");
        Assertions.assertThrows(AccountNotFoundException.class, () -> customerBalancesModifier.editFidelityPoints(customer, 100));
    }

    @Test
    void canRecordMatriculation() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password, "");
        String matriculation = customer.getMatriculation();
        customer = customerProfileModifier.recordMatriculation(customer, "AB-123-CD");
        assertEquals("AB-123-CD", customer.getMatriculation());
        assertNotEquals(matriculation, customer.getMatriculation());
    }

    @Test
    void cannotRecordMatriculationOfUnknownCustomer() {
        Customer customer = new Customer(name, mail, password, "");
        Assertions.assertThrows(AccountNotFoundException.class, () -> customerProfileModifier.recordMatriculation(customer, "AB-123-CD"));
    }

    @Test
    void canRecordCreditCard() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password, "");
        String creditCard = customer.getCreditCard();
        customer = customerProfileModifier.recordCreditCard(customer, "1234 5678 9012 3456");
        assertEquals("1234 5678 9012 3456", customer.getCreditCard());
        assertNotEquals(creditCard, customer.getCreditCard());
    }

    @Test
    void cannotRecordCreditCardOfUnknownCustomer() {
        Customer customer = new Customer(name, mail, password, "");
        Assertions.assertThrows(AccountNotFoundException.class, () -> customerProfileModifier.recordCreditCard(customer, "1234 5678 9012 3456"));
    }

    @Test
    void canRecordFavoriteStore() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password, "");
        List<Store> favoriteStores = customer.getFavoriteStores();
        StoreOwner storeOwner = new StoreOwner("Owner", "owner@store.com", "password");
        storeOwner = ownerRepository.save(storeOwner);
        Store store = new Store("Carrefour", schedule, storeOwner);
        store = storeRepository.save(store);
        assertFalse(favoriteStores.contains(store));
        customer = customerProfileModifier.recordNewFavoriteStore(customer, store);
        assertEquals("Carrefour", customer.getFavoriteStores().get(0).getName());
    }

    @Test
    void cannotRecordFavoriteStoreOfUnknownCustomer() {
        Customer customer = new Customer(name, mail, password, "");
        StoreOwner storeOwner = new StoreOwner("Owner", "owner@store.com", "password");
        ownerRepository.save(storeOwner);
        storeOwner = ownerRepository.findStoreOwnerByName(storeOwner.getName()).get();
        Store store = new Store("Carrefour", schedule, storeOwner);
        storeRepository.save(store);
        store = storeRepository.findStoreByName(store.getName()).get();
        Store finalStore = store;
        Assertions.assertThrows(AccountNotFoundException.class, () -> customerProfileModifier.recordNewFavoriteStore(customer, finalStore));
    }

    @Test
    void favoriteStoreAlreadyRegistered() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password, "");
        StoreOwner storeOwner = new StoreOwner("Owner", "owner@store.com", "password");
        ownerRepository.save(storeOwner);
        storeOwner = ownerRepository.findStoreOwnerByName(storeOwner.getName()).get();
        Store store = new Store("Carrefour", schedule, storeOwner);
        storeRepository.save(store);
        store = storeRepository.findStoreByName(store.getName()).get();
        Store finalStore = store;
        Customer finalCustomer = customerProfileModifier.recordNewFavoriteStore(customer, finalStore);
        Assertions.assertThrows(AlreadyRegisteredStoreException.class, () -> customerProfileModifier.recordNewFavoriteStore(finalCustomer, finalStore));
    }

    @Test
    void canRemoveFavoriteStore() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password, "");
        StoreOwner storeOwner = new StoreOwner("Owner", "owner@store.com", "password");
        ownerRepository.save(storeOwner);
        storeOwner = ownerRepository.findStoreOwnerByName(storeOwner.getName()).get();
        Store store = new Store("Carrefour", schedule, storeOwner);
        storeRepository.save(store);
        store = storeRepository.findStoreByName(store.getName()).get();
        customer = customerProfileModifier.recordNewFavoriteStore(customer, store);
        assertTrue(customer.getFavoriteStores().contains(store));
        customer = customerProfileModifier.removeFavoriteStore(customer, store);
        assertFalse(customer.getFavoriteStores().contains(store));
    }

    @Test
    void cannotRemoveFavoriteStoreOfUnknownCustomer() {
        Customer customer = new Customer(name, mail, password, "");
        StoreOwner storeOwner = new StoreOwner("Owner", "owner@store.com", "password");
        Store store = new Store("Carrefour", schedule, storeOwner);
        Assertions.assertThrows(AccountNotFoundException.class, () -> customerProfileModifier.removeFavoriteStore(customer, store));
    }

    @Test
    void cannotRemoveFavoriteStoreNotRegistered() throws Exception {
        Customer customer = customerRegistration.register(name, mail, password, "");
        StoreOwner storeOwner = new StoreOwner("Owner", "owner@store.com", "password");
        ownerRepository.save(storeOwner);
        storeOwner = ownerRepository.findStoreOwnerByName(storeOwner.getName()).get();
        Store store = new Store("Carrefour", schedule, storeOwner);
        Assertions.assertThrows(StoreNotFoundException.class, () -> customerProfileModifier.removeFavoriteStore(customer, store));
    }

}