package mfc.controllers;

import mfc.POJO.Customer;
import mfc.POJO.StoreOwner;
import mfc.controllers.dto.*;
import mfc.exceptions.*;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.explorer.CustomerFinder;
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

    private final ConvertDTO convertDTO = new ConvertDTO();

    @Autowired
    private CustomerFinder customerFinder;

    @Autowired
    private CatalogExplorer catalogExplorer;

    @Autowired
    private CatalogModifier catalogModifier;

    @Autowired
    private StoreOwnerFinder storeOwnerFinder;

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
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(c);
        } else throw new CustomerNotFoundException();
    }

    @PostMapping(path = LOGGED_URI + "exploreCatalog")
    public ResponseEntity<CatalogDTO> exploreCatalog(@RequestBody String string, @PathVariable("customerID") UUID customerID) throws CustomerNotFoundException {
        Optional<Customer> customer = customerFinder.findCustomerById(customerID);
        if (customer.isPresent()) {
            System.out.println(string);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertDTO.convertCatalogToDTO(catalogExplorer.exploreCatalogue(customer.get(), string)));
        } else throw new CustomerNotFoundException();
    }

    @PostMapping(path = "addPayoff", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<PayoffDTO> addPayoff(@RequestBody @Valid StoreOwnerDTO storeOwnerDTO, @RequestBody PayoffDTO payoffDTO) throws StoreOwnerNotFoundException {
        try {
            Optional<StoreOwner> storeOwner = storeOwnerFinder.findStoreOwnerById(storeOwnerDTO.getId());
            if (storeOwner.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(convertDTO.convertPayoffToDTO(catalogModifier.addPayOff(payoffDTO.getName(), payoffDTO.getCost(), payoffDTO.getPointCost(), null/*, payoffDTO.getStore()*/)));
            } else throw new StoreOwnerNotFoundException();
        } catch (NegativeCostException | NegativePointCostException | AlreadyExistingPayoffException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
