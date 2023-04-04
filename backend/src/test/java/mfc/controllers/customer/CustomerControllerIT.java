package mfc.controllers.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import mfc.controllers.CustomerController;
import mfc.controllers.dto.CustomerDTO;
import mfc.entities.Customer;
import mfc.entities.Store;
import mfc.entities.StoreOwner;
import mfc.interfaces.modifier.CustomerProfileModifier;
import mfc.interfaces.modifier.CustomerRegistration;
import mfc.interfaces.modifier.StoreModifier;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    private MockMvc mockMvc;

    @Autowired
    CustomerRegistration customerRegistration;

    @Autowired
    StoreOwnerRegistration storeOwnerRegistration;

    @Autowired
    StoreModifier storeModifier;

    @Autowired
    CustomerProfileModifier customerProfileModifier;

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
        customerRegistration.register(customer.getName(), customer.getMail(), customer.getPassword(), customer.getCreditCard());
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
        customerRegistration.register(customer.getName(), customer.getMail(), customer.getPassword(), customer.getCreditCard());
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
    void loginCustomerWrongMail() throws Exception{
        mockMvc.perform(post(CustomerController.BASE_URI + "/loginCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerDTO(null, "default", "a@a", "pwd", "", ""))))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }
    
    @Test
    void modifyCustomerSCreditCard() throws Exception {
        Customer customer = customerRegistration.register("a", "a@a", "pwd", "");
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
    void modifyCustomerInvalidCreditCard() throws Exception {
        Customer customer = customerRegistration.register("a", "a@a", "pwd", "");
        mockMvc.perform(post(CustomerController.BASE_URI + "/" + customer.getId() + "/modifyCreditCard")
                        .contentType(MediaType.ALL_VALUE)
                        .content("012345678"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void modifyCustomerSMatriculation() throws Exception {
        Customer customer = customerRegistration.register("a", "a@a", "pwd", "");
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

    @Test
    void deleteCustomer() throws Exception {
        Customer customer = customerRegistration.register("a", "a@a", "pwd", "");
        mockMvc.perform(delete(CustomerController.BASE_URI + "/" + customer.getId() + "/deleteCustomer")
                        .contentType(MediaType.ALL_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomerWithWrongId() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URI + "/0/deleteCustomer")
                        .contentType(MediaType.ALL_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCustomerWithEmptyId() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URI + "/deleteCustomer")
                        .contentType(MediaType.ALL_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeCustomerFavoriteStore() throws Exception {
        Customer customer = customerRegistration.register("a", "a@a", "pwd", "");
        StoreOwner storeOwner = storeOwnerRegistration.registerStoreOwner("StoreOwner", "store@owner", "pwd");
        Store store = storeModifier.register("store", new HashMap<>(), storeOwner);
        List<Store> favoriteStores = new ArrayList<>();
        customerProfileModifier.recordNewFavoriteStore(customer, store);
        mockMvc.perform(post(CustomerController.BASE_URI + "/" + customer.getId() + "/removeFavoriteStore")
                        .contentType(MediaType.ALL_VALUE)
                        .content(String.valueOf(store.getName())))
                .andExpect(status().isOk());
    }

    @Test
    void removeCustomerFavoriteStoreWithWrongId() throws Exception {
        mockMvc.perform(post(CustomerController.BASE_URI + "/0/removeFavoriteStore")
                        .contentType(MediaType.ALL_VALUE)
                        .content("store"))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeCustomerFavoriteStoreWithEmptyId() throws Exception {
        mockMvc.perform(post(CustomerController.BASE_URI + "/removeFavoriteStore")
                        .contentType(MediaType.ALL_VALUE)
                        .content("store"))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeCustomerFavoriteStoreWithWrongStoreName() throws Exception {
        Customer customer = customerRegistration.register("a", "a@a", "pwd", "");
        mockMvc.perform(post(CustomerController.BASE_URI + "/" + customer.getId() + "/removeFavoriteStore")
                        .contentType(MediaType.ALL_VALUE)
                        .content("store"))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeCustomerFavoriteStoreWithEmptyStoreName() throws Exception {
        Customer customer = customerRegistration.register("a", "a@a", "pwd", "");
        mockMvc.perform(post(CustomerController.BASE_URI + "/" + customer.getId() + "/removeFavoriteStore")
                        .contentType(MediaType.ALL_VALUE)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addCustomerFavoriteStore() throws Exception {
        Customer customer = customerRegistration.register("a", "a@a", "pwd", "");
        StoreOwner storeOwner = storeOwnerRegistration.registerStoreOwner("StoreOwner", "store@owner", "pwd");
        Store store = storeModifier.register("store", new HashMap<>(), storeOwner);
        mockMvc.perform(post(CustomerController.BASE_URI + "/" + customer.getId() + "/addFavoriteStore")
                        .contentType(MediaType.ALL_VALUE)
                        .content(String.valueOf(store.getName())))
                .andExpect(status().isOk());
    }

    @Test
    void addCustomerFavoriteStoreWithWrongId() throws Exception {
        mockMvc.perform(post(CustomerController.BASE_URI + "/0/addFavoriteStore")
                        .contentType(MediaType.ALL_VALUE)
                        .content("store"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addCustomerFavoriteStoreWithEmptyId() throws Exception {
        mockMvc.perform(post(CustomerController.BASE_URI + "/addFavoriteStore")
                        .contentType(MediaType.ALL_VALUE)
                        .content("store"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addCustomerFavoriteStoreWithWrongStoreName() throws Exception {
        Customer customer = customerRegistration.register("a", "a@a", "pwd", "");
        mockMvc.perform(post(CustomerController.BASE_URI + "/" + customer.getId() + "/addFavoriteStore")
                        .contentType(MediaType.ALL_VALUE)
                        .content("store"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addCustomerFavoriteStoreWithEmptyStoreName() throws Exception {
        Customer customer = customerRegistration.register("a", "a@a", "pwd", "");
        mockMvc.perform(post(CustomerController.BASE_URI + "/" + customer.getId() + "/addFavoriteStore")
                        .contentType(MediaType.ALL_VALUE)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addCustomerFavoriteStoreWithAlreadyFavoriteStore() throws Exception {
        Customer customer = customerRegistration.register("a", "a@a", "pwd", "");
        StoreOwner storeOwner = storeOwnerRegistration.registerStoreOwner("StoreOwner", "store@owner", "pwd");
        Store store = storeModifier.register("store", new HashMap<>(), storeOwner);
        customerProfileModifier.recordNewFavoriteStore(customer, store);
        mockMvc.perform(post(CustomerController.BASE_URI + "/" + customer.getId() + "/addFavoriteStore")
                        .contentType(MediaType.ALL_VALUE)
                        .content(String.valueOf(store.getName())))
                .andExpect(status().isConflict());
    }

}
