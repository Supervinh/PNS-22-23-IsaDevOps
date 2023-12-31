package mfc.controllers.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AdminDTO {
    @NotBlank(message = "name should not be blank")
    private String name;
    private Long id;
    @Email(message = "mail should be a valid email")
    @NotBlank(message = "mail should not be blank")
    private String mail;
    @NotBlank(message = "password should not be blank")
    private String password;


    public AdminDTO(Long id, String name, String mail, String password) {
        this.name = name;
        this.id = id;
        this.mail = mail;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
