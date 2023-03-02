package cli.model;

import java.util.UUID;

public class CliStoreOwner extends CliAccount{

    public CliStoreOwner(String name, String mail, String password) {
        super(name, mail, password);
    }

    public CliStoreOwner(String mail, String password) {
        super("default", mail, password);
    }

    @Override
    public String toString() {
        return "CliStoreOwner{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", mail='" + super.getMail() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                '}';
    }
}
