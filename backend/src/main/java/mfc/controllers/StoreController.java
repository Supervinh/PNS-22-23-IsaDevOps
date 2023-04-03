package mfc.controllers;

import mfc.components.TransactionHandler;
import mfc.controllers.dto.ErrorDTO;
import mfc.controllers.dto.PurchaseDTO;
import mfc.controllers.dto.StoreDTO;
import mfc.entities.Customer;
import mfc.entities.Purchase;
import mfc.entities.Store;
import mfc.entities.StoreOwner;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.NegativeCostException;
import mfc.exceptions.StoreNotFoundException;
import mfc.exceptions.StoreOwnerNotFoundException;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.explorer.StoreOwnerFinder;
import mfc.interfaces.modifier.StoreModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static mfc.controllers.dto.ConvertDTO.convertPurchaseToDto;
import static mfc.controllers.dto.ConvertDTO.convertStoreToDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = StoreController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class StoreController {

    public static final String BASE_URI = "/store";
    public static final String LOGGED_URI = "/{ownerId}/";

    @Autowired
    private StoreFinder storeFinder;

    @Autowired
    private StoreOwnerFinder ownerFinder;

    @Autowired
    private CustomerFinder customerFinder;

    @Autowired
    private StoreModifier storeModifier;

    @Autowired
    private TransactionHandler transactionHandler;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process Store information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @PostMapping(path = LOGGED_URI + "register", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<StoreDTO> register(@RequestBody @Valid StoreDTO storeDTO, @PathVariable("ownerId") long ownerId) {
        try {
            // from DTO to POJO
            StoreOwner owner = ownerFinder.findStoreOwnerById(ownerId).orElseThrow(StoreOwnerNotFoundException::new);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertStoreToDto(storeModifier.register(storeDTO.getName(), storeDTO.getSchedule(), owner)));
        } catch (Exception e) {
            // Note: Returning 409 (Conflict) can also be seen a security/privacy vulnerability, exposing a service for account enumeration
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(path = LOGGED_URI + "addPurchase", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<PurchaseDTO> addPurchase(@RequestBody @Valid PurchaseDTO purchaseDTO, @PathVariable("ownerId") long ownerId) {
        try {
            StoreOwner owner = ownerFinder.findStoreOwnerById(ownerId).orElseThrow(StoreOwnerNotFoundException::new);
            Customer customer = customerFinder.findCustomerByMail(purchaseDTO.getCustomerEmail()).orElseThrow(CustomerNotFoundException::new);
            Store store = storeFinder.findStoreByName(purchaseDTO.getStoreName()).orElseThrow(StoreNotFoundException::new);
            if (!store.getOwner().equals(owner)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            Purchase p;
            if (purchaseDTO.getCost() < 0) {
                throw new NegativeCostException();
            }
            if (purchaseDTO.isInternalAccount()) {
                p = transactionHandler.purchaseFidelityCardBalance(customer, purchaseDTO.getCost(), store);
            } else {
                p = transactionHandler.purchase(customer, purchaseDTO.getCost(), store);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(convertPurchaseToDto(p));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //    /store/6/modifySchedule
    @PostMapping(path = LOGGED_URI + "modifySchedule", consumes = APPLICATION_JSON_VALUE)
    // path is a REST CONTROLLER NAME
    public ResponseEntity<StoreDTO> modifySchedule(@RequestBody @Valid StoreDTO storeDTO, @PathVariable("ownerId") long ownerId) throws StoreOwnerNotFoundException, StoreNotFoundException {
        StoreOwner owner = ownerFinder.findStoreOwnerById(ownerId).orElseThrow(StoreOwnerNotFoundException::new);
        Store store = storeFinder.findStoreByName(storeDTO.getName()).orElseThrow(StoreNotFoundException::new);
        if (!store.getOwner().equals(owner)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(convertStoreToDto(storeModifier.updateOpeningHours(store, storeDTO.getSchedule())));
    }

}

