package mfc.controllers;

import mfc.components.PayoffHandler;
import mfc.connectors.externaldto.externaldto.NotificationDTO;
import mfc.controllers.dto.ConvertDTO;
import mfc.controllers.dto.PayoffIndentifierDTO;
import mfc.controllers.dto.PayoffPurchaseDTO;
import mfc.exceptions.CustomerNotFoundException;
import mfc.exceptions.PayoffNotFoundException;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.pojo.Customer;
import mfc.pojo.Payoff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = PayoffController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class PayoffController {

    public static final String BASE_URI = "/payoff";
    public static final String LOGGED_URI = "/{customerId}/";
    private static final Map<String, NotificationDTO> notifications = new HashMap<>();

    @Autowired
    CatalogExplorer catalogExplorer;

    @Autowired
    CustomerFinder customerFinder;

    @Autowired
    PayoffHandler payOffHandler;

    @PostMapping(path = LOGGED_URI + "claimPayoff", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PayoffPurchaseDTO> claimPayoff(@PathVariable("customerId") Long customerId, @RequestBody @Valid PayoffIndentifierDTO payoffIndentifierDTO) {
        try {
            Customer customer = customerFinder.findCustomerById(customerId).orElseThrow(CustomerNotFoundException::new);
            Payoff payoff = catalogExplorer.findPayoff(payoffIndentifierDTO.getPayOffName(), payoffIndentifierDTO.getStoreName()).orElseThrow(PayoffNotFoundException::new);
            return ResponseEntity.ok(ConvertDTO.convertPayoffPurchaseToDTO(payOffHandler.claimPayoff(customer, payoff)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addNotification(NotificationDTO notificationDTO) {
        notifications.put(notificationDTO.getNumberplate(), notificationDTO);
    }

    @GetMapping(path = LOGGED_URI + "getNotification")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable("customerId") UUID customerId) throws CustomerNotFoundException {
        Customer customer = customerFinder.findCustomerById(customerId).orElseThrow(CustomerNotFoundException::new);
        NotificationDTO notificationDTO = notifications.get(customer.getMatriculation());
        if (!isNull(notificationDTO)) {
            notifications.remove(notificationDTO.getNumberplate());
        }
        return ResponseEntity.ok(notificationDTO);
    }

    @PostMapping(path = "notify", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> storeNotif(@RequestBody @Valid NotificationDTO notificationDTO) {
        addNotification(notificationDTO);
        return ResponseEntity.ok("OK");
    }
}