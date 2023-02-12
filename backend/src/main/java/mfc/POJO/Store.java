package mfc.POJO;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Store {

    private UUID id;
    private String name;

    private Map<String,String> openingHours;

    private StoreOwner owner;

    public Store(String name, Map<String, String> openingHours, StoreOwner owner) {
        this.name = name;
        this.openingHours = openingHours;
        this.owner = owner;
        this.id = UUID.randomUUID();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(id, store.id) && Objects.equals(name, store.name) && Objects.equals(openingHours, store.openingHours) && Objects.equals(owner, store.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, openingHours, owner);
    }
}
