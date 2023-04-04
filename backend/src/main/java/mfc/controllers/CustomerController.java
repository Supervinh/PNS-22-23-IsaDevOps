package mfc.controllers;

import mfc.controllers.dto.ConvertDTO;
import mfc.controllers.dto.CustomerDTO;
import mfc.controllers.dto.ErrorDTO;
import mfc.controllers.dto.SurveyDTO;
import mfc.entities.Customer;
import mfc.entities.Store;
import mfc.exceptions.*;
import mfc.interfaces.Payment;
import mfc.interfaces.SurveyFinder;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.modifier.CustomerProfileModifier;
import mfc.interfaces.modifier.CustomerRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static mfc.controllers.dto.ConvertDTO.convertCustomerToDto;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = CustomerController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class CustomerController {

    public static final String BASE_URI = "/customers";
    public static final String LOGGED_URI = "/{customerId}/";

    private final CustomerRegistration customerRegistration;

    private final CustomerFinder customerFinder;

    private final CustomerProfileModifier customerProfileModifier;

    private final Payment payment;

    private final SurveyFinder surveyFinder;

    private final StoreFinder storeFinder;

    @Autowired
    public CustomerController(CustomerRegistration customerRegistration, CustomerFinder customerFinder, CustomerProfileModifier customerProfileModifier, Payment payment, SurveyFinder surveyFinder, StoreFinder storeFinder) {
        this.customerRegistration = customerRegistration;
        this.customerFinder = customerFinder;
        this.customerProfileModifier = customerProfileModifier;
        this.payment = payment;
        this.surveyFinder = surveyFinder;
        this.storeFinder = storeFinder;
    }

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
                    .body(convertCustomerToDto(customerRegistration.register(cusdto.getName(), cusdto.getMail(), cusdto.getPassword(), creditCard)));
        } catch (AlreadyExistingAccountException e) {
            // Note: Returning 409 (Conflict) can also be seen a security/privacy vulnerability, exposing a service for account enumeration
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(path = "loginCustomer", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> login(@RequestBody @Valid CustomerDTO cusdto) throws CustomerNotFoundException, CredentialsException {
        Optional<Customer> customer = customerFinder.findCustomerAtConnexion(cusdto.getMail(), cusdto.getPassword());
        if (customer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        CustomerDTO c = convertCustomerToDto(customer.get());
        List<SurveyDTO> surveys = surveyFinder.findByCustomerDidntAnswered(customer.get()).stream().map(ConvertDTO::convertToSurveyDisplayDto).toList();
        c.setSurveysToAnswer(surveys);
        c.setLastConnexion(customer.get().getLastConnexion());
        return ResponseEntity.status(HttpStatus.OK).body(c);

    }

    @PostMapping(path = LOGGED_URI + "modifyCreditCard", consumes = ALL_VALUE)
    public ResponseEntity<CustomerDTO> modifyCreditCard(@PathVariable("customerId") Long customerId, @RequestBody @Valid String creditCard) throws CustomerNotFoundException {
        if (!creditCard.matches("\\d{10}+")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(
                convertCustomerToDto(
                        customerProfileModifier.recordCreditCard(
                                customerFinder.findCustomerById(customerId).orElseThrow(), creditCard)));

    }

    @PostMapping(path = LOGGED_URI + "modifyMatriculation", consumes = ALL_VALUE)
    public ResponseEntity<CustomerDTO> modifyMatriculation(@PathVariable("customerId") Long customerId, @RequestBody @Valid String matriculation) throws CustomerNotFoundException {
        return ResponseEntity.ok().body(convertCustomerToDto(customerProfileModifier.recordMatriculation(customerFinder.findCustomerById(customerId).orElseThrow(), matriculation)));
    }

    @PostMapping(path = LOGGED_URI + "refill", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> refill(@RequestBody @Valid double amount, @PathVariable("customerId") Long customerId) throws NoCreditCardException, PaymentException, NegativeRefillException {
        Optional<Customer> customer = customerFinder.findCustomerById(customerId);
        if (customer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if(customer.get().getCreditCard().equals("")){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (amount <= 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().body(convertCustomerToDto(payment.refillBalance(customer.get(), amount)));
    }

    @DeleteMapping(path = LOGGED_URI + "deleteCustomer")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable("customerId") Long customerId) throws CustomerNotFoundException, NoCorrespongingAccountException {
        try {
            Customer customer = customerFinder.findCustomerById(customerId).orElseThrow(CustomerNotFoundException::new);
            customerRegistration.delete(customer);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(path = LOGGED_URI + "removeFavoriteStore", consumes = ALL_VALUE)
    public ResponseEntity<CustomerDTO> removeFavoriteStore(@PathVariable("customerId") Long customerId, @RequestBody @Valid String storeName) throws CustomerNotFoundException, StoreNotFoundException {

            Optional<Customer> customer = customerFinder.findCustomerById(customerId);
            Optional<Store> store = storeFinder.findStoreByName(storeName);
            if(customer.isEmpty() || store.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            if(!customer.get().getFavoriteStores().contains(store.get())){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.ok().body(ConvertDTO.convertCustomerToDto(customerProfileModifier.removeFavoriteStore(customer.get(), store.get())));

    }

    @PostMapping(path = LOGGED_URI + "addFavoriteStore", consumes = ALL_VALUE)
    public ResponseEntity<CustomerDTO> addFavoriteStore(@PathVariable("customerId") Long customerId, @RequestBody @Valid String storeName) throws CustomerNotFoundException, StoreNotFoundException, StoreAlreadyRegisteredException {
            Optional<Customer> customer = customerFinder.findCustomerById(customerId);
            Optional<Store> store = storeFinder.findStoreByName(storeName);
            if(customer.isEmpty() || store.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            if(customer.get().getFavoriteStores().contains(store.get())){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.ok().body(ConvertDTO.convertCustomerToDto(customerProfileModifier.recordNewFavoriteStore(customer.get(), store.get())));
    }
}

