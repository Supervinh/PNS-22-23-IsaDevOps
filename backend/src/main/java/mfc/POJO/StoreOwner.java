package mfc.POJO;

import javax.persistence.Entity;

@Entity
public class StoreOwner extends Account {
    public StoreOwner(){}
    public StoreOwner(String name, String mail, String password) {
        super(name, mail, password);
    }


}
