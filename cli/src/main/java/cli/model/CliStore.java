package cli.model;

import java.util.Map;
import java.util.Objects;

public class CliStore {
    private Long id;
    private String name;
    private Map<String, String> schedule;

    public CliStore(String name, Map<String, String> schedule) {
        this.name = name;
        this.schedule = schedule;
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

    public Map<String, String> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, String> schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "CliStore{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", schedule=" + schedule +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CliStore cliStore)) return false;
        return Objects.equals(id, cliStore.id) && Objects.equals(name, cliStore.name) && Objects.equals(schedule, cliStore.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, schedule);
    }
}
