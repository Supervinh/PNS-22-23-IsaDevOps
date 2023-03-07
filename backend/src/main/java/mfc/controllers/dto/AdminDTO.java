package mfc.controllers.dto;

import java.util.UUID;

public class AdminDTO {
    private String name;
    private Long id;
    private String mail;
    private String password;

    private String authorizationMail;
    private String authorizationPassword;

    public AdminDTO(String name, Long id, String mail, String password, String authorizationMail, String authorizationPassword) {
        this.name = name;
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.authorizationMail = authorizationMail;
        this.authorizationPassword = authorizationPassword;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthorizationMail() {
        return authorizationMail;
    }

    public String getAuthorizationPassword() {
        return authorizationPassword;
    }

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
