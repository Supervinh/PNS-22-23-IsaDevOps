package mfc.controllers;

import mfc.POJO.Customer;
import mfc.POJO.Payoff;
import mfc.components.PayoffHandler;
import mfc.connectors.externaldto.externaldto.NotificationDTO;
import mfc.controllers.dto.ConvertDTO;
import mfc.controllers.dto.PayoffIndentifierDTO;
import mfc.controllers.dto.PayoffPurchaseDTO;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.PayoffNotFoundException;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.explorer.CustomerFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = PayoffController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class PayoffController {

    public static final String BASE_URI = "/payoff";
    public static final String LOGGED_URI = "/{customerId}/";
    private static Map<String, NotificationDTO> notifications = new HashMap<>();

    @Autowired
    CatalogExplorer catalogExplorer;

    @Autowired
    CustomerFinder customerFinder;

    @Autowired
    PayoffHandler payOffHandler;

    @PostMapping(path = LOGGED_URI + "claimPayoff", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PayoffPurchaseDTO> claimPayoff(@PathVariable("customerId") UUID customerId, @RequestBody @Valid PayoffIndentifierDTO payoffIndentifierDTO) {
        try {
            Customer customer = customerFinder.findCustomerById(customerId).orElseThrow(CustomerNotFoundException::new);
            Payoff payoff = catalogExplorer.findPayoff(payoffIndentifierDTO.getPayOffName(), payoffIndentifierDTO.getStoreName()).orElseThrow(PayoffNotFoundException::new);
            return ResponseEntity.ok(ConvertDTO.convertPayoffPurchaseToDTO(payOffHandler.claimPayoff(customer, payoff)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static NotificationDTO addNotification(NotificationDTO notificationDTO) {
        return notifications.put(notificationDTO.getNumberplate(), notificationDTO);
    }

    @GetMapping(path = LOGGED_URI + "getNotification")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable("customerId") UUID customerId) {
        try {
            Customer customer = customerFinder.findCustomerById(customerId).orElseThrow(CustomerNotFoundException::new);
            if ((notifications.get(customer.getMatriculation()) != null)) {
                return ResponseEntity.ok(notifications.get(customer.getMatriculation()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            throw new RuntimeException("No notification found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.TOO_EARLY).body(null);
        }
    }
}