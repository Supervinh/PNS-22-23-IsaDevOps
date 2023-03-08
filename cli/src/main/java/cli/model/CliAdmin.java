package cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CliAdmin extends CliAccount{


    @JsonCreator
    public CliAdmin(@JsonProperty("name") String name, @JsonProperty("mail") String mail, @JsonProperty("password") String password) {
        super(name, mail, password);
    }

    public CliAdmin(String mail, String password) {
        //We need to initialize the name to "default" because the backend will not accept a null or a blank value
        super("default", mail, password);
    }


    @Override
    public String toString() {
        return "CliAdmin{" +
                "name='" + super.getName() + '\'' +
                ", id=" + super.getId() +
                ", mail='" + super.getMail() + '\'' +
                ", password='" + super.getMail() + '\'' +
                '}';
    }
}
