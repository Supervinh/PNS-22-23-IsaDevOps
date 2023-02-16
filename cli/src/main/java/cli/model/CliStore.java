package cli.model;

import java.util.UUID;

public class CliStore {
    private UUID id;
    private String name;

    public CliStore(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CliStore{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
