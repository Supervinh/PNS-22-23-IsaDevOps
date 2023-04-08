package mfc.cucumber.transactions;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mfc.entities.Customer;
import mfc.entities.Store;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.InsufficientBalanceException;
import mfc.exceptions.NegativePointCostException;
import mfc.exceptions.PayoffNotFoundException;
import mfc.interfaces.TransactionProcessor;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.PurchaseFinder;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.modifier.PurchaseRecording;
import mfc.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static mfc.cucumber.Helper.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

@SpringBootTest
@Transactional
public class Purchase {

    @Autowired
    private CustomerFinder customerFinder;
    @Autowired
    private TransactionProcessor transactionProcessor;
    @Autowired
    private StoreFinder storeFinder;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private PurchaseFinder purchaseFinder;
    @Autowired
    private PurchaseRecording purchaseRecording;

    @Before
    public void settingUpContext() {
        resetException();
        purchaseRepository.deleteAll();
    }

    @When("{string} makes a purchase of {int} euros at the store {string}")
    public void makesAPurchaseOfEuros(String name, int cost, String storeName) {
        Customer customer = customerFinder.findCustomerByName(name).get();
        try {
            transactionProcessor.purchase(customer, cost, storeFinder.findStoreByName(storeName).get());
        } catch (NegativePointCostException | CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @When("{string} makes a purchase of {double} euros with the fidelity card at the store {string}")
    public void makesAPurchaseOfEurosWithTheFidelityCard(String name, double cost, String storeName) {
        Customer customer = customerFinder.findCustomerByName(name).get();
        try {
            transactionProcessor.purchaseFidelityCardBalance(customer, cost, storeFinder.findStoreByName(storeName).get());
        } catch (InsufficientBalanceException | CustomerNotFoundException | NegativePointCostException e) {
            setException(e.getClass().getSimpleName());
        }
    }

    @Then("{string} is raised")
    public void isRaised(String arg0) {
        assertEquals(getException(), getException(), arg0);
    }

    @Then("{string} vfp status should be updated")
    public void vfpStatusShouldBeUpdated(String name) throws CustomerNotFoundException {
        assertEquals("VFP status should be updated", LocalDate.now().plusDays(7), customerFinder.findCustomerByName(name).orElseThrow(CustomerNotFoundException::new).getVfp());
    }

    @And("{string} already bought {int} times at the store {string} in the last week")
    public void alreadyBoughtTimesAtTheStoreInTheLastWeek(String customerName, int numberOfPurchases, String storeName) throws CustomerNotFoundException {
        Customer customer = customerFinder.findCustomerByName(customerName).orElseThrow(CustomerNotFoundException::new);
        Store store = storeFinder.findStoreByName(storeName).orElseThrow(CustomerNotFoundException::new);
        mfc.entities.Purchase p;
        for (int i = 0; i < numberOfPurchases; i++) {
            p = new mfc.entities.Purchase(5, customer, store);
            p.setDate(LocalDate.now().minusDays(6));
            purchaseRepository.save(p);
        }

    }

    @Then("{string} vfp status should not be updated")
    public void vfpStatusShouldNotBeUpdated(String name) throws CustomerNotFoundException {
        assertNotEquals("VFP status should be updated", LocalDate.now().plusDays(7), customerFinder.findCustomerByName(name).orElseThrow(CustomerNotFoundException::new).getVfp());
    }

    @And("a purchase is registered for {string} at {string} with a value of {double} euros")
    public void aPurchaseIsRegisteredForAtWithAValueOfEuros(String customerName, String storeName, double cost) {
        assertDoesNotThrow(() -> {
            Customer customer = customerFinder.findCustomerByName(customerName).orElseThrow(CustomerNotFoundException::new);
            Store store = storeFinder.findStoreByName(storeName).orElseThrow(CustomerNotFoundException::new);
            purchaseFinder.lookUpPurchasesByStore(store).stream().peek(System.out::println).
                    filter(p -> p.getCustomer().equals(customer) && p.getCost() == cost).
                    findFirst().orElseThrow(PayoffNotFoundException::new);
        });
    }
}
