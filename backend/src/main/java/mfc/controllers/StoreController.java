package mfc.controllers;

import mfc.components.TransactionHandler;
import mfc.controllers.dto.ErrorDTO;
import mfc.controllers.dto.PurchaseDTO;
import mfc.controllers.dto.StoreDTO;
import mfc.exceptions.AlreadyExistingStoreException;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.NegativeCostException;
import mfc.exceptions.StoreNotFoundException;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.explorer.StoreOwnerFinder;
import mfc.interfaces.modifier.StoreRegistration;
import mfc.pojo.Customer;
import mfc.pojo.Purchase;
import mfc.pojo.Store;
import mfc.pojo.StoreOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static mfc.controllers.dto.ConvertDTO.convertPurchaseToDto;
import static mfc.controllers.dto.ConvertDTO.convertStoreToDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = StoreController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class StoreController {

    public static final String BASE_URI = "/store";

    @Autowired
    private StoreFinder storeFinder;

    @Autowired
    private StoreOwnerFinder ownerFinder;

    @Autowired
    private CustomerFinder customerFinder;

    @Autowired
    private StoreRegistration storeRegistration;

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

    @PostMapping(path = "register", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<StoreDTO> register(@RequestBody @Valid StoreDTO storeDTO) {
        try {
            // from DTO to POJO
            Optional<StoreOwner> owner = ownerFinder.findStoreOwnerByName(storeDTO.getOwner());
            if (owner.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertStoreToDto(storeRegistration.register(storeDTO.getName(), storeDTO.getSchedule(), owner.get())));
        } catch (AlreadyExistingStoreException e) {
            // Note: Returning 409 (Conflict) can also be seen a security/privacy vulnerability, exposing a service for account enumeration
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(path = "addPurchase", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<PurchaseDTO> addPurchase(@RequestBody @Valid PurchaseDTO purchaseDTO) {
        try {
            Customer customer = customerFinder.findCustomerByMail(purchaseDTO.getCustomerEmail()).orElseThrow(CustomerNotFoundException::new);
            Store store = storeFinder.findStoreByName(purchaseDTO.getStoreName()).orElseThrow(StoreNotFoundException::new);
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
}
