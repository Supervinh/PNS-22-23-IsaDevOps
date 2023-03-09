package cli.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

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
                .andRespond(withSuccess("{\"id\":1,\"name\":\"test\",\"email\":\"}", MediaType.APPLICATION_JSON));
    }
}
