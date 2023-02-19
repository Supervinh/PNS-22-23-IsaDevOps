package mfc.controllers.dto;

import mfc.POJO.StoreOwner;

import java.util.UUID;

public class StoreDTO {
    private UUID id;
    private String name;

//    private Map<LocalTime, LocalTime> openingHours;

    private StoreOwnerDTO owner;

    public StoreDTO(UUID id, String name,/*, Map<LocalTime, LocalTime> openingHours,*/ StoreOwnerDTO owner) {
        this.id = id;
        this.name = name;
//        this.openingHours = openingHours;
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

//    public Map<LocalTime, LocalTime> getOpeningHours() {
//        return openingHours;
//    }

//    public void setOpeningHours(Map<LocalTime, LocalTime> openingHours) {
//        this.openingHours = openingHours;
//    }

    public StoreOwner getOwner() {
        return new StoreOwner(owner.getName(), owner.getPassword(), owner.getPassword());
    }

    public void setOwner(StoreOwnerDTO owner) {
        this.owner = owner;
    }

}
