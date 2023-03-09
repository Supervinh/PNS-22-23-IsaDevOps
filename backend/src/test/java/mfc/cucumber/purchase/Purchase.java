package mfc.cucumber.purchase;

import io.cucumber.java.en.When;
import mfc.POJO.Customer;
import mfc.exceptions.InsufficientBalanceException;
import mfc.interfaces.TransactionProcessor;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.StoreFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Purchase {

    @Autowired
    private CustomerFinder customerFinder;
    @Autowired
    private TransactionProcessor transactionProcessor;
    @Autowired
    private StoreFinder storeFinder;


    @When("{string} makes a purchase of {int} euros at the store {string}")
    public void makesAPurchaseOfEuros(String name, int cost, String storeName) {
        Customer customer = customerFinder.findCustomerByName(name).get();
        transactionProcessor.purchase(customer, cost, storeFinder.findStoreByName(storeName).get());
    }

    @When("{string} makes a purchase of {int} euros with the fidelity card at the store {string}")
    public void makesAPurchaseOfEurosWithTheFidelityCard(String name, int cost, String storeName) {
        Customer customer = customerFinder.findCustomerByName(name).get();
        try {
            transactionProcessor.purchaseFidelityCardBalance(customer, cost, storeFinder.findStoreByName(storeName).get());
        } catch (InsufficientBalanceException e) {
            throw new RuntimeException(e);
        }
    }
}
