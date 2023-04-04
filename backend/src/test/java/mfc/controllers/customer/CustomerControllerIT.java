package mfc.controllers.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import mfc.controllers.CustomerController;
import mfc.controllers.dto.CustomerDTO;
import mfc.entities.Customer;
import mfc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class CustomerControllerIT {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerCustomerWithInvalidCreditCard() throws Exception {
        mockMvc.perform(post(CustomerController.BASE_URI + "/registerCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "a", "a@a", "pwd", "1", ""))))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void registerCustomerWithoutCreditCard() throws Exception {
        mockMvc.perform(post(CustomerController.BASE_URI + "/registerCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "a", "a@a", "pwd", "", ""))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("a"))
                .andExpect(jsonPath("$.password").value("pwd"))
                .andExpect(jsonPath("$.mail").value("a@a"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerCustomerWithACreditCard() throws Exception {
        mockMvc.perform(post(CustomerController.BASE_URI + "/registerCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "a", "a@a", "pwd", "0123456789", ""))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("a"))
                .andExpect(jsonPath("$.password").value("pwd"))
                .andExpect(jsonPath("$.mail").value("a@a"))
                .andExpect(jsonPath("$.creditCard").value("0123456789"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerCustomerAlreadyExists() throws Exception {
        Customer customer = new Customer("a", "a@a", "pwd", "");
        customerRepository.save(customer);
        mockMvc.perform(post(CustomerController.BASE_URI + "/registerCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "a", "a@a", "pwd", "", ""))))
                .andExpect(status().isConflict());
    }

    @Test
    void registerCustomerWithoutName() throws Exception {
        mockMvc.perform(post(CustomerController.BASE_URI + "/registerCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, null, "a@a", "pwd", "", ""))))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void registerCustomerWithNameEmptyString() throws Exception {
        mockMvc.perform(post(CustomerController.BASE_URI + "/registerCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "", "a@a", "pwd", "", ""))))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void registerCustomerWithoutMail() throws Exception {
        mockMvc.perform(post(CustomerController.BASE_URI + "/registerCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "a", null, "pwd", "", ""))))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void registerCustomerWithMailEmptyString() throws Exception {
        mockMvc.perform(post(CustomerController.BASE_URI + "/registerCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "a", "", "pwd", "", ""))))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void registerCustomerWithoutPassword() throws Exception {
        mockMvc.perform(post(CustomerController.BASE_URI + "/registerCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "a", "a@a", null, "", ""))))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void registerCustomerWithPasswordEmptyString() throws Exception {
        mockMvc.perform(post(CustomerController.BASE_URI + "/registerCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "a", "a@a", "", "", ""))))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void loginCustomer() throws Exception {
        Customer customer = new Customer("a", "a@a", "pwd", "");
        customerRepository.save(customer);
        mockMvc.perform(post(CustomerController.BASE_URI + "/loginCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "default", "a@a", "pwd", "", ""))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("pwd"))
                .andExpect(jsonPath("$.mail").value("a@a"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void modifyCustomerSCreditCard() throws Exception {
        Customer customer = new Customer("a", "a@a", "pwd", "");
        customerRepository.save(customer);
        mockMvc.perform(post(CustomerController.BASE_URI + "/" + customer.getId() + "/modifyCreditCard")
                        .contentType(MediaType.ALL_VALUE)
                        .content("0123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("a"))
                .andExpect(jsonPath("$.password").value("pwd"))
                .andExpect(jsonPath("$.mail").value("a@a"))
                .andExpect(jsonPath("$.creditCard").value("0123456789"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void modifyCustomerSMatriculation() throws Exception {
        Customer customer = new Customer("a", "a@a", "pwd","");
        customerRepository.save(customer);
        mockMvc.perform(post(CustomerController.BASE_URI + "/" + customer.getId() + "/modifyMatriculation")
                        .contentType(MediaType.ALL_VALUE)
                        .content("XX-XX-XX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("a"))
                .andExpect(jsonPath("$.password").value("pwd"))
                .andExpect(jsonPath("$.mail").value("a@a"))
                .andExpect(jsonPath("$.matriculation").value("XX-XX-XX"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

}
