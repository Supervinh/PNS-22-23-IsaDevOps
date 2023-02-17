package mfc.controllers;

import mfc.POJO.*;
import mfc.controllers.dto.*;
import mfc.exceptions.NotEnoughRightsException;
import mfc.interfaces.explorer.AdminFinder;
import mfc.interfaces.explorer.StoreOwnerFinder;
import mfc.interfaces.modifier.AdminRegistration;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = AdminController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class AdminController {
    public static final String BASE_URI = "/admin";

    private final ConvertDTO convertDTO = new ConvertDTO();

//    @Autowired
//    private StoreOwnerRegistration ownerReg;

    @Autowired
    private AdminRegistration adminReg;

    @Autowired
    private StoreOwnerRegistration ownerReg;

    @Autowired
    private StoreOwnerFinder ownerFind;

    @Autowired
    private AdminFinder adminFind;

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
            Optional<StoreOwner> authorization = ownerFind.findStoreOwnerByMailAndPassword(storeOwnerDTO.getAuthorizationMail(), storeOwnerDTO.getAuthorizationPassword());

            if(authorization.isPresent()){
                throw new NotEnoughRightsException();
            }

            StoreOwner owner = ownerReg.registerStoreOwner(storeOwnerDTO.getName(), storeOwnerDTO.getMail(), storeOwnerDTO.getPassword());

            storeOwnerDTO.setId(owner.getId());
            storeOwnerDTO.setMail(owner.getMail());
            storeOwnerDTO.setPassword(owner.getPassword());
            storeOwnerDTO.setName(owner.getName());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(storeOwnerDTO);
        }
        catch (Exception e) {
            //TODO Handle exception
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(path = "registerAdmin", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<AdminDTO> registerAdmin(@RequestBody @Valid AdminDTO adminDTO) {
        try {
            Optional<Admin> authorization = adminFind.findAdminByMailAndPassword(adminDTO.getAuthorizationMail(), adminDTO.getAuthorizationPassword());

            if(authorization.isPresent()){
                throw new NotEnoughRightsException();
            }

            Admin admin = adminReg.registerAdmin(adminDTO.getName(), adminDTO.getMail(), adminDTO.getPassword());

            adminDTO.setId(admin.getId());
            adminDTO.setMail(admin.getMail());
            adminDTO.setPassword(admin.getPassword());
            adminDTO.setName(admin.getName());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(adminDTO);
        }
        catch (Exception e) {
            //TODO Handle exception
//            if (e instanceof NotEnoughRightsException){
//                ErrorDTO err = new ErrorDTO();
//                err.setError("Cannot create admin account");
//                err.setDetails("Not enough rights");
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
//            }
            // Note: Returning 409 (Conflict) can also be seen a security/privacy vulnerability, exposing a service for account enumeration
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
