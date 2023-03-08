package mfc.POJO;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Admin extends Account {
    public Admin(){}
    public Admin(String name, String mail, String password) {
        super(name, mail, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin admin)) return false;
        return Objects.equals(getName(), admin.getName()) &&
                Objects.equals(getMail(), admin.getMail()) &&
                Objects.equals(getPassword(), admin.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getMail(), getPassword());
    }
}
