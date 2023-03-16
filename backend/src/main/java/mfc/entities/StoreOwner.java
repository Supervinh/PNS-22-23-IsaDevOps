package mfc.entities;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class StoreOwner extends Account {
    public StoreOwner(){}
    public StoreOwner(String name, String mail, String password) {
        super(name, mail, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreOwner storeOwner)) return false;
        return Objects.equals(getName(), storeOwner.getName()) &&
                Objects.equals(getMail(), storeOwner.getMail()) &&
                Objects.equals(getPassword(), storeOwner.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getMail(), getPassword());
    }
}
