package mfc.controllers.storeOwner;

import com.fasterxml.jackson.databind.ObjectMapper;
import mfc.controllers.StoreOwnerController;
import mfc.controllers.dto.DashboardDTO;
import mfc.controllers.dto.StoreOwnerDTO;
import mfc.entities.Store;
import mfc.entities.StoreOwner;
import mfc.interfaces.StoreDataGathering;
import mfc.interfaces.explorer.StoreFinder;
import mfc.interfaces.explorer.StoreOwnerFinder;
import mfc.interfaces.modifier.StoreModifier;
import mfc.interfaces.modifier.StoreOwnerRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StoreOwnerControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    StoreOwnerFinder storeOwnerFinder;
    @MockBean
    StoreOwnerRegistration storeOwnerRegistration;
    @MockBean
    StoreModifier storeModifier;
    @MockBean
    StoreFinder storeFinder;
    @MockBean
    StoreDataGathering storeDataGathering;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerStoreOwner() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        when(storeOwnerRegistration.registerStoreOwner(owner.getName(), owner.getMail(), owner.getPassword())).thenReturn(owner);
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
        when(storeOwnerRegistration.registerStoreOwner(owner.getName(), owner.getMail(), owner.getPassword())).thenReturn(owner);

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
        when(storeOwnerRegistration.registerStoreOwner(owner.getName(), owner.getMail(), owner.getPassword())).thenReturn(owner);
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
        when(storeOwnerRegistration.registerStoreOwner(owner.getName(), owner.getMail(), owner.getPassword())).thenReturn(owner);

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
        when(storeOwnerRegistration.registerStoreOwner(owner.getName(), owner.getMail(), owner.getPassword())).thenReturn(owner);
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
        when(storeOwnerRegistration.registerStoreOwner(owner.getName(), owner.getMail(), owner.getPassword())).thenReturn(owner);
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
        when(storeOwnerRegistration.registerStoreOwner(owner.getName(), owner.getMail(), owner.getPassword())).thenReturn(owner);
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
        when(storeOwnerRegistration.registerStoreOwner(owner.getName(), owner.getMail(), owner.getPassword())).thenReturn(owner);
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
        when(storeOwnerRegistration.registerStoreOwner(owner.getName(), owner.getMail(), owner.getPassword())).thenReturn(owner);
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
        when(storeOwnerFinder.findStoreOwnerByMail(owner.getMail())).thenReturn(Optional.of(owner));
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
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        when(storeOwnerFinder.findStoreOwnerByMail(owner.getMail())).thenReturn(Optional.of(owner));
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "a", "b@a", "pwd"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void loginStoreOwnerWithWrongPassword() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        when(storeOwnerFinder.findStoreOwnerByMail(owner.getMail())).thenReturn(Optional.of(owner));
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "a", "a@a", "pwd1"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginStoreOwnerWithEmptyBody() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        when(storeOwnerFinder.findStoreOwnerByMail(owner.getMail())).thenReturn(Optional.of(owner));
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginStoreOwnerWithEmptyBodyAndNull() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        when(storeOwnerFinder.findStoreOwnerByMail(owner.getMail())).thenReturn(Optional.of(owner));
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, null, null, null))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginStoreOwnerWithEmptyBodyAndEmpty() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        when(storeOwnerFinder.findStoreOwnerByMail(owner.getMail())).thenReturn(Optional.of(owner));
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", "", ""))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void retrieveDashboard() throws Exception {
        Long ownerId = 5L;
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        Store store = new Store(storeName, owner);
        DashboardDTO dashboardDTO = new DashboardDTO();
        dashboardDTO.setSalesVolumes(0);
        dashboardDTO.setNumberOfSales(0);
        dashboardDTO.setNumberOfCustomers(0);
        dashboardDTO.setNumberOfGivenPayoffs(0);
        dashboardDTO.setPayoffCumulatedCost(0);
        when(storeOwnerFinder.findStoreOwnerById(ownerId)).thenReturn(Optional.of(owner));
        when(storeFinder.findStoreByName(storeName)).thenReturn(Optional.of(store));
        when(storeDataGathering.gather(store)).thenReturn(dashboardDTO);
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/" + ownerId + "/dashboard/")
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
        Long ownerId = 5L;
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        Store store = new Store(storeName, owner);
        DashboardDTO dashboardDTO = new DashboardDTO();
        dashboardDTO.setSalesVolumes(0);
        dashboardDTO.setNumberOfSales(0);
        dashboardDTO.setNumberOfCustomers(0);
        dashboardDTO.setNumberOfGivenPayoffs(0);
        dashboardDTO.setPayoffCumulatedCost(0);
        when(storeOwnerFinder.findStoreOwnerById(ownerId)).thenReturn(Optional.of(owner));
        when(storeFinder.findStoreByName(storeName)).thenReturn(Optional.of(store));
        when(storeDataGathering.gather(store)).thenReturn(dashboardDTO);
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/" + -1L + "/dashboard/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(storeName)).andExpect(status().isNotFound());
    }

    @Test
    void retrieveDashBordForUnknownStore() throws Exception {
        Long ownerId = 5L;
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        when(storeOwnerFinder.findStoreOwnerById(ownerId)).thenReturn(Optional.of(owner));
        when(storeFinder.findStoreByName(storeName)).thenReturn(Optional.empty());

        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/" + ownerId + "/dashboard/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(storeName)).andExpect(status().isNotFound());

    }

    @Test
    void retrieveNotOwnedStoreDashBord() throws Exception {
        Long ownerId = 5L;
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        StoreOwner secondOwner = new StoreOwner("b", "b@b", "pwd");
        String storeName = "store";
        Store store = new Store(storeName, secondOwner);
        when(storeOwnerFinder.findStoreOwnerById(ownerId)).thenReturn(Optional.of(owner));
        when(storeFinder.findStoreByName(storeName)).thenReturn(Optional.of(store));
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/" + ownerId + "/dashboard/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(storeName)).andExpect(status().isUnauthorized());
    }

    @Test
    void retrieveDashBordWithEmptyBody() throws Exception {
        Long ownerId = 5L;
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        Store store = new Store(storeName, owner);
        DashboardDTO dashboardDTO = new DashboardDTO();
        dashboardDTO.setSalesVolumes(0);
        dashboardDTO.setNumberOfSales(0);
        dashboardDTO.setNumberOfCustomers(0);
        dashboardDTO.setNumberOfGivenPayoffs(0);
        dashboardDTO.setPayoffCumulatedCost(0);
        when(storeOwnerFinder.findStoreOwnerById(ownerId)).thenReturn(Optional.of(owner));
        when(storeFinder.findStoreByName(storeName)).thenReturn(Optional.of(store));
        when(storeDataGathering.gather(store)).thenReturn(dashboardDTO);
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/" + ownerId + "/dashboard/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteStoreOwner() throws Exception {
        Long ownerId = 5L;
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        when(storeOwnerFinder.findStoreOwnerById(ownerId)).thenReturn(Optional.of(owner));
        when(storeOwnerRegistration.delete(owner)).thenReturn(owner);
        mockMvc.perform(delete(StoreOwnerController.BASE_URI + "/" + ownerId + "/deleteStoreOwner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteStoreOwnerWithWrongId() throws Exception {
        Long ownerId = 5L;
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        when(storeOwnerFinder.findStoreOwnerById(ownerId)).thenReturn(Optional.of(owner));
        when(storeOwnerRegistration.delete(owner)).thenReturn(owner);
        mockMvc.perform(delete(StoreOwnerController.BASE_URI + "/" + -1L + "/deleteStoreOwner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStoreOwnerWithEmptyId() throws Exception {
        Long ownerId = 5L;
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        when(storeOwnerFinder.findStoreOwnerById(ownerId)).thenReturn(Optional.of(owner));
        when(storeOwnerRegistration.delete(owner)).thenReturn(owner);
        mockMvc.perform(delete(StoreOwnerController.BASE_URI + "/" + "/deleteStoreOwner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
