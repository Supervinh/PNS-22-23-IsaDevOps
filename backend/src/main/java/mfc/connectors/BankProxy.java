package mfc.connectors;

import mfc.connectors.externaldto.externaldto.PaymentDTO;
import mfc.entities.Customer;
import mfc.interfaces.Bank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class BankProxy implements Bank {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${bank.host.baseurl}")
    private String bankHostandPort;

    @Override
    public boolean pay(Customer customer, double value) {
        try {
            ResponseEntity<PaymentDTO> result = restTemplate.postForEntity(
                    bankHostandPort + "/cctransactions",
                    new PaymentDTO(customer.getCreditCard(), value),
                    PaymentDTO.class
            );
            return (result.getStatusCode().equals(HttpStatus.CREATED));
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return false;
            }
            throw errorException;
        }
    }

}