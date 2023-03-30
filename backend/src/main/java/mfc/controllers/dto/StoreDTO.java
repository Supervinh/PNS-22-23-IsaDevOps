package mfc.controllers.dto;

import java.util.List;
import java.util.Objects;

public class StoreDTO {

    private Long id;
    private String name;
    private List<String> schedule;

    public StoreDTO(Long id, String name, List<String> schedule) {
        this.name = name;
        this.schedule = schedule;
        this.id = id;
    }

    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreDTO store = (StoreDTO) o;
        return Objects.equals(id, store.id) && Objects.equals(name, store.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
