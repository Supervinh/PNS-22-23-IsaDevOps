package mfc.controllers;

import mfc.controllers.dto.ConvertDTO;
import mfc.controllers.dto.ErrorDTO;
import mfc.controllers.dto.StoreDTO;
import mfc.exceptions.AlreadyExistingStoreException;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.modifier.StoreRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = StoreController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class StoreController {

    public static final String BASE_URI = "/store";

    private final ConvertDTO convertDTO = new ConvertDTO();

    @Autowired
    private StoreFinder storeFinder;

    @Autowired
    private StoreRegistration storeRegistration;

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
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertDTO.convertStoreToDto(storeRegistration.register(storeDTO.getOpeningHours(), storeDTO.getOwner(), storeDTO.getName())));
        } catch (AlreadyExistingStoreException e) {
            // Note: Returning 409 (Conflict) can also be seen a security/privacy vulnerability, exposing a service for account enumeration
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    //TODO: add modifier and explorer for Customer
}
