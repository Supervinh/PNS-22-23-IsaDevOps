package mfc.cucumber.purchase;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mfc.POJO.Customer;
import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.exceptions.AlreadyExistingStoreException;
import mfc.interfaces.TransactionProcessor;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.modifier.CustomerRegistration;
import mfc.interfaces.modifier.StoreRegistration;
import mfc.repositories.CustomerRepository;
import mfc.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.Map;

public class Purchase {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerRegistration customerRegistration;
    @Autowired
    private CustomerFinder customerFinder;
    @Autowired
    private TransactionProcessor transactionProcessor;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreRegistration storeRegistration;
    // TODO: enlever les " quand StoreOwnerRegistration et StoreOwnerRepository seront implémentés
    /*@Autowired
    private StoreOwnerRegistration storeOwnerRegistration;
    @Autowired
    private StoreOwnerRepository storeOwnerRepository;*/


    private Store store;

    private StoreOwner storeOwner;

    @Before
    void setUp() throws AlreadyExistingStoreException {
        customerRepository.deleteAll();
        // TODO: pareil que précédemment
        //storeOwnerRepository.deleteAll();
        storeRepository.deleteAll();
        //storeOwner = storeOwnerRegistration.register(name, mail, password);
        storeOwner = new StoreOwner("Jane Doe", "jane@doe.com", "password");
        Map<LocalTime, LocalTime> openingHours = Map.of(LocalTime.of(8, 0), LocalTime.of(20, 0));
        store = storeRegistration.register(openingHours, storeOwner, "Store");
    }

    @Given("a customer named {string} with {string} as mail address and {string} as password")
    public void aCustomerNamedWithAsMailAddressAndAsPassword(String name, String mail, String password) throws AlreadyExistingAccountException {
        customerRegistration.register(mail, name, password);
    }

    @Given("{string} has {int} points")
    public void hasPoints(String name, int points) {
        Customer customer = customerFinder.findCustomerByName(name).get();
        customer.setFidelityPoints(points);
    }


    @When("{string} makes a purchase of {int} euros")
    public void makesAPurchaseOfEuros(String name, int cost) {
        Customer customer = customerFinder.findCustomerByName(name).get();
        transactionProcessor.purchase(customer, cost, store);
    }


    @Then("{string} should have {int} points")
    public void shouldHavePoints(String name, int points) {
        Customer customer = customerFinder.findCustomerByName(name).get();
        assert customer.getFidelityPoints() == points;
    }

//    @And("{string} has a fidelity card with a balance of {double} euros")
//    public void hasAFidelityCardWithABalanceOfEuros(String name, double balance) {
//        Customer customer = customerFinder.findCustomerByName(name).get();
//        customer.setBalance(balance);
//    }
}
