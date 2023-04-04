package mfc.controllers.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import mfc.controllers.CustomerController;
import mfc.controllers.dto.CustomerDTO;
import mfc.entities.Customer;
import mfc.interfaces.explorer.CustomerFinder;
import mfc.interfaces.modifier.CustomerBalancesModifier;
import mfc.interfaces.modifier.CustomerProfileModifier;
import mfc.interfaces.modifier.CustomerRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CustomerRegistration customerRegistration;

    @MockBean
    CustomerFinder customerFinder;

    @MockBean
    CustomerProfileModifier customerProfileModifier;

    @MockBean
    CustomerBalancesModifier customerBalancesModifier;

    @Test
    void registerCustomerWithoutCreditCard() throws Exception {
        Customer customer = new Customer("a", "a@a", "pwd", "");
        when(customerRegistration.register(customer.getName(), customer.getMail(), customer.getPassword(), customer.getCreditCard())).thenReturn(customer);
        mockMvc.perform(post(CustomerController.BASE_URI + "/registerCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "a", "a@a", "pwd", "", ""))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("a"))
                .andExpect(jsonPath("$.password").value("pwd"))
                .andExpect(jsonPath("$.mail").value("a@a"))
                .andExpect(jsonPath("$.creditCard").value(""))
                .andExpect(jsonPath("$.matriculation").value(""))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerCustomerWithACreditCard() throws Exception {
        Customer customer = new Customer("a", "a@a", "pwd", "0123456789");
        when(customerRegistration.register(customer.getName(), customer.getMail(), customer.getPassword(), customer.getCreditCard())).thenReturn(customer);
        mockMvc.perform(post(CustomerController.BASE_URI + "/registerCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "a", "a@a", "pwd", "0123456789", ""))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("a"))
                .andExpect(jsonPath("$.password").value("pwd"))
                .andExpect(jsonPath("$.mail").value("a@a"))
                .andExpect(jsonPath("$.creditCard").value("0123456789"))
                .andExpect(jsonPath("$.matriculation").value(""))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void loginCustomer() throws Exception {
        Customer customer = new Customer("a", "a@a", "pwd", "");
        when(customerFinder.findCustomerAtConnexion(customer.getMail(), customer.getPassword())).thenReturn(Optional.of(customer));
        mockMvc.perform(post(CustomerController.BASE_URI + "/loginCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "default", "a@a", "pwd", "", ""))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("a"))
                .andExpect(jsonPath("$.password").value("pwd"))
                .andExpect(jsonPath("$.mail").value("a@a"))
                .andExpect(jsonPath("$.creditCard").value(""))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void modifyCustomerSCreditCard() throws Exception {
        Long customerId = 1L;
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(customerId);
        when(customer.getName()).thenReturn("a");
        when(customer.getMail()).thenReturn("a@a");
        when(customer.getPassword()).thenReturn("pwd");
        when(customer.getCreditCard()).thenReturn("0123456789");
        when(customerFinder.findCustomerById(customerId)).thenReturn(Optional.of(customer));
        when(customerProfileModifier.recordCreditCard
                (customer,
                        "0123456789")).thenReturn(customer);
        mockMvc.perform(post(CustomerController.BASE_URI + "/" + customerId + "/modifyCreditCard")
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
        Long customerId = 1L;
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(customerId);
        when(customer.getName()).thenReturn("a");
        when(customer.getMail()).thenReturn("a@a");
        when(customer.getPassword()).thenReturn("pwd");
        when(customer.getMatriculation()).thenReturn("XX-XX-XX");
        when(customerFinder.findCustomerById(customer.getId())).thenReturn(Optional.of(customer));
        when(customerProfileModifier.recordMatriculation
                (customer,"XX-XX-XX")).thenReturn(customer);
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
