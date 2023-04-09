package mfc.controllers.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import mfc.controllers.AdminController;
import mfc.controllers.dto.AdminDTO;
import mfc.interfaces.modifier.AdminRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class AdminControllerIT {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AdminRegistration adminRegistration;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerAdminNoId() throws Exception {
        mockMvc.perform(post(AdminController.BASE_URI + "/registerAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(null, "admin", "admin@mail", "admin")))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("admin"))
                .andExpect(jsonPath("$.password").value("admin"))
                .andExpect(jsonPath("$.mail").value("admin@mail"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerAdminNoMail() throws Exception {
        mockMvc.perform(post(AdminController.BASE_URI + "/registerAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(0L, "admin", "", "admin")))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void registerAdminExists() throws Exception {
        adminRegistration.registerAdmin("admin", "admin@mail", "admin");
        mockMvc.perform(post(AdminController.BASE_URI + "/registerAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(null, "admin", "admin@mail", "admin")))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void loginAdmin() throws Exception {
        adminRegistration.registerAdmin("admin", "admin@mail", "admin");
        mockMvc.perform(post(AdminController.BASE_URI + "/loginAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(null, "admin", "admin@mail", "admin")))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("admin"))
                .andExpect(jsonPath("$.password").value("admin"))
                .andExpect(jsonPath("$.mail").value("admin@mail"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginAdminNoUsername() throws Exception {
        adminRegistration.registerAdmin("admin", "admin@mail", "admin");
        mockMvc.perform(post(AdminController.BASE_URI + "/loginAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(null, "", "admin", "admin")))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void wrongPasswordLogin() throws Exception {
        adminRegistration.registerAdmin("admin", "admin@mail", "admin");
        mockMvc.perform(post(AdminController.BASE_URI + "/loginAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(null, "admin", "admin@mail", "abc")))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void notFoundLogin() throws Exception {
        adminRegistration.registerAdmin("admin", "admin@mail", "admin");
        mockMvc.perform(post(AdminController.BASE_URI + "/loginAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(null, "admin", "ad@mail", "admin")))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}