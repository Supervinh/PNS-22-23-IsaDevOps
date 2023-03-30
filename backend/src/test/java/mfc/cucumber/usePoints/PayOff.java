package mfc.cucumber.usePoints;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import mfc.entities.Customer;
import mfc.entities.Payoff;
import mfc.entities.Store;
import mfc.exceptions.*;
import mfc.interfaces.PayOffProcessor;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.PayOffPurchaseFinder;
import mfc.interfaces.explorer.StoreFinder;
import mfc.repositories.PayoffRepository;
import mfc.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PayOff {

    @Autowired
    private PayoffRepository payoffRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreFinder storeFinder;

    @Autowired
    private PayOffProcessor payOffProcessor;

    @Autowired
    private CustomerFinder customerFinder;


    @Given("a payOff named {string}, which costs {int} euros, which can be exchanged for {int} points and is available at {string}")
    public void aPayOffNamedWhichCostsEurosWhichCanBeExchangedForPointsAndIsAvailableAt(String name, int cost, int pointCost, String store) {
        Store store1 = storeFinder.findStoreByName(store).get();
        Payoff payoff = new Payoff(name, cost, pointCost, store1);
        payoffRepository.save(payoff);
    }

    @When("{string} use points to buy {string} at {string}")
    public void usePointsToBuy(String name, String payoffName, String storeName) throws NegativePointCostException, NoMatriculationException, VFPExpiredException, ParkingException, CustomerNotFoundException {
        Payoff payoff = payoffRepository.findPayoffByNameAndStore_Name(payoffName, storeName).get();
        Customer customer = customerFinder.findCustomerByName(name).get();
        payOffProcessor.claimPayoff(customer, payoff);
    }


}
