package mfc.cucumber.usePoints;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import mfc.entities.Customer;
import mfc.entities.Payoff;
import mfc.entities.Store;
import mfc.exceptions.*;
import mfc.interfaces.PayOffProcessor;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.PayOffPurchaseFinder;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.modifier.CatalogModifier;
import mfc.repositories.PayoffRepository;
import mfc.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void aPayOffNamedWhichCostsEurosWhichCanBeExchangedForPointsAndIsAvailableAt(String name, int cost, int pointCost, String store) throws NegativePointCostException, NegativeCostException, AlreadyExistingPayoffException {
        Store store1 = storeFinder.findStoreByName(store).get();
        catalogModifier.addPayOff(name, cost, pointCost, store1, false);
    }

    @When("{string} use points to buy {string} at {string}")
    public void usePointsToBuy(String name, String payoffName, String storeName) throws NegativePointCostException, NoMatriculationException, VFPExpiredException, ParkingException, CustomerNotFoundException, InsufficientBalanceException, PayoffNotFoundException, NoPreviousPurchaseException {
        Payoff payoff = catalogExplorer.findPayoff(payoffName, storeName).get();
        Customer customer = customerFinder.findCustomerByName(name).get();
        payOffProcessor.claimPayoff(customer, payoff);
    }


}
