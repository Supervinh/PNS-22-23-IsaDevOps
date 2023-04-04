package mfc.controllers.storeOwner;

import com.fasterxml.jackson.databind.ObjectMapper;
import mfc.controllers.StoreOwnerController;
import mfc.controllers.dto.StoreOwnerDTO;
import mfc.entities.StoreOwner;
import mfc.exceptions.CredentialsException;
import mfc.exceptions.StoreNotFoundException;
import mfc.exceptions.StoreOwnerNotFoundException;
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
import org.springframework.web.util.NestedServletException;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class StoreOwnerControllerIT {

    @Autowired
    private MockMvc mockMvc;

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


    @Test
    void registerStoreOwner() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
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
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");

        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", "a@a", "pwd"))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyPassword() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");

        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "a", "a@a", ""))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyNameAndMail() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");

        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", null, "pwd"))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyNameAndPassword() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", "a@a", ""))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyMailAndPassword() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "a", null, ""))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyNameAndMailAndPassword() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", null, ""))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyBody() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/registerOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerStoreOwnerWithEmptyBodyAndNull() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
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
        assertThrows(StoreOwnerNotFoundException.class, () -> {
            try {
                mockMvc.perform(post(StoreOwnerController.BASE_URI + "/" + -1L + "/dashboard/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storeName));
            } catch (NestedServletException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    void retrieveDashBordForUnknownStore() throws Exception {
        StoreOwner owner = storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        assertThrows(StoreNotFoundException.class, () -> {
            try {
                mockMvc.perform(post(StoreOwnerController.BASE_URI + "/" + owner.getId() + "/dashboard/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storeName));
            } catch (NestedServletException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    void retrieveNotOwnedStoreDashBord() throws Exception {
        StoreOwner owner = storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        StoreOwner secondOwner = storeOwnerRegistration.registerStoreOwner("b", "b@b", "pwd");
        String storeName = "store";
        Map<String, String> storeSchedule = new HashMap<>();
        storeModifier.register(storeName, storeSchedule, secondOwner);
        assertThrows(CredentialsException.class, () -> {
            try {
                mockMvc.perform(post(StoreOwnerController.BASE_URI + "/" + owner.getId() + "/dashboard/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storeName));
            } catch (NestedServletException e) {
                throw e.getCause();
            }
        });
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
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
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

    @Test
    void deleteStore() throws Exception {
        StoreOwner owner = storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        Map<String, String> storeSchedule = new HashMap<>();
        storeModifier.register(storeName, storeSchedule, owner);

        mockMvc.perform(delete(StoreOwnerController.BASE_URI + "/" + owner.getId() + "/deleteStore/" + storeName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStoreWithUnknownId() throws Exception {
        StoreOwner owner = storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        Map<String, String> storeSchedule = new HashMap<>();
        storeModifier.register(storeName, storeSchedule, owner);

        assertThrows(StoreOwnerNotFoundException.class, () -> {
            try {
                mockMvc.perform(delete(StoreOwnerController.BASE_URI + "/" + -1L + "/deleteStore/" + storeName)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            } catch (NestedServletException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    void deleteStoreWithUnknownStoreName() throws Exception {
        StoreOwner owner = storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        Map<String, String> storeSchedule = new HashMap<>();
        storeModifier.register(storeName, storeSchedule, owner);

        assertThrows(StoreNotFoundException.class, () -> {
            try {
                mockMvc.perform(delete(StoreOwnerController.BASE_URI + "/" + owner.getId() + "/deleteStore/" + "store1")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            } catch (NestedServletException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    void deleteNotOwnedStore() throws Exception {
        StoreOwner owner = storeOwnerRegistration.registerStoreOwner("a", "a@a", "pwd");
        StoreOwner secondOwner = storeOwnerRegistration.registerStoreOwner("b", "b@b", "pwd");
        String storeName = "store";
        Map<String, String> storeSchedule = new HashMap<>();
        storeModifier.register(storeName, storeSchedule, secondOwner);

        assertThrows(CredentialsException.class, () -> {
            try {
                mockMvc.perform(delete(StoreOwnerController.BASE_URI + "/" + owner.getId() + "/deleteStore/" + storeName)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            } catch (NestedServletException e) {
                throw e.getCause();
            }
        });
    }
}
