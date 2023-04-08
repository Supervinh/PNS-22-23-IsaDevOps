package mfc.controllers;

import mfc.connectors.externaldto.externaldto.NotificationDTO;
import mfc.controllers.dto.ConvertDTO;
import mfc.controllers.dto.PayoffIndentifierDTO;
import mfc.controllers.dto.PayoffPurchaseDTO;
import mfc.entities.Customer;
import mfc.entities.Payoff;
import mfc.exceptions.*;
import mfc.interfaces.PayOffProcessor;
import mfc.interfaces.explorer.CatalogExplorer;
import mfc.interfaces.explorer.CustomerFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = PayoffController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class PayoffController {

    public static final String BASE_URI = "/payoff";
    public static final String LOGGED_URI = "/{customerId}/";
    private static final Map<String, NotificationDTO> notifications = new HashMap<>();

    private final CatalogExplorer catalogExplorer;

    private final CustomerFinder customerFinder;

    private final PayOffProcessor payOffProcessor;

    @Autowired
    public PayoffController(CatalogExplorer catalogExplorer, CustomerFinder customerFinder, PayOffProcessor payOffProcessor) {
        this.catalogExplorer = catalogExplorer;
        this.customerFinder = customerFinder;
        this.payOffProcessor = payOffProcessor;
    }

    public static void addNotification(NotificationDTO notificationDTO) {
        notifications.put(notificationDTO.getNumberplate(), notificationDTO);
    }

    @PostMapping(path = LOGGED_URI + "claimPayoff", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<PayoffPurchaseDTO> claimPayoff(@PathVariable("customerId") Long customerId, @RequestBody @Valid PayoffIndentifierDTO payoffIndentifierDTO) throws PayoffNotFoundException, AccountNotFoundException, NoMatriculationException, NegativePointCostException, VFPExpiredException, ParkingException, InsufficientBalanceException, NoPreviousPurchaseException {
        Customer customer = customerFinder.findCustomerById(customerId).orElseThrow(AccountNotFoundException::new);
        Payoff payoff = catalogExplorer.findPayoff(payoffIndentifierDTO.getPayOffName(), payoffIndentifierDTO.getStoreName()).orElseThrow(PayoffNotFoundException::new);
        return ResponseEntity.ok(ConvertDTO.convertPayoffPurchaseToDTO(payOffProcessor.claimPayoff(customer, payoff)));
    }

    @GetMapping(path = LOGGED_URI + "getNotification")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable("customerId") long customerId) {
        try {
            Customer customer = customerFinder.findCustomerById(customerId).orElseThrow(AccountNotFoundException::new);
            NotificationDTO notificationDTO = notifications.get(customer.getMatriculation());
            if (!isNull(notificationDTO)) {
                notifications.remove(notificationDTO.getNumberplate());
            }
            return ResponseEntity.ok(notificationDTO);
        } catch (Exception e) {
            return ResponseEntity.ok(null);
        }
    }

    @PostMapping(path = "notify", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> storeNotif(@RequestBody @Valid NotificationDTO notificationDTO) {
        addNotification(notificationDTO);
        return ResponseEntity.ok("OK");
    }
}