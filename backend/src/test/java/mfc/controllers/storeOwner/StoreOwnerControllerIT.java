package mfc.controllers.storeOwner;

import mfc.controllers.StoreOwnerController;
import mfc.controllers.dto.StoreOwnerDTO;
import mfc.entities.StoreOwner;
import mfc.repositories.StoreOwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StoreOwnerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    StoreOwnerRepository storeOwnerRepository;

    @BeforeEach
    void setUp() {
        storeOwnerRepository.deleteAll();
    }

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
        storeOwnerRepository.save(owner);
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
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        storeOwnerRepository.save(owner);
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", "", "pwd"))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginStoreOwnerWithEmptyPassword() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        storeOwnerRepository.save(owner);
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", "a@a", ""))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginStoreOwnerWithEmptyMailAndPassword() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        storeOwnerRepository.save(owner);
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
        storeOwnerRepository.save(owner);
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "a", "b@a", "pwd"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void loginStoreOwnerWithWrongPassword() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        storeOwnerRepository.save(owner);
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "a", "a@a", "pwd1"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginStoreOwnerWithEmptyBody() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        storeOwnerRepository.save(owner);
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
        storeOwnerRepository.save(owner);
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
        storeOwnerRepository.save(owner);
        mockMvc.perform(post(StoreOwnerController.BASE_URI + "/loginOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new StoreOwnerDTO(null, "", "", ""))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteStoreOwner() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        owner = storeOwnerRepository.save(owner);
        mockMvc.perform(delete(StoreOwnerController.BASE_URI + "/" + owner.getId() + "/deleteStoreOwner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteStoreOwnerWithWrongId() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        storeOwnerRepository.save(owner);
        mockMvc.perform(delete(StoreOwnerController.BASE_URI + "/" + -1L+ "/deleteStoreOwner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStoreOwnerWithEmptyId() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        storeOwnerRepository.save(owner);
        mockMvc.perform(delete(StoreOwnerController.BASE_URI + "/" + "" + "/deleteStoreOwner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStore() throws Exception {
        StoreOwner owner = new StoreOwner("a", "a@a", "pwd");
        owner = storeOwnerRepository.save(owner);

        mockMvc.perform(delete(StoreOwnerController.BASE_URI + "/" + owner.getId() + "/deleteStoreOwner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }
}
