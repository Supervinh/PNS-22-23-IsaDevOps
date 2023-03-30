package mfc.controllers;

import mfc.controllers.dto.CustomerDTO;
import mfc.controllers.dto.ErrorDTO;
import mfc.entities.Customer;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.NegativeCostException;
import mfc.interfaces.Payment;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.modifier.CustomerProfileModifier;
import mfc.interfaces.modifier.CustomerRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static mfc.controllers.dto.ConvertDTO.convertCustomerToDto;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = CustomerController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class CustomerController {

    public static final String BASE_URI = "/customers";
    public static final String LOGGED_URI = "/{customerId}/";

    @Autowired
    private CustomerRegistration registry;

    @Autowired
    private CustomerFinder finder;

    @Autowired
    private CustomerProfileModifier modifier;

    @Autowired
    private Payment payment;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process Customer information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @PostMapping(path = "registerCustomer", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<CustomerDTO> register(@RequestBody @Valid CustomerDTO cusdto) {
        // Note that there is no validation at all on the CustomerDto mapped
        String creditCard = cusdto.getCreditCard();
        if (!creditCard.equals("") && !creditCard.matches("\\d{10}+")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                            convertCustomerToDto(registry.register(cusdto.getName(), cusdto.getMail(), cusdto.getPassword(), creditCard))
                    );
        } catch (AlreadyExistingAccountException e) {
            // Note: Returning 409 (Conflict) can also be seen a security/privacy vulnerability, exposing a service for account enumeration
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(path = "loginCustomer", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> login(@RequestBody @Valid CustomerDTO cusdto) {
        // Note that there is no validation at all on the CustomerDto mapped
        Optional<Customer> customer = finder.findCustomerByMail(cusdto.getMail());
        if (customer.isEmpty()) {
            // If no customer is found, we return a 404 (Not Found) status code
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!customer.get().getPassword().equals(cusdto.getPassword())) {
            // If the password is wrong, we return a 401 (Unauthorized) status code
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            // If the password is correct, we return a 200 (OK) status code
            return ResponseEntity.status(HttpStatus.OK).body(convertCustomerToDto(customer.get()));
        }
    }

    @PostMapping(path = LOGGED_URI + "modifyCreditCard", consumes = ALL_VALUE)
    public ResponseEntity<CustomerDTO> modifyCreditCard(@PathVariable("customerId") Long customerId, @RequestBody @Valid String creditCard) throws CustomerNotFoundException {
        if (!creditCard.matches("\\d{10}+")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(
                convertCustomerToDto(
                        modifier.recordCreditCard(
                                finder.findCustomerById(customerId).orElseThrow(), creditCard)));

    }

    @PostMapping(path = LOGGED_URI + "modifyMatriculation", consumes = ALL_VALUE)
    public ResponseEntity<CustomerDTO> modifyMatriculation(@PathVariable("customerId") Long customerId, @RequestBody @Valid String matriculation) throws CustomerNotFoundException {
        return ResponseEntity.ok().body(
                convertCustomerToDto(
                        modifier.recordMatriculation(
                                finder.findCustomerById(customerId).orElseThrow(), matriculation)));
    }

    @PostMapping(path = LOGGED_URI + "refill", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> refill(@RequestBody @Valid double amount, @PathVariable("customerId") Long customerId) {
        try {
            Customer customer = finder.findCustomerById(customerId).orElseThrow(CustomerNotFoundException::new);
            if (amount < 0) {
                throw new NegativeCostException();
            }
            return ResponseEntity.ok().body(convertCustomerToDto(payment.refillBalance(customer, amount)));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping(path = LOGGED_URI + "deleteCustomer")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable("customerId") Long customerId) {
        try {
            Customer customer = finder.findCustomerById(customerId).orElseThrow(CustomerNotFoundException::new);
            return ResponseEntity.ok().body(convertCustomerToDto(registry.delete(customer)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

