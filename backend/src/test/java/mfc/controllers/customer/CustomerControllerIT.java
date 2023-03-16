package mfc.controllers.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import mfc.controllers.CustomerController;
import mfc.controllers.dto.CustomerDTO;
import mfc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//TODO demander pourquoi cet import ne fonctionne pas import org.springframework.transaction.annotation.Transactional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@Transactional
public class CustomerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CustomerRepository customerRepository;

    //TODO décommenter quand on pourra importer Transactional
    //@Test
    void registerCustomerWithInvalidCreditCard() throws Exception{
        mockMvc.perform(post(CustomerController.BASE_URI + "/registerCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "a", "a@a", "pwd", "1", ""))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("a"))
                .andExpect(jsonPath("$.password").value("pwd"))
                .andExpect(jsonPath("$.mail").value("a@a"))
                .andExpect(jsonPath("$.creditCard").value(""))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }
}
