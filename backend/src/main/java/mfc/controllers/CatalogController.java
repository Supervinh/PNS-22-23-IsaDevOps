package mfc.controllers;

import mfc.POJO.Customer;
import mfc.POJO.Payoff;
import mfc.POJO.Store;
import mfc.POJO.StoreOwner;
import mfc.controllers.dto.CatalogDTO;
import mfc.controllers.dto.ConvertDTO;
import mfc.controllers.dto.ErrorDTO;
import mfc.controllers.dto.PayoffDTO;
import mfc.exceptions.*;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.explorer.StoreOwnerFinder;
import mfc.interfaces.modifier.CatalogModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = CatalogController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class CatalogController {

    public static final String BASE_URI = "/catalog";
    public static final String LOGGED_URI = "/{customerID}/cat/";
    public static final String STORE_OWNER_URI = "/{storeOwnerID}/cat/";

    private final ConvertDTO convertDTO = new ConvertDTO();

    @Autowired
    private CustomerFinder customerFinder;

    @Autowired
    private CatalogExplorer catalogExplorer;

    @Autowired
    private CatalogModifier catalogModifier;

    @Autowired
    private StoreOwnerFinder storeOwnerFinder;

    @Autowired
    private StoreFinder storeFinder;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process catalog information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @GetMapping(path = LOGGED_URI + "availableCatalog")
    // path is a REST CONTROLLER NAME
    public ResponseEntity<CatalogDTO> availableCatalog(@PathVariable("customerID") UUID customerID) throws CustomerNotFoundException {
        Optional<Customer> customer = customerFinder.findCustomerById(customerID);
        if (customer.isPresent()) {
            CatalogDTO c = convertDTO.convertCatalogToDTO(catalogExplorer.availablePayoffs((customer.get())));
            System.out.println(c);
            return ResponseEntity.status(HttpStatus.CREATED).body(c);
        } else throw new CustomerNotFoundException();
    }

    @PostMapping(path = LOGGED_URI + "exploreCatalog")
    public ResponseEntity<CatalogDTO> exploreCatalog(@RequestBody String string, @PathVariable("customerID") UUID customerID) throws CustomerNotFoundException {
        Optional<Customer> customer = customerFinder.findCustomerById(customerID);
        if (customer.isPresent()) {
            System.out.println(string);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertDTO.convertCatalogToDTO(catalogExplorer.exploreCatalogue(customer.get(), string)));
        } else throw new CustomerNotFoundException();
    }

    @PostMapping(path = STORE_OWNER_URI + "addPayoff", consumes = APPLICATION_JSON_VALUE)
    // path is a REST CONTROLLER NAME
    public ResponseEntity<PayoffDTO> addPayoff(@RequestBody @Valid PayoffDTO payoffDTO, @PathVariable("storeOwnerID") UUID storeOwnerID) throws StoreOwnerNotFoundException {
        try {
            Store store = getStore(payoffDTO, storeOwnerID);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertDTO.convertPayoffToDTO(
                    catalogModifier.addPayOff(payoffDTO.getName(), payoffDTO.getCost(), payoffDTO.getPointCost(), store)));
        } catch (StoreNotFoundException | NegativeCostException | NegativePointCostException |
                 AlreadyExistingPayoffException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    private Store getStore(PayoffDTO payoffDTO, UUID storeOwnerID) throws StoreOwnerNotFoundException, StoreNotFoundException {
        storeOwnerFinder.findStoreOwnerById(storeOwnerID).orElseThrow(StoreOwnerNotFoundException::new); // Only for authentication, no point in saving the storeOwner
        return storeFinder.findStoreByName(payoffDTO.getStoreName()).orElseThrow(StoreNotFoundException::new);
    }

    @PostMapping(path = STORE_OWNER_URI + "deletePayoff", consumes = APPLICATION_JSON_VALUE)
    // path is a REST CONTROLLER NAME
    public ResponseEntity<PayoffDTO> deletePayoff(@RequestBody @Valid PayoffDTO payoffDTO, @PathVariable("storeOwnerID") UUID storeOwnerID) throws StoreOwnerNotFoundException {
        try {
            Optional<StoreOwner> storeOwner = storeOwnerFinder.findStoreOwnerById(storeOwnerID);
            Payoff payOff = new Payoff(payoffDTO.getName(), payoffDTO.getCost(), payoffDTO.getPointCost(), null/*, payoffDTO.getStore()*/);
            if (storeOwner.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(convertDTO.convertPayoffToDTO(catalogModifier.deletePayoff(payOff)));
            } else throw new StoreOwnerNotFoundException();
        } catch (PayoffNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping(path = STORE_OWNER_URI + "editPayoff", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PayoffDTO> editPayoff(@RequestBody @Valid PayoffDTO payoffDTO, @PathVariable("storeOwnerID") UUID storeOwnerID) throws StoreOwnerNotFoundException {
        try {
            Store store = getStore(payoffDTO, storeOwnerID);
            Payoff payOff = new Payoff(payoffDTO.getName(), payoffDTO.getCost(), payoffDTO.getPointCost(), store);
            Optional<Double> cost = payOff.getCost() == 0 ? Optional.empty() : Optional.of(payOff.getCost());
            Optional<Integer> pointCost = payOff.getPointCost() == 0 ? Optional.empty() : Optional.of(payOff.getPointCost());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(convertDTO.convertPayoffToDTO(catalogModifier.editPayOff(payOff, cost, pointCost)));
        } catch (PayoffNotFoundException | StoreNotFoundException | NegativeCostException |
                 NegativePointCostException e) {
            System.out.println(e.getMessage() + "" + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
