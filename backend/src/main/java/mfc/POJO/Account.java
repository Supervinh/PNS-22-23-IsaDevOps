package mfc.POJO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String mail;
    private String password;

    protected Account(){}

    protected Account(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(name, account.name) && Objects.equals(mail, account.mail) && Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mail, password);
    }
}
