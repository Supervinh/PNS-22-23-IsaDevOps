package mfc.controllers.dto;

import java.util.UUID;

public class StoreOwnerDTO {
    private String name;
    private UUID id;
    private String mail;
    private String password;


    public StoreOwnerDTO(UUID id, String name, String mail, String password) {
        this.name = name;
        this.id = id;
        this.mail = mail;
        this.password = password;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
