package mfc.controllers.dto;

import java.util.UUID;

public class StoreOwnerDTO {
    private String name;
    private UUID id;
    private String mail;
    private String password;

    private String authorizationMail;
    private String authorizationPassword;

    public StoreOwnerDTO() {
    }

    public String getAuthorizationMail() {
        return authorizationMail;
    }

    public void setAuthorizationMail(String authorizationMail) {
        this.authorizationMail = authorizationMail;
    }

    public String getAuthorizationPassword() {
        return authorizationPassword;
    }

    public void setAuthorizationPassword(String authorizationPassword) {
        this.authorizationPassword = authorizationPassword;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getId() {
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
