package cli.model;

import java.util.UUID;

public class CliStore {
    private UUID id;
    private String name;
//    private Pair<LocalTime,LocalTime>[] openingHours;

    private CliStoreOwner owner;

    public CliStore(UUID id, String name, CliStoreOwner owner) {
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

    public CliStoreOwner getOwner() {
        return owner;
    }

    public void setOwner(CliStoreOwner owner) {
        this.owner = owner;
    }

}
