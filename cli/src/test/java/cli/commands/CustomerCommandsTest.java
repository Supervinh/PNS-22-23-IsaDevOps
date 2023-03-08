package cli.commands;

import cli.CliContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

@RestClientTest(CustomerCommands.class)
class CustomerCommandsTest {

    @Autowired
    private CustomerCommands client;

    @Autowired
    private MockRestServiceServer server;


    //TODO demander explication pour l'erreur Consider defining a bean of type 'cli.CliContext' in your configuration.
    void registerCustomerTest() {
        server
                .expect(requestTo("/customers/registerCustomer"))
                .andExpect(method(HttpMethod.POST))
                //répond avec un cliCustomer
                .andRespond(withSuccess("{\"id\":1,\"name\":\"test\",\"email\":\"}", MediaType.APPLICATION_JSON));
    }
}
