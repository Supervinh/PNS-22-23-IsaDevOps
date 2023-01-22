package POJO;

import java.util.Map;
import java.util.UUID;

public class Store {

    private UUID id;
    private String name;

    private Map<String,String> openingHours;

    private StoreOwner owner;

    public Store() {
    }
}
