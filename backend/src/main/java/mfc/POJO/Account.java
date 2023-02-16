package mfc.POJO;

import java.util.UUID;

public abstract class Account {

    private UUID id;
    private String name;
    private String mail;
    private String password;

    public Account(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
