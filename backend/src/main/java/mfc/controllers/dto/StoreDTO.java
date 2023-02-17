package mfc.controllers.dto;

import mfc.POJO.StoreOwner;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.UUID;

public class StoreDTO {
    private UUID id;
    private String name;

    private Map<LocalTime, LocalTime> openingHours;

    private StoreOwner owner;

    public StoreDTO(UUID id, String name, Map<LocalTime, LocalTime> openingHours, StoreOwner owner) {
        this.id = id;
        this.name = name;
        this.openingHours = openingHours;
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<LocalTime, LocalTime> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(Map<LocalTime, LocalTime> openingHours) {
        this.openingHours = openingHours;
    }

    public StoreOwner getOwner() {
        return owner;
    }

    public void setOwner(StoreOwner owner) {
        this.owner = owner;
    }

}
