package mfc.controllers.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import mfc.controllers.AdminController;
import mfc.controllers.dto.AdminDTO;
import mfc.entities.Admin;
import mfc.exceptions.AlreadyExistingAccountException;
import mfc.interfaces.explorer.AdminFinder;
import mfc.interfaces.modifier.AdminRegistration;
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
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AdminRegistration adminReg;
    @MockBean
    private AdminFinder adminFind;

    @Test
    void registerAdminNoId() throws Exception {
        Admin admin = mock(Admin.class);
        when(admin.getId()).thenReturn(null);
        when(admin.getName()).thenReturn("admin");
        when(admin.getMail()).thenReturn("admin@mail");
        when(admin.getPassword()).thenReturn("admin");
        when(adminReg.registerAdmin(admin.getName(), admin.getMail(), admin.getPassword())).thenReturn(admin);

        mockMvc.perform(post(AdminController.BASE_URI + "/registerAdmin").content(objectMapper.writeValueAsString(new AdminDTO(null, "admin", "admin@mail", "admin"))).contentType(APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("admin")).andExpect(jsonPath("$.password").value("admin")).andExpect(jsonPath("$.mail").value("admin@mail")).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerAdmin() throws Exception {
        Admin admin = mock(Admin.class);
        when(admin.getId()).thenReturn(1L);
        when(admin.getName()).thenReturn("admin");
        when(admin.getMail()).thenReturn("admin@mail");
        when(admin.getPassword()).thenReturn("admin");
        when(adminReg.registerAdmin(admin.getName(), admin.getMail(), admin.getPassword())).thenReturn(admin);
        mockMvc.perform(post(AdminController.BASE_URI + "/registerAdmin").content(objectMapper.writeValueAsString(new AdminDTO(null, "admin", "admin@mail", "admin"))).contentType(APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("admin")).andExpect(jsonPath("$.password").value("admin")).andExpect(jsonPath("$.mail").value("admin@mail")).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void registerAdminExists() throws Exception {
        Admin admin = mock(Admin.class);
        when(admin.getId()).thenReturn(1L);
        when(admin.getName()).thenReturn("admin");
        when(admin.getMail()).thenReturn("admin@mail");
        when(admin.getPassword()).thenReturn("admin");
        when(adminReg.registerAdmin("admin", "admin@mail", "admin")).thenThrow(AlreadyExistingAccountException.class);
        mockMvc.perform(post(AdminController.BASE_URI + "/registerAdmin").content(objectMapper.writeValueAsString(new AdminDTO(null, "admin", "admin@mail", "admin"))).contentType(APPLICATION_JSON)).andExpect(status().isConflict());
    }

    @Test
    void loginAdmin() throws Exception {
        Admin admin = mock(Admin.class);
        when(admin.getId()).thenReturn(1L);
        when(admin.getName()).thenReturn("admin");
        when(admin.getMail()).thenReturn("admin@mail");
        when(admin.getPassword()).thenReturn("admin");
        when(adminFind.findAdminByMail("admin@mail")).thenReturn(Optional.of(admin));
        mockMvc.perform(post(AdminController.BASE_URI + "/loginAdmin").content(objectMapper.writeValueAsString(new AdminDTO(0L, "admin", "admin@mail", "admin"))).contentType(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("admin")).andExpect(jsonPath("$.password").value("admin")).andExpect(jsonPath("$.mail").value("admin@mail")).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void wrongPasswordLogin() throws Exception {
        Admin admin = mock(Admin.class);
        when(admin.getId()).thenReturn(1L);
        when(admin.getName()).thenReturn("admin");
        when(admin.getMail()).thenReturn("admin@mail");
        when(admin.getPassword()).thenReturn("admin");
        when(adminFind.findAdminByMail("admin@mail")).thenReturn(Optional.of(admin));
        mockMvc.perform(post(AdminController.BASE_URI + "/loginAdmin").content(objectMapper.writeValueAsString(new AdminDTO(0L, "admin", "admin@mail", "wrong"))).contentType(APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }

    @Test
    void notFoundLogin() throws Exception {
        Admin admin = mock(Admin.class);
        when(admin.getId()).thenReturn(1L);
        when(admin.getName()).thenReturn("admin");
        when(admin.getMail()).thenReturn("admin@mail");
        when(admin.getPassword()).thenReturn("admin");
        when(adminFind.findAdminByMail("admin@mail")).thenReturn(Optional.of(admin));

        mockMvc.perform(post(AdminController.BASE_URI + "/loginAdmin")
                .content(objectMapper.writeValueAsString(new AdminDTO(0L, "admin", "an@mail", "admin")))
                .contentType(APPLICATION_JSON)).andExpect(status().isNotFound());
    }
}