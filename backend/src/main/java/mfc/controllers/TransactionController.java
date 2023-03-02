package mfc.controllers;

import mfc.POJO.Customer;
import mfc.POJO.Store;
import mfc.controllers.dto.ConvertDTO;
import mfc.controllers.dto.ErrorDTO;
import mfc.controllers.dto.PurchaseDTO;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.modifier.PurchaseRecording;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = TransactionController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class TransactionController {
    public static final String BASE_URI = "/transaction";

    @Autowired
    private CustomerFinder customerFinder;

    @Autowired
    private StoreFinder storeFinder;

    @Autowired
    private PurchaseRecording purchaseRecording;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process Purchase information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @PostMapping(path = "register", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<PurchaseDTO> register(@RequestBody @Valid PurchaseDTO purchaseDto, @RequestParam double cost) {
        // Note that there is no validation at all on the CustomerDto mapped
        try {
            Optional<Customer> customer = customerFinder.findCustomerById(purchaseDto.getCustomerDTO().getId());
            Optional<Store> store = storeFinder.findStoreById(purchaseDto.getStoreDTO().getId());
            if (customer.isEmpty() || store.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(null/*convertDTO.convertPurchaseToDto(purchaseRecording.recordPurchase(customer.get()
                            , cost
                            , store.get()))*/);
        } catch (Exception e) {
            // Note: Returning 409 (Conflict) can also be seen a security/privacy vulnerability, exposing a service for account enumeration
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // TODO: add modifier and explorer for Purchase

}
