package mfc.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Store {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ElementCollection
    private List<String> schedule ;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StoreOwner owner;

    public Store(String name,StoreOwner owner) {
        this.name = name;
        String starting = "7h00";
        String ending = "19h00";
        this.schedule = new ArrayList<>(List.of(starting,ending,starting,ending,starting,ending,starting,ending,starting,ending,starting,ending));
        this.owner = owner;
    }

    public Store(String name, List<String> schedule,StoreOwner owner) {
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

    public List<String> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<String> schedule) {
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
