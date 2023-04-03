package mfc.components;

import mfc.controllers.dto.DashboardDTO;
import mfc.entities.PayoffPurchase;
import mfc.entities.Purchase;
import mfc.entities.Store;
import mfc.interfaces.StoreDataGathering;
import mfc.interfaces.explorer.PayOffPurchaseFinder;
import mfc.interfaces.explorer.PurchaseFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;

@Component
@Transactional
public class DataGatherer implements StoreDataGathering {

    private final PurchaseFinder purchaseFinder;

    private final PayOffPurchaseFinder payOffPurchaseFinder;

    @Autowired
    public DataGatherer(PurchaseFinder purchaseFinder, PayOffPurchaseFinder payOffPurchaseFinder) {
        this.purchaseFinder = purchaseFinder;
        this.payOffPurchaseFinder = payOffPurchaseFinder;
    }

    @Override
    public DashboardDTO gather(Store store) {
        Set<Purchase> purchases = purchaseFinder.lookUpPurchasesByStore(store);
        Set<PayoffPurchase> payoffs = payOffPurchaseFinder.lookUpPayOffPurchasesByStore(store);
        DashboardDTO dashboardDTO = new DashboardDTO();
        dashboardDTO.setSalesVolumes(purchases.stream().mapToDouble(Purchase::getCost).sum());
        dashboardDTO.setNumberOfSales(purchases.size());
        dashboardDTO.setNumberOfCustomers(purchases.stream().map(Purchase::getCustomer).distinct().count());
        dashboardDTO.setNumberOfGivenPayoffs(payoffs.size());
        dashboardDTO.setPayoffCumulatedCost(payoffs.stream().mapToDouble(PayoffPurchase::getCost).sum());
        return dashboardDTO;
    }
}
