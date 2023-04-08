package mfc.controllers;

import mfc.controllers.dto.PurchaseDTO;
import mfc.controllers.dto.StoreDTO;
import mfc.entities.Customer;
import mfc.entities.Purchase;
import mfc.entities.Store;
import mfc.entities.StoreOwner;
import mfc.exceptions.*;
import mfc.interfaces.TransactionProcessor;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.explorer.StoreOwnerFinder;
import mfc.interfaces.modifier.StoreModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final StoreFinder storeFinder;

    private final StoreOwnerFinder ownerFinder;

    private final CustomerFinder customerFinder;

    private final StoreModifier storeModifier;

    private final TransactionProcessor transactionProcessor;

    @Autowired
    public StoreController(StoreFinder storeFinder, StoreOwnerFinder ownerFinder, CustomerFinder customerFinder, StoreModifier storeModifier, TransactionProcessor transactionProcessor) {
        this.storeFinder = storeFinder;
        this.ownerFinder = ownerFinder;
        this.customerFinder = customerFinder;
        this.storeModifier = storeModifier;
        this.transactionProcessor = transactionProcessor;
    }

    @PostMapping(path = LOGGED_URI + "register", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<StoreDTO> register(@RequestBody @Valid StoreDTO storeDTO, @PathVariable("ownerId") long ownerId) throws AccountNotFoundException, AlreadyExistingStoreException {
        // from DTO to POJO
        StoreOwner owner = ownerFinder.findStoreOwnerById(ownerId).orElseThrow(AccountNotFoundException::new);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertStoreToDto(storeModifier.register(storeDTO.getName(), storeDTO.getSchedule(), owner)));
    }

    @PostMapping(path = LOGGED_URI + "addPurchase", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<PurchaseDTO> addPurchase(@RequestBody @Valid PurchaseDTO purchaseDTO, @PathVariable("ownerId") long ownerId) throws AccountNotFoundException, StoreNotFoundException, NegativeCostException, NegativePointCostException, InsufficientBalanceException {
        StoreOwner owner = ownerFinder.findStoreOwnerById(ownerId).orElseThrow(AccountNotFoundException::new);
        Customer customer = customerFinder.findCustomerByMail(purchaseDTO.getCustomerEmail()).orElseThrow(AccountNotFoundException::new);
        Store store = storeFinder.findStoreByName(purchaseDTO.getStoreName()).orElseThrow(StoreNotFoundException::new);
        if (!store.getOwner().equals(owner)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Purchase p;
        if (purchaseDTO.getCost() < 0) {
            throw new NegativeCostException();
        }
        if (purchaseDTO.isInternalAccount()) {
            p = transactionProcessor.purchaseFidelityCardBalance(customer, purchaseDTO.getCost(), store);
        } else {
            p = transactionProcessor.purchase(customer, purchaseDTO.getCost(), store);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(convertPurchaseToDto(p));
    }

    //    /store/6/modifySchedule
    @PostMapping(path = LOGGED_URI + "modifySchedule", consumes = APPLICATION_JSON_VALUE)
    // path is a REST CONTROLLER NAME
    public ResponseEntity<StoreDTO> modifySchedule(@RequestBody @Valid StoreDTO storeDTO, @PathVariable("ownerId") long ownerId) throws AccountNotFoundException, StoreNotFoundException, CredentialsException {
        StoreOwner owner = ownerFinder.findStoreOwnerById(ownerId).orElseThrow(AccountNotFoundException::new);
        Store store = storeFinder.findStoreByName(storeDTO.getName()).orElseThrow(StoreNotFoundException::new);
        if (!store.getOwner().equals(owner)) {
            throw new CredentialsException();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(convertStoreToDto(storeModifier.updateOpeningHours(store, storeDTO.getSchedule())));
    }

    @DeleteMapping(path = LOGGED_URI + "deleteStore/{storeName}")
    public ResponseEntity<Void> deleteStore(@PathVariable("ownerId") Long storeOwnerId, @PathVariable("storeName") String storeName) throws StoreNotFoundException, CredentialsException, AccountNotFoundException {
        Store store = storeFinder.findStoreByName(storeName).orElseThrow(StoreNotFoundException::new);
        StoreOwner storeOwner = ownerFinder.findStoreOwnerById(storeOwnerId).orElseThrow(AccountNotFoundException::new);
        if (store.getOwner().equals(storeOwner)) {
            storeModifier.delete(store);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            throw new CredentialsException();
        }
    }

}

