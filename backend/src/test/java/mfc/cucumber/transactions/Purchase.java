package mfc.cucumber.transactions;

import io.cucumber.java.en.When;
import mfc.entities.Customer;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.InsufficientBalanceException;
import mfc.exceptions.NegativePointCostException;
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
        try {
            transactionProcessor.purchase(customer, cost, storeFinder.findStoreByName(storeName).get());
        } catch (NegativePointCostException | CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @When("{string} makes a purchase of {int} euros with the fidelity card at the store {string}")
    public void makesAPurchaseOfEurosWithTheFidelityCard(String name, int cost, String storeName) {
        Customer customer = customerFinder.findCustomerByName(name).get();
        try {
            transactionProcessor.purchaseFidelityCardBalance(customer, cost, storeFinder.findStoreByName(storeName).get());
        } catch (InsufficientBalanceException | CustomerNotFoundException | NegativePointCostException e) {
            throw new RuntimeException(e);
        }
    }
}
