package mfc.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class Store {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    /**
     * 0 --> opening
     * 1 --> closing
     * Days are defined by : Mo,Tu,We,Th,Fr,Sa,Su
     * Keys are Day + 1 or 0
     * Values are hours in string format
     * Example : Mo0 = 7h00
     * Mo1 = 19h00
     */
    @ElementCollection
    private Map<String, String> schedule;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StoreOwner owner;

    public Store(String name, StoreOwner owner) {
        this.name = name;
        HashMap<String, String> setup = new HashMap<>();
        setup.put("Mo0", "7h00");
        setup.put("Mo1", "19h00");
        this.schedule = setup;
        this.owner = owner;
    }

    public Store(String name, Map<String, String> schedule, StoreOwner owner) {
        this.name = name;
        this.schedule = schedule;
        this.owner = owner;
    }

    public Store() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.equals(id, store.id) && Objects.equals(name, store.name) && Objects.equals(owner, store.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner);
    }
}
