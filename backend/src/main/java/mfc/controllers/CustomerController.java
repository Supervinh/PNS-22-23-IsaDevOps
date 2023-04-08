package mfc.controllers;

import mfc.controllers.dto.ConvertDTO;
import mfc.controllers.dto.CustomerDTO;
import mfc.controllers.dto.SurveyDTO;
import mfc.entities.Customer;
import mfc.entities.Store;
import mfc.exceptions.*;
import mfc.interfaces.Payment;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.explorer.SurveyFinder;
import mfc.interfaces.modifier.CustomerProfileModifier;
import mfc.interfaces.modifier.CustomerRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping(path = "registerCustomer", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<CustomerDTO> register(@RequestBody @Valid CustomerDTO cusdto) throws AlreadyExistingAccountException {
        // Note that there is no validation at all on the CustomerDto mapped
        String creditCard = cusdto.getCreditCard();
        if (!creditCard.equals("") && !creditCard.matches("\\d{10}+")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(convertCustomerToDto(customerRegistration.register(cusdto.getName(), cusdto.getMail(), cusdto.getPassword(), creditCard)));
    }

    @PostMapping(path = "loginCustomer", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> login(@RequestBody @Valid CustomerDTO cusdto) throws AccountNotFoundException, CredentialsException {
        Customer customer = customerFinder.findCustomerAtConnexion(cusdto.getMail(), cusdto.getPassword()).orElseThrow(AccountNotFoundException::new);
        CustomerDTO c = convertCustomerToDto(customer);
        List<SurveyDTO> surveys = surveyFinder.findByCustomerDidntAnswered(customer).stream().map(ConvertDTO::convertToSurveyDisplayDto).toList();
        c.setSurveysToAnswer(surveys);
        c.setLastConnexion(customer.getLastConnexion());
        return ResponseEntity.status(HttpStatus.OK).body(c);

    }

    @PostMapping(path = LOGGED_URI + "modifyCreditCard", consumes = ALL_VALUE)
    public ResponseEntity<CustomerDTO> modifyCreditCard(@PathVariable("customerId") Long customerId, @RequestBody @Valid String creditCard) throws AccountNotFoundException {
        if (!creditCard.matches("\\d{10}+")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(convertCustomerToDto(customerProfileModifier.recordCreditCard(customerFinder.findCustomerById(customerId).orElseThrow(), creditCard)));

    }

    @PostMapping(path = LOGGED_URI + "modifyMatriculation", consumes = ALL_VALUE)
    public ResponseEntity<CustomerDTO> modifyMatriculation(@PathVariable("customerId") Long customerId, @RequestBody @Valid String matriculation) throws AccountNotFoundException {
        return ResponseEntity.ok().body(convertCustomerToDto(customerProfileModifier.recordMatriculation(customerFinder.findCustomerById(customerId).orElseThrow(), matriculation)));
    }

    @PostMapping(path = LOGGED_URI + "refill", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> refill(@RequestBody @Valid double amount, @PathVariable("customerId") Long customerId) throws NoCreditCardException, PaymentException, NegativeRefillException, AccountNotFoundException {
        Customer customer = customerFinder.findCustomerById(customerId).orElseThrow(AccountNotFoundException::new);
        if (customer.getCreditCard().equals("")) {
            throw new NoCreditCardException();
        }
        if (amount <= 0) {
            throw new NegativeRefillException();
        }
        return ResponseEntity.ok().body(convertCustomerToDto(payment.refillBalance(customer, amount)));
    }

    @DeleteMapping(path = LOGGED_URI + "deleteCustomer")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable("customerId") Long customerId) throws AccountNotFoundException {
        Customer customer = customerFinder.findCustomerById(customerId).orElseThrow(AccountNotFoundException::new);
        customerRegistration.delete(customer);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(path = LOGGED_URI + "removeFavoriteStore", consumes = ALL_VALUE)
    public ResponseEntity<CustomerDTO> removeFavoriteStore(@PathVariable("customerId") Long customerId, @RequestBody @Valid String storeName) throws AccountNotFoundException, StoreNotFoundException {
        Customer customer = customerFinder.findCustomerById(customerId).orElseThrow(AccountNotFoundException::new);
        Store store = storeFinder.findStoreByName(storeName).orElseThrow(StoreNotFoundException::new);
        return ResponseEntity.ok().body(ConvertDTO.convertCustomerToDto(customerProfileModifier.removeFavoriteStore(customer, store)));

    }

    @PostMapping(path = LOGGED_URI + "addFavoriteStore", consumes = ALL_VALUE)
    public ResponseEntity<CustomerDTO> addFavoriteStore(@PathVariable("customerId") Long customerId, @RequestBody @Valid String storeName) throws AccountNotFoundException, StoreNotFoundException, AlreadyRegisteredStoreException {
        Customer customer = customerFinder.findCustomerById(customerId).orElseThrow(AccountNotFoundException::new);
        Store store = storeFinder.findStoreByName(storeName).orElseThrow(StoreNotFoundException::new);
        return ResponseEntity.ok().body(ConvertDTO.convertCustomerToDto(customerProfileModifier.recordNewFavoriteStore(customer, store)));
    }
}

