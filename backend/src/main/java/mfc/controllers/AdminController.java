package mfc.controllers;

import mfc.POJO.Admin;
import mfc.controllers.dto.AdminDTO;
import mfc.controllers.dto.ErrorDTO;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.explorer.AdminFinder;
import mfc.interfaces.modifier.AdminRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static mfc.controllers.dto.ConvertDTO.convertAdminToDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = AdminController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class AdminController {
    public static final String BASE_URI = "/admin";

    public static final String LOGGED_URI = "/{adminId}/";

    @Autowired
    private AdminRegistration adminReg;


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

    @PostMapping(path = "registerAdmin", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<AdminDTO> registerAdmin(@RequestBody @Valid AdminDTO adminDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                            convertAdminToDto(adminReg.registerAdmin(adminDTO.getName(), adminDTO.getMail(), adminDTO.getPassword()))
                    );

        } catch (AlreadyExistingAccountException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(path = "loginAdmin", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminDTO> login(@RequestBody @Valid AdminDTO adminto) {
        // Note that there is no validation at all on the CustomerDto mapped
        Optional<Admin> admin = adminFind.findAdminByMail(adminto.getMail());
        if (admin.isEmpty()) {
            // If no admin is found, we return a 404 (Not Found) status code
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!admin.get().getPassword().equals(adminto.getPassword())) {
            // If the password is wrong, we return a 401 (Unauthorized) status code
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            // If the password is correct, we return a 200 (OK) status code
            return ResponseEntity.status(HttpStatus.OK).body(convertAdminToDto(admin.get()));
        }
    }


}
