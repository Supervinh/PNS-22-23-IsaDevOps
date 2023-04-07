package mfc.cucumber.transactions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mfc.entities.Customer;
import mfc.entities.Payoff;
import mfc.entities.Store;
import mfc.exceptions.*;
import mfc.interfaces.PayOffProcessor;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.modifier.CatalogModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class PayOff {

    @Autowired
    private CatalogModifier catalogModifier;

    @Autowired
    private CatalogExplorer catalogExplorer;

    @Autowired
    private StoreFinder storeFinder;

    @Autowired
    private PayOffProcessor payOffProcessor;

    @Autowired
    private CustomerFinder customerFinder;

    @Given("a payOff named {string}, which costs {int} euros, which can be exchanged for {int} points and is available at {string}")
    public void aPayOffNamedWhichCostsEurosWhichCanBeExchangedForPointsAndIsAvailableAt(String name, int cost, int pointCost, String storeName) throws NegativePointCostException, NegativeCostException, AlreadyExistingPayoffException, StoreNotFoundException {
        Store store = storeFinder.findStoreByName(storeName).orElseThrow(StoreNotFoundException::new);
        catalogModifier.addPayOff(name, cost, pointCost, store, false);
    }

    @When("{string} use points to buy {string} at {string}")
    public void usePointsToBuy(String name, String payoffName, String storeName) throws NegativePointCostException, NoMatriculationException, VFPExpiredException, ParkingException, CustomerNotFoundException, InsufficientBalanceException, PayoffNotFoundException, NoPreviousPurchaseException {
        Payoff payoff = catalogExplorer.findPayoff(payoffName, storeName).orElseThrow(PayoffNotFoundException::new);
        Customer customer = customerFinder.findCustomerByName(name).orElseThrow(CustomerNotFoundException::new);
        payOffProcessor.claimPayoff(customer, payoff);
    }

    @When("a payoff named {string} with a cost of {double}, a point cost of {int} with a vfp status is added to {string}")
    public void aPayoffNamedWithACostOfAPointCostOfWithAVfpStatusTrueIsAddedTo(String name, double cost, int pointCost, String storeName) throws StoreNotFoundException, NegativePointCostException, NegativeCostException, AlreadyExistingPayoffException {
        catalogModifier.addPayOff(name, cost, pointCost, storeFinder.findStoreByName(storeName).orElseThrow(StoreNotFoundException::new), true);
    }

    @When("a payoff named {string} with a cost of {double}, a point cost of {int} without a vfp status is added to {string}")
    public void aPayoffNamedWithACostOfAPointCostOfWithoutAVfpStatusTrueIsAddedTo(String name, double cost, int pointCost, String storeName) throws StoreNotFoundException, NegativePointCostException, NegativeCostException, AlreadyExistingPayoffException {
        catalogModifier.addPayOff(name, cost, pointCost, storeFinder.findStoreByName(storeName).orElseThrow(StoreNotFoundException::new), false);
    }

    @Then("{string} should have a payoff named {string} with a cost of {double} and a point cost of {int} and a vfp status")
    public void shouldHaveAPayoffNamedWithACostOfAndAPointCostOfAndAVfpStatus(String storeName, String name, double cost, int pointCost) throws PayoffNotFoundException, StoreNotFoundException {
        assertEquals("Payoff: ", new Payoff(name, cost, pointCost, storeFinder.findStoreByName(storeName).orElseThrow(StoreNotFoundException::new), true), catalogExplorer.findPayoff(name, storeName).orElseThrow(PayoffNotFoundException::new));
    }

    @Then("{string} should have a payoff named {string} with a cost of {double} and a point cost of {int} and no vfp status")
    public void shouldHaveAPayoffNamedWithACostOfAndAPointCostOfAndNoVfpStatus(String storeName, String name, double cost, int pointCost) throws PayoffNotFoundException, StoreNotFoundException {
        assertEquals("Payoff: ", new Payoff(name, cost, pointCost, storeFinder.findStoreByName(storeName).orElseThrow(StoreNotFoundException::new), false), catalogExplorer.findPayoff(name, storeName).orElseThrow(PayoffNotFoundException::new));
    }

    @And("add a payoff named {string} to {string} should raised an AlreadyExistingPayoffException")
    public void addAPayoffNamedToShouldRaisedAnAlreadyExistingPayoffException(String payoff, String storeOwner) {
        assertThrows(AlreadyExistingPayoffException.class, () -> catalogModifier.addPayOff(payoff, 0, 0, new Store(storeOwner, null), false));
    }
}
