package cli.model;

import java.util.List;
import java.util.Objects;

public class CliStore {
    private Long id;
    private String name;
    private List<String> schedule;
    private String owner;

    public CliStore(String name, List<String> schedule, String owner) {
        this.name = name;
        this.schedule = schedule;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<String> schedule) {
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
                ", schedule=" + schedule.toString() +
                ", owner='" + owner + '\'' +
                '}';
    }
}
