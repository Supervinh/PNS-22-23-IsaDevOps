package mfc.controllers;

import mfc.POJO.Customer;
import mfc.controllers.dto.CustomerDTO;
import mfc.controllers.dto.ErrorDTO;
import mfc.exceptions.*;
import mfc.interfaces.Payment;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.modifier.CustomerRegistration;
import mfc.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

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

    @PostMapping(path = "register", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<CustomerDTO> register(@RequestBody @Valid CustomerDTO cusdto) {
        // Note that there is no validation at all on the CustomerDto mapped
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                            convertCustomerToDto(registry.register(cusdto.getName(), cusdto.getMail(), cusdto.getPassword(), cusdto.getCreditCard()))
                    );
        } catch (AlreadyExistingAccountException e) {
            // Note: Returning 409 (Conflict) can also be seen a security/privacy vulnerability, exposing a service for account enumeration
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(path = "login", consumes = APPLICATION_JSON_VALUE)
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
        }
        else {
            // If the password is correct, we return a 200 (OK) status code
            return ResponseEntity.status(HttpStatus.OK).body(convertCustomerToDto(customer.get()));
        }
    }

    @PostMapping(path= LOGGED_URI + "logout", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> logout(@PathVariable("customerId") UUID customerId, @RequestBody @Valid CustomerDTO cusdto) {
        //TODO : check if the customer is logged in
        return ResponseEntity.status(HttpStatus.OK).body(convertCustomerToDto(finder.findCustomerById(customerId).orElseThrow()));
    }

    private CustomerDTO convertCustomerToDto(Customer customer) { // In more complex cases, we could use ModelMapper
        CustomerDTO dto = new CustomerDTO(customer.getId(), customer.getName(), customer.getMail(), customer.getPassword(), customer.getCreditCard());
        dto.setBalance(customer.getBalance());
        return dto;
    }

    /*@PostMapping(path = LOGGED_URI + "refill", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> refill(@RequestBody @Valid double amount, @PathVariable("customerId") UUID customerId) throws NoCreditCardException, PaymentException, NegativeRefillException {
        Customer res = payment.refillBalance(finder.findCustomerById(customerId).orElseThrow(), amount); //TODO: check if customer exists
        return ResponseEntity.ok().body(*//*"Refill of " + res +" is validated"*//*convertCustomerToDto(res));
    }*/

}

