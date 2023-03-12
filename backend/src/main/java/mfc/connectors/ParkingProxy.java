package mfc.connectors;

import mfc.connectors.externaldto.externaldto.NumberplateDTO;
import mfc.exceptions.ParkingException;
import mfc.interfaces.Parking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.isNull;

@Component
public class ParkingProxy implements Parking {

    @Value("${parking.host.baseurl}")
    private String parkingHostandPort;

    private final RestTemplate restTemplate;

    @Autowired
    public ParkingProxy() {
        this.restTemplate = new RestTemplate();
    }

    public ParkingProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean park(String matriculation) throws ParkingException {
        try {
            String result = restTemplate.postForObject(
                    parkingHostandPort + "/parking/",
                    new NumberplateDTO(matriculation),
                    String.class
            );
            return (!isNull(result) && !result.equals(""));
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST));
            }
            throw new ParkingException();
        }
    }
}
