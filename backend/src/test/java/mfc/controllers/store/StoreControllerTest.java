package mfc.controllers.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import mfc.controllers.StoreController;
import mfc.controllers.dto.StoreDTO;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StoreControllerTest {

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
    void registerStore() throws Exception {
        StoreOwner storeOwner = new StoreOwner("a", "a@a", "pwd");
        Map<String, String> storeSchedule = new HashMap<>();
        StoreDTO storeDTO = new StoreDTO(null, "a", storeSchedule, null);
        Store store = mock(Store.class);
        when(store.getId()).thenReturn(1L);
        when(store.getName()).thenReturn("a");
        when(store.getSchedule()).thenReturn(storeSchedule);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        when(store.getLastUpdate()).thenReturn(date);
        when(storeOwnerFinder.findStoreOwnerById(1L)).thenReturn(Optional.of(storeOwner));
        when(storeModifier.register(storeDTO.getName(), storeDTO.getSchedule(), storeOwner)).thenReturn(store);
        mockMvc.perform(post(StoreController.BASE_URI + "/" + 1L + "/register").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(storeDTO)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id")
                        .value("1")).andExpect(jsonPath("$.name")
                        .value("a")).andExpect(jsonPath("$.schedule")
                        .value(storeSchedule)).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteStore() throws Exception {
        Long ownerId = 5L;
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        Store store = new Store(storeName, owner);
        when(storeOwnerFinder.findStoreOwnerById(ownerId)).thenReturn(Optional.of(owner));
        when(storeOwnerRegistration.delete(owner)).thenReturn(owner);
        when(storeFinder.findStoreByName(storeName)).thenReturn(Optional.of(store));
        when(storeModifier.delete(store)).thenReturn(store);

        mockMvc.perform(delete(StoreController.BASE_URI + "/" + ownerId + "/deleteStore/" + storeName)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void deleteStoreWithUnknownId() throws Exception {
        Long ownerId = 5L;
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        Store store = new Store(storeName, owner);
        when(storeOwnerFinder.findStoreOwnerById(ownerId)).thenReturn(Optional.of(owner));
        when(storeOwnerRegistration.delete(owner)).thenReturn(owner);
        when(storeFinder.findStoreByName(storeName)).thenReturn(Optional.of(store));
        when(storeModifier.delete(store)).thenReturn(store);


        mockMvc.perform(delete(StoreController.BASE_URI + "/" + -1L + "/deleteStore/" + storeName).
                contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

    }

    @Test
    void deleteStoreWithUnknownStoreName() throws Exception {
        Long ownerId = 5L;
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        Store store = new Store(storeName, owner);
        when(storeOwnerFinder.findStoreOwnerById(ownerId)).thenReturn(Optional.of(owner));
        when(storeOwnerRegistration.delete(owner)).thenReturn(owner);
        when(storeFinder.findStoreByName(storeName)).thenReturn(Optional.of(store));
        when(storeModifier.delete(store)).thenReturn(store);
        mockMvc.perform(delete(StoreController.BASE_URI + "/" + ownerId + "/deleteStore/" + "store1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void deleteNotOwnedStore() throws Exception {
        Long ownerId = 5L;
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        String storeName = "store";
        StoreOwner secondOwner = new StoreOwner("b", "b@b", "pwd");
        Store store = new Store(storeName, secondOwner);
        when(storeOwnerFinder.findStoreOwnerById(ownerId)).thenReturn(Optional.of(owner));
        when(storeOwnerRegistration.delete(owner)).thenReturn(owner);
        when(storeFinder.findStoreByName(storeName)).thenReturn(Optional.of(store));
        when(storeModifier.delete(store)).thenReturn(store);

        mockMvc.perform(delete(StoreController.BASE_URI + "/" + ownerId + "/deleteStore/" + storeName).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }
}

