package mfc.controllers.dto;

public class StoreOwnerDTO {
    private String name;
    private Long id;
    private String mail;
    private String password;

    public StoreOwnerDTO(Long id,String name, String mail, String password) {
        this.name = name;
        this.id = id;
        this.mail = mail;
        this.password = password;
    }

    public StoreOwnerDTO() {
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
}
