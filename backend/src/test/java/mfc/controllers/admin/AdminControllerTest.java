package mfc.controllers.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import mfc.entities.Admin;
import mfc.controllers.AdminController;
import mfc.controllers.dto.AdminDTO;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.explorer.AdminFinder;
import mfc.interfaces.modifier.AdminRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@AutoConfigureWebClient
class AdminControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AdminRegistration adminReg;
    @MockBean
    private AdminFinder adminFind;

    @BeforeEach
    void setUp() throws AlreadyExistingAccountException {
        when(adminReg.registerAdmin(anyString(), anyString(), anyString())).thenReturn(new Admin("admin", "admin", "admin"));
        when(adminReg.registerAdmin(eq("exist"), anyString(), anyString())).thenThrow(AlreadyExistingAccountException.class);
        when(adminFind.findAdminByMail(anyString())).thenReturn(Optional.of(new Admin("admin", "admin", "admin")));
        when(adminFind.findAdminByMail("password")).thenReturn(Optional.of(new Admin("admin", "admin", "password")));
        when(adminFind.findAdminByMail("none")).thenReturn(Optional.empty());
    }

    @Test
    void registerAdminNoId() throws Exception {
        mockMvc.perform(post(AdminController.BASE_URI + "/registerAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(null, "admin", "admin", "admin")))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("admin"))
                .andExpect(jsonPath("$.password").value("admin"))
                .andExpect(jsonPath("$.mail").value("admin"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerAdmin() throws Exception {
        mockMvc.perform(post(AdminController.BASE_URI + "/registerAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(0L, "admin", "admin", "admin")))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("admin"))
                .andExpect(jsonPath("$.password").value("admin"))
                .andExpect(jsonPath("$.mail").value("admin"))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerAdminExists() throws Exception {
        mockMvc.perform(post(AdminController.BASE_URI + "/registerAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(0L, "exist", "admin", "admin")))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void loginAdmin() throws Exception {
        mockMvc.perform(post(AdminController.BASE_URI + "/loginAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(0L,"admin", "admin", "admin")))
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
                        .content(objectMapper.writeValueAsString(new AdminDTO(0L,"admin", "password", "admin")))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void notFoundLogin() throws Exception {
        mockMvc.perform(post(AdminController.BASE_URI + "/loginAdmin")
                        .content(objectMapper.writeValueAsString(new AdminDTO(0L, "admin", "none", "admin")))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}