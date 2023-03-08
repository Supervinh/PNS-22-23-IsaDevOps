package cli.model;

import java.util.UUID;

public class CliStoreOwner {
    private Long id; //empty at register, not when returned by the server
    private String name;
    private String mail;
    private String password;

    private String authorizationMail;
    private String authorizationPassword;

    public CliStoreOwner(String name, String mail, String password, String authorizationMail, String authorizationPassword) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.authorizationMail = authorizationMail;
        this.authorizationPassword = authorizationPassword;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "CliStoreOwner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", authorizationMail='" + authorizationMail + '\'' +
                ", authorizationPassword='" + authorizationPassword + '\'' +
                '}';
    }
}
