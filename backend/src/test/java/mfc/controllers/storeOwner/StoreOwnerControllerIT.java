package mfc.controllers.storeOwner;

import com.fasterxml.jackson.databind.ObjectMapper;
import mfc.controllers.StoreOwnerController;
import mfc.controllers.dto.StoreOwnerDTO;
import mfc.entities.StoreOwner;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.explorer.StoreOwnerFinder;
import mfc.interfaces.modifier.StoreModifier;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class StoreOwnerControllerIT {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    StoreOwnerFinder storeOwnerFinder;
    @Autowired
    StoreOwnerRegistration storeOwnerRegistration;
    @Autowired
    StoreModifier storeModifier;
    @Autowired
    StoreFinder storeFinder;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerStoreOwner() throws Exception {
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "a", "a@a", "pwd"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("a"))
                .andExpect(jsonPath("$.password").value("pwd"))
                .andExpect(jsonPath("$.mail").value("a@a"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyName() throws Exception {
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", "a@a", "pwd"))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyPassword() throws Exception {
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "a", "a@a", ""))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyNameAndMail() throws Exception {
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", null, "pwd"))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyNameAndPassword() throws Exception {
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", "a@a", ""))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyMailAndPassword() throws Exception {
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "a", null, ""))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyNameAndMailAndPassword() throws Exception {
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", null, ""))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyBody() throws Exception {
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyBodyAndNull() throws Exception {
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, null, null, null))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginStoreOwner() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        storeOwnerRegistration.registerStoreOwner(owner.getName(), owner.getMail(), owner.getPassword());
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "a", "a@a", "pwd"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("a"))
                .andExpect(jsonPath("$.password").value("pwd"))
                .andExpect(jsonPath("$.mail").value("a@a"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginStoreOwnerWithEmptyMail() throws Exception {
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", "", "pwd"))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginStoreOwnerWithEmptyPassword() throws Exception {
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", "a@a", ""))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginStoreOwnerWithEmptyMailAndPassword() throws Exception {
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "a", "", ""))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginStoreOwnerWithWrongMail() throws Exception {
        storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "a", "b@a", "pwd"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void loginStoreOwnerWithWrongPassword() throws Exception {
        storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "a", "a@a", "pwd1"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginStoreOwnerWithEmptyBody() throws Exception {
        storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginStoreOwnerWithEmptyBodyAndNull() throws Exception {
        storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, null, null, null))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginStoreOwnerWithEmptyBodyAndEmpty() throws Exception {
        storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", "", ""))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void retrieveDashboard() throws Exception {
        StoreOwner owner = storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        Map<String, String> storeSchedule = new HashMap<>();
        storeModifier.register(storeName, storeSchedule, owner);
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/" + owner.getId() + "/dashboard/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storeName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfCustomers").value("0.0"))
                .andExpect(jsonPath("$.numberOfSales").value("0.0"))
                .andExpect(jsonPath("$.numberOfGivenPayoffs").value("0.0"))
                .andExpect(jsonPath("$.salesVolumes").value("0.0"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void retrieveDashBordForUnknownStoreOwner() throws Exception {
        StoreOwner owner = storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        Map<String, String> storeSchedule = new HashMap<>();
        storeModifier.register(storeName, storeSchedule, owner);
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/" + -1L + "/dashboard/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(storeName)).andExpect(status().isNotFound());
    }

    @Test
    void retrieveDashBordForUnknownStore() throws Exception {
        StoreOwner owner = storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/" + owner.getId() + "/dashboard/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(storeName)).andExpect(status().isNotFound());

    }

    @Test
    void retrieveNotOwnedStoreDashBord() throws Exception {
        StoreOwner owner = storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        StoreOwner secondOwner = storeOwnerRegistration.registerStoreOwner("b", "b@b", "pwd");
        String storeName = "store";
        Map<String, String> storeSchedule = new HashMap<>();
        storeModifier.register(storeName, storeSchedule, secondOwner);
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/" + owner.getId() + "/dashboard/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(storeName)).andExpect(status().isUnauthorized());
    }

    @Test
    void retrieveDashBordWithEmptyBody() throws Exception {
        StoreOwner owner = storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/" + owner.getId() + "/dashboard/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteStoreOwner() throws Exception {
        StoreOwner owner = storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        mockMvc.perform(delete(StoreOwnerController.BASE_URI + "/" + owner.getId() + "/deleteStoreOwner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void deleteStoreOwnerWithWrongId() throws Exception {
        mockMvc.perform(delete(StoreOwnerController.BASE_URI + "/" + -1L + "/deleteStoreOwner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStoreOwnerWithEmptyId() throws Exception {
        mockMvc.perform(delete(StoreOwnerController.BASE_URI + "/" + "/deleteStoreOwner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
