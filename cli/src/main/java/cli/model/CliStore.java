package cli.model;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class CliStore {
    private UUID id;
    private String name;
    private String[][] schedule;
    private String owner;

    public CliStore(String name, String[][] schedule, String owner) {
        this.name = name;
        this.schedule = schedule;
        this.owner = owner;
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

    public String[][] getSchedule() {
        return schedule;
    }

    public void setSchedule(String[][] schedule) {
        this.schedule = schedule;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CliStore store = (CliStore) o;
        return Objects.equals(id, store.id) && Objects.equals(name, store.name) && Objects.equals(owner, store.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner);
    }

    @Override
    public String toString() {
        return "CliStore{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", schedule=" + Arrays.toString(schedule) +
                ", owner='" + owner + '\'' +
                '}';
    }
}
