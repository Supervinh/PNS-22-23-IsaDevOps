package cli.model;

import java.util.UUID;

public class CliAdmin extends CliAccount{


    public CliAdmin(String name, String mail, String password) {
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
