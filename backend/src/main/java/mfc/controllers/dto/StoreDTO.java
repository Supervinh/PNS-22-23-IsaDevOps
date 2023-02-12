package mfc.controllers.dto;

import mfc.POJO.StoreOwner;

import java.util.Map;
import java.util.UUID;

public class StoreDTO {
    private UUID id;
    private String name;

    private Map<String,String> openingHours;

    private StoreOwner owner;

    public StoreDTO(UUID id, String name, Map<String, String> openingHours, StoreOwner owner) {
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

    public Map<String, String> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(Map<String, String> openingHours) {
        this.openingHours = openingHours;
    }

    public StoreOwner getOwner() {
        return owner;
    }

    public void setOwner(StoreOwner owner) {
        this.owner = owner;
    }

}
