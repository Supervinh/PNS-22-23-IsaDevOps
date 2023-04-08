package mfc.controllers;

import mfc.controllers.dto.CatalogDTO;
import mfc.controllers.dto.DeletePayoffDTO;
import mfc.controllers.dto.PayoffDTO;
import mfc.entities.Customer;
import mfc.entities.Payoff;
import mfc.entities.Store;
import mfc.entities.StoreOwner;
import mfc.exceptions.*;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.explorer.StoreOwnerFinder;
import mfc.interfaces.modifier.CatalogModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static mfc.controllers.dto.ConvertDTO.convertCatalogToDTO;
import static mfc.controllers.dto.ConvertDTO.convertPayoffToDTO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = CatalogController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class CatalogController {

    public static final String BASE_URI = "/catalog";
    public static final String LOGGED_URI = "/{customerID}/";
    public static final String STORE_OWNER_URI = "/{storeOwnerID}/";

    private final CustomerFinder customerFinder;

    private final CatalogExplorer catalogExplorer;

    private final CatalogModifier catalogModifier;

    private final StoreOwnerFinder storeOwnerFinder;

    private final StoreFinder storeFinder;

    @Autowired
    public CatalogController(CustomerFinder customerFinder, CatalogExplorer catalogExplorer, CatalogModifier catalogModifier, StoreOwnerFinder storeOwnerFinder, StoreFinder storeFinder) {
        this.customerFinder = customerFinder;
        this.catalogExplorer = catalogExplorer;
        this.catalogModifier = catalogModifier;
        this.storeOwnerFinder = storeOwnerFinder;
        this.storeFinder = storeFinder;
    }

    @GetMapping(path = LOGGED_URI + "availableCatalog")
    // path is a REST CONTROLLER NAME
    public ResponseEntity<CatalogDTO> availableCatalog(@PathVariable("customerID") Long customerID) throws AccountNotFoundException {
        Customer customer = customerFinder.findCustomerById(customerID).orElseThrow(AccountNotFoundException::new);
        CatalogDTO c = convertCatalogToDTO(catalogExplorer.showAvailablePayoffs((customer)));
        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }

    @PostMapping(path = LOGGED_URI + "exploreCatalog")
    public ResponseEntity<CatalogDTO> exploreCatalog(@RequestBody String string, @PathVariable("customerID") Long customerID) throws AccountNotFoundException {
        Customer customer = customerFinder.findCustomerById(customerID).orElseThrow(AccountNotFoundException::new);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertCatalogToDTO(catalogExplorer.exploreCatalogue(customer, string)));
    }

    @PostMapping(path = STORE_OWNER_URI + "addPayoff", consumes = APPLICATION_JSON_VALUE)
    // path is a REST CONTROLLER NAME
    public ResponseEntity<PayoffDTO> addPayoff(@RequestBody @Valid PayoffDTO payoffDTO, @PathVariable("storeOwnerID") Long storeOwnerID) throws AccountNotFoundException, StoreNotFoundException, NegativePointCostException, NegativeCostException, AlreadyExistingPayoffException {
        Store store = getStore(payoffDTO, storeOwnerID);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertPayoffToDTO(catalogModifier.addPayOff(payoffDTO.getName(), payoffDTO.getCost(), payoffDTO.getPointCost(), store, payoffDTO.isVfp())));
    }

    private Store getStore(PayoffDTO payoffDTO, Long storeOwnerID) throws AccountNotFoundException, StoreNotFoundException {
        storeOwnerFinder.findStoreOwnerById(storeOwnerID).orElseThrow(AccountNotFoundException::new); // Only for authentication, no point in saving the storeOwner
        return storeFinder.findStoreByName(payoffDTO.getStoreName()).orElseThrow(StoreNotFoundException::new);
    }

    @PostMapping(path = STORE_OWNER_URI + "deletePayoff", consumes = APPLICATION_JSON_VALUE)
    // path is a REST CONTROLLER NAME
    public ResponseEntity<Void> deletePayoff(@RequestBody @Valid DeletePayoffDTO deletePayoffDTO, @PathVariable("storeOwnerID") Long storeOwnerID) throws AccountNotFoundException, CredentialsException, PayoffNotFoundException {
        StoreOwner storeOwner = storeOwnerFinder.findStoreOwnerById(storeOwnerID).orElseThrow(AccountNotFoundException::new);
        Payoff payOff = catalogExplorer.findPayoff(deletePayoffDTO.getPayoffName(), deletePayoffDTO.getStoreName()).orElseThrow(PayoffNotFoundException::new);
        if (payOff.getStore().getOwner().equals(storeOwner)) {
            catalogModifier.deletePayoff(payOff);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else throw new CredentialsException();
    }

    @PostMapping(path = STORE_OWNER_URI + "editPayoff", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PayoffDTO> editPayoff(@RequestBody @Valid PayoffDTO payoffDTO, @PathVariable("storeOwnerID") Long storeOwnerID) throws AccountNotFoundException, StoreNotFoundException, NegativePointCostException, NegativeCostException, PayoffNotFoundException {
        Store store = getStore(payoffDTO, storeOwnerID);
        Payoff payOff = new Payoff(payoffDTO.getName(), payoffDTO.getCost(), payoffDTO.getPointCost(), store, payoffDTO.isVfp());
        Optional<Double> cost = payOff.getCost() == 0 ? Optional.empty() : Optional.of(payOff.getCost());
        Optional<Integer> pointCost = payOff.getPointCost() == 0 ? Optional.empty() : Optional.of(payOff.getPointCost());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(convertPayoffToDTO(catalogModifier.editPayOff(payOff, cost, pointCost, payoffDTO.isVfp())));
    }
}
