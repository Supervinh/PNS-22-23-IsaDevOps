package mfc.controllers.dto;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class StoreDTO {

    private final Long id;
    private String name;
    private Map<String, String> schedule;
    private LocalDateTime lastUpdate;

    public StoreDTO(Long id, String name, Map<String, String> schedule, LocalDateTime lastUpdate) {
        this.name = name;
        this.schedule = schedule;
        this.id = id;
        this.lastUpdate = lastUpdate;
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

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
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
