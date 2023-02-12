package mfc.controllers.dto;

import java.util.UUID;

public class StoreDTO {
    private UUID id;
    private String name;

    public StoreDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
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

}
