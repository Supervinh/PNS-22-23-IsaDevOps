package mfc.controllers.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class StoreDTO {

    private UUID id;
    private String name;
    private List<ScheduleDTO> schedule;

    private StoreOwnerDTO owner;

    public StoreDTO(String name, List<ScheduleDTO> schedule, StoreOwnerDTO owner, UUID id) {
        this.name = name;
        this.schedule = schedule;
        this.owner = owner;
        this.id = id;
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

    public List<ScheduleDTO> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleDTO> schedule) {
        this.schedule = schedule;
    }

    public StoreOwnerDTO getOwner() {
        return owner;
    }

    public void setOwner(StoreOwnerDTO owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreDTO store = (StoreDTO) o;
        return Objects.equals(id, store.id) && Objects.equals(name, store.name) && Objects.equals(owner, store.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner);
    }
}
