package mfc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import mfc.POJO.Admin;
import mfc.controllers.dto.AdminDTO;
import mfc.repositories.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdminControllerIT {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private MockMvc mockMvc;

    Admin defaultAdmin;

    @BeforeEach
    void setUp() {
        defaultAdmin = new Admin("admin", "admin", "admin");
        adminRepository.save(defaultAdmin, defaultAdmin.getId());
    }

    @Test
    void registerAdminNoId() throws Exception {
        mockMvc.perform(post(AdminController.BASE_URI + "/registerAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(null, "admin", "aaz", "admin")))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("admin"))
                .andExpect(jsonPath("$.password").value("admin"))
                .andExpect(jsonPath("$.mail").value("aaz"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    //    @Test
    void registerAdminNoMail() throws Exception {
        mockMvc.perform(post(AdminController.BASE_URI + "/registerAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(UUID.randomUUID(), "admin", "", "admin")))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    //TODO registerAdminWeakPassword

    @Test
    void registerAdminExists() throws Exception {
        mockMvc.perform(post(AdminController.BASE_URI + "/registerAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(null, "admin", "admin", "admin")))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void loginAdmin() throws Exception {
        mockMvc.perform(post(AdminController.BASE_URI + "/loginAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(UUID.randomUUID(), "admin", "admin", "admin")))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("admin"))
                .andExpect(jsonPath("$.password").value("admin"))
                .andExpect(jsonPath("$.mail").value("admin"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginAdminNoUsername() throws Exception {
        mockMvc.perform(post(AdminController.BASE_URI + "/loginAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(UUID.randomUUID(), "", "admin", "admin")))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("admin"))
                .andExpect(jsonPath("$.password").value("admin"))
                .andExpect(jsonPath("$.mail").value("admin"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void wrongPasswordLogin() throws Exception {
        mockMvc.perform(post(AdminController.BASE_URI + "/loginAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(UUID.randomUUID(), "admin", "admin", "abc")))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void notFoundLogin() throws Exception {
        mockMvc.perform(post(AdminController.BASE_URI + "/loginAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(UUID.randomUUID(), "admin", "none", "admin")))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}