package cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CliStoreOwner extends CliAccount{

    @JsonCreator
    public CliStoreOwner(@JsonProperty("name") String name, @JsonProperty("mail") String mail, @JsonProperty("password") String password) {
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
