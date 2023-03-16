package mfc.controllers;

import mfc.components.DataGatherer;
import mfc.controllers.dto.DashboardDTO;
import mfc.controllers.dto.ErrorDTO;
import mfc.controllers.dto.StoreOwnerDTO;
import mfc.entities.Store;
import mfc.entities.StoreOwner;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.exceptions.CredentialsException;
import mfc.exceptions.StoreNotFoundException;
import mfc.exceptions.StoreOwnerNotFoundException;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.explorer.StoreOwnerFinder;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static mfc.controllers.dto.ConvertDTO.convertOwnerToDto;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path = StoreOwnerController.BASE_URI, produces = APPLICATION_JSON_VALUE)

public class StoreOwnerController {
    public static final String BASE_URI = "/owner";

    public static final String LOGGED_URI = "/{ownerId}/";

    @Autowired
    private StoreOwnerRegistration ownerReg;

    @Autowired
    private StoreOwnerFinder ownerFind;

    @Autowired
    private StoreFinder storeFinder;

    @Autowired
    private DataGatherer dataGatherer;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process admin registration");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @PostMapping(path = "registerOwner", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<StoreOwnerDTO> registerOwner(@RequestBody @Valid StoreOwnerDTO storeOwnerDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                            convertOwnerToDto(ownerReg.registerStoreOwner(storeOwnerDTO.getName(), storeOwnerDTO.getMail(), storeOwnerDTO.getPassword()))
                    );

        } catch (AlreadyExistingAccountException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(path = "loginOwner", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<StoreOwnerDTO> login(@RequestBody @Valid StoreOwnerDTO ownerto) {
        // Note that there is no validation at all on the CustomerDto mapped
        Optional<StoreOwner> storeOwner = ownerFind.findStoreOwnerByMail(ownerto.getMail());
        if (storeOwner.isEmpty()) {
            // If no customer is found, we return a 404 (Not Found) status code
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!storeOwner.get().getPassword().equals(ownerto.getPassword())) {
            // If the password is wrong, we return a 401 (Unauthorized) status code
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            // If the password is correct, we return a 200 (OK) status code
            return ResponseEntity.status(HttpStatus.OK).body(convertOwnerToDto(storeOwner.get()));
        }
    }

    @PostMapping(path = LOGGED_URI + "dashboard", consumes = ALL_VALUE)
    public ResponseEntity<DashboardDTO> dashboard(@PathVariable("storeOwnerId") Long storeOwnerId, @RequestBody @Valid String storeName) throws StoreNotFoundException, CredentialsException, StoreOwnerNotFoundException {
        Store store = storeFinder.findStoreByName(storeName).orElseThrow(StoreNotFoundException::new);
        StoreOwner storeOwner = ownerFind.findStoreOwnerById(storeOwnerId).orElseThrow(StoreOwnerNotFoundException::new);
        if (store.getOwner().equals(storeOwner)) {
            return ResponseEntity.status(HttpStatus.OK).body(dataGatherer.gather(store));
        } else {
            throw new CredentialsException();
        }
    }
}
