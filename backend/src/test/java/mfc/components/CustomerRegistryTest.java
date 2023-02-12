package mfc.components;

import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.modifier.CustomerRegistration;
import mfc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CustomerRegistryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerRegistration customerRegistration;

    @Autowired
    private CustomerFinder customerFinder;

    @Test
    void findCustomerByMail() {
    }

    @Test
    void findCustomerById() {
    }

    @Test
    void editBalance() {
    }

    @Test
    void editFidelityPoints() {
    }

    @Test
    void recordMatriculation() {
    }

    @Test
    void recordCreditCard() {
    }

    @Test
    void recordNewFavoriteStore() {
    }

    @Test
    void removeFavoriteStore() {
    }

    @Test
    void recordNewFavoriteStores() {
    }

    @Test
    void removeAllFavoriteStores() {
    }

    @Test
    void register() {
    }
}