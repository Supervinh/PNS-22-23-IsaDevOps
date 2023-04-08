package mfc.controllers;

import mfc.controllers.dto.AdminDTO;
import mfc.entities.Admin;
import mfc.exceptions.AccountNotFoundException;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.exceptions.CredentialsException;
import mfc.interfaces.explorer.AdminFinder;
import mfc.interfaces.modifier.AdminRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static mfc.controllers.dto.ConvertDTO.convertAdminToDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = AdminController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class AdminController {
    public static final String BASE_URI = "/admin";

    public static final String LOGGED_URI = "/{adminId}/";

    private final AdminRegistration adminReg;

    private final AdminFinder adminFind;

    @Autowired
    public AdminController(AdminRegistration adminReg, AdminFinder adminFind) {
        this.adminReg = adminReg;
        this.adminFind = adminFind;
    }

    @PostMapping(path = "registerAdmin", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<AdminDTO> registerAdmin(@RequestBody @Valid AdminDTO adminDTO) throws AlreadyExistingAccountException {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertAdminToDto(adminReg.registerAdmin(adminDTO.getName(), adminDTO.getMail(), adminDTO.getPassword())));
    }

    @PostMapping(path = "loginAdmin", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminDTO> login(@RequestBody @Valid AdminDTO adminDTO) throws AccountNotFoundException, CredentialsException {
        // Note that there is no validation at all on the CustomerDto mapped
        Admin admin = adminFind.findAdminByMail(adminDTO.getMail()).orElseThrow(AccountNotFoundException::new);
        if (!admin.getPassword().equals(adminDTO.getPassword())) {
            // If the password is wrong, we return a 401 (Unauthorized) status code
            throw new CredentialsException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(convertAdminToDto(admin));
    }

    @DeleteMapping(path = LOGGED_URI + "deleteAdmin")
    public ResponseEntity<AdminDTO> deleteAdmin(@PathVariable("adminId") Long adminId) throws AccountNotFoundException {
        Admin admin = adminFind.findAdminById(adminId).orElseThrow(AccountNotFoundException::new);
        adminReg.delete(admin);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
