package mfc.controllers;

import mfc.controllers.dto.DashboardDTO;
import mfc.controllers.dto.StoreOwnerDTO;
import mfc.entities.Store;
import mfc.entities.StoreOwner;
import mfc.exceptions.AccountNotFoundException;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.exceptions.CredentialsException;
import mfc.exceptions.StoreNotFoundException;
import mfc.interfaces.StoreDataGathering;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.explorer.StoreOwnerFinder;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final StoreOwnerRegistration ownerReg;
    private final StoreOwnerFinder ownerFind;
    private final StoreFinder storeFinder;
    private final StoreDataGathering storeDataGathering;

    @Autowired
    public StoreOwnerController(StoreOwnerRegistration ownerReg, StoreOwnerFinder ownerFind, StoreFinder storeFinder, StoreDataGathering storeDataGathering) {
        this.ownerReg = ownerReg;
        this.ownerFind = ownerFind;
        this.storeFinder = storeFinder;
        this.storeDataGathering = storeDataGathering;
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
    public ResponseEntity<DashboardDTO> dashboard(@PathVariable("ownerId") Long storeOwnerId, @RequestBody @Valid String storeName) throws StoreNotFoundException, CredentialsException, AccountNotFoundException {
        Store store = storeFinder.findStoreByName(storeName).orElseThrow(StoreNotFoundException::new);
        StoreOwner storeOwner = ownerFind.findStoreOwnerById(storeOwnerId).orElseThrow(AccountNotFoundException::new);
        if (store.getOwner().equals(storeOwner)) {
            return ResponseEntity.status(HttpStatus.OK).body(storeDataGathering.gather(store));
        } else {
            throw new CredentialsException();
        }
    }

    @DeleteMapping(path = LOGGED_URI + "deleteStoreOwner")
    public ResponseEntity<StoreOwnerDTO> deleteStoreOwner(@PathVariable("ownerId") Long storeOwnerId) {
        try {
            StoreOwner storeOwner = ownerFind.findStoreOwnerById(storeOwnerId).orElseThrow(AccountNotFoundException::new);
            return ResponseEntity.ok().body(convertOwnerToDto(ownerReg.delete(storeOwner)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
