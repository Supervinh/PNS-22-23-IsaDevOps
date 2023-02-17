package mfc.controllers;

import mfc.POJO.Customer;
import mfc.controllers.dto.*;
import mfc.exceptions.PayoffNotFoundException;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.explorer.CustomerFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = CatalogController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class CatalogController {

    public static final String BASE_URI = "/catalog";

    private final ConvertDTO convertDTO = new ConvertDTO();

    @Autowired
    private CustomerFinder customerFinder;

    @Autowired
    private CatalogExplorer catalogExplorer;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process catalog information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @PostMapping(path = "availableCatalog", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<PayoffDTO> availableCatalog (@RequestBody @Valid CustomerDTO customerDTO) throws PayoffNotFoundException {
        Optional<Customer> customer = customerFinder.findCustomerById(customerDTO.getId());
        if(customer.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertDTO.convertPayoffToDTO(catalogExplorer.availablePayoffs((customer.get()))));
        }
        else throw new PayoffNotFoundException();
    }

    @PostMapping(path = "exploreCatalog", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<PayoffDTO> exploreCatalog (@RequestBody @Valid CustomerDTO customerDTO, @RequestParam String string) throws PayoffNotFoundException {
        Optional<Customer> customer = customerFinder.findCustomerById(customerDTO.getId());
        if(customer.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertDTO.convertPayoffToDTO(catalogExplorer.exploreCatalogue(customer.get(), string)));
        }
        else throw new PayoffNotFoundException();
    }
}
