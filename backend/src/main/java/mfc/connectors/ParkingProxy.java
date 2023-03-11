package mfc.connectors;

import mfc.exceptions.ParkingException;
import mfc.interfaces.Parking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ParkingProxy implements Parking {

    @Value("http://localhost:9191")
    private String parkingHostandPort;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean park(String matriculation) throws ParkingException {
        try {
            ResponseEntity<String> result = restTemplate.postForEntity(
                    parkingHostandPort + "/" + matriculation,
                    null,
                    String.class
            );
            return (result.getStatusCode().equals(HttpStatus.CREATED));
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST));
            }
            throw new ParkingException();
        }
    }
}
