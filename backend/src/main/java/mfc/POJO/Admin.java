package mfc.POJO;

import javax.persistence.Entity;

@Entity
public class Admin extends Account {
    public Admin(){}
    public Admin(String name, String mail, String password) {
        super(name, mail, password);
    }
}
