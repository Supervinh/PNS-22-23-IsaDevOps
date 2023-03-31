package mfc.controllers.dto;

import java.util.Map;
import java.util.Objects;

public class StoreDTO {

    private Long id;
    private String name;
    private Map<String, String> schedule;

    public StoreDTO(Long id, String name, Map<String, String> schedule) {
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

    public Map<String, String> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, String> schedule) {
        this.schedule = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreDTO storeDTO)) return false;
        return Objects.equals(id, storeDTO.id) && Objects.equals(name, storeDTO.name) && Objects.equals(schedule, storeDTO.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
