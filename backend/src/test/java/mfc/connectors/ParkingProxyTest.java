package mfc.connectors;

import mfc.exceptions.ParkingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class ParkingProxyTest {

    @MockBean
    RestTemplate restTemplate;

    ParkingProxy parkingProxy;

    @BeforeEach
    void setUp() {
        parkingProxy = new ParkingProxy(restTemplate);
        when(restTemplate.postForObject(anyString(), any(), any())).thenReturn("test");
    }

    @Test
    void parkNormal() throws ParkingException {
        assertTrue(parkingProxy.park("ABC"));
    }

    @Test
    void parkNormalNoMat() {
        assertThrows(Exception.class, () -> parkingProxy.park(null));
    }

    @Test
    void parkNormalEmptyMat() {
        assertThrows(Exception.class, () -> parkingProxy.park(""));
    }
}