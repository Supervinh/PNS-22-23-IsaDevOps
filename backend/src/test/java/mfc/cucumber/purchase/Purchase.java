package mfc.cucumber.purchase;

import mfc.interfaces.TransactionProcessor;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.modifier.CustomerRegistration;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import mfc.interfaces.modifier.StoreRegistration;
import mfc.repositories.CustomerRepository;
import mfc.repositories.StoreOwnerRepository;
import mfc.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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
    private StoreFinder storeFinder;
    @Autowired
    private StoreRegistration storeRegistration;
    @Autowired
    private StoreOwnerRegistration storeOwnerRegistration;
    @Autowired
    private StoreOwnerRepository storeOwnerRepository;


//    @Given("a customer named {string} with {string} as mail address and {string} as password")
//    public void aCustomerNamedWithAsMailAddressAndAsPassword(String name, String mail, String password) throws AlreadyExistingAccountException {
//        customerRepository.deleteAll();
//        customerRegistration.register(mail, name, password);
//    }

//    @Given("a store owner named {string} with {string} as mail address and {string} as password")
//    public void aStoreOwnerNamedWithAsMailAddressAndAsPassword(String name, String mail, String password) throws AlreadyExistingAccountException {
//        storeOwnerRepository.deleteAll();
//        storeOwnerRegistration.registerStoreOwner(name, mail, password);
//    }
//
//    @Given("a store named {string}, owned by {string}, with opening hours from {int}:{int} to {int}:{int}")
//    public void aStoreNamedOwnedByWithOpeningHoursFromTo(String storeName, String ownerMail, int openingHour, int openingMinute, int closingHour, int closingMinute) throws AlreadyExistingStoreException, AlreadyExistingAccountException {
//        storeRepository.deleteAll();
//        Map<LocalTime, LocalTime> openingHours = Map.of(LocalTime.of(openingHour, openingMinute), LocalTime.of(closingHour, closingMinute));
//        storeRegistration.register(openingHours, storeOwnerRepository.findByMail(ownerMail).get(), storeName);
//    }
//
//    @Given("{string} has {int} points")
//    public void hasPoints(String name, int points) {
//        Customer customer = customerFinder.findCustomerByName(name).get();
//        customer.setFidelityPoints(points);
//    }
//
//
//    @When("{string} makes a purchase of {int} euros at the store {string}")
//    public void makesAPurchaseOfEuros(String name, int cost, String storeName) {
//        Customer customer = customerFinder.findCustomerByName(name).get();
//        transactionProcessor.purchase(customer, cost, storeFinder.findStoreByName(storeName).get());
//    }
//
//
//    @Then("{string} should have {int} points")
//    public void shouldHavePoints(String name, int points) {
//        Customer customer = customerFinder.findCustomerByName(name).get();
//        assert customer.getFidelityPoints() == points;
//    }
//
//    @And("{string} has a fidelity card with a balance of {double} euros")
//    public void hasAFidelityCardWithABalanceOfEuros(String name, double balance) {
//        Customer customer = customerFinder.findCustomerByName(name).get();
//        customer.setBalance(balance);
//    }
//
//    @When("{string} makes a purchase of {int} euros with the fidelity card at the store {string}")
//    public void makesAPurchaseOfEurosWithTheFidelityCard(String name, int cost, String storeName) {
//        Customer customer = customerFinder.findCustomerByName(name).get();
//        try {
//            transactionProcessor.purchaseFidelityCardBalance(customer, cost, storeFinder.findStoreByName(storeName).get());
//        } catch (InsufficientBalanceException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
