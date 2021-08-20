package ru.volod878.buying_auto_parts.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.REFRESH,
                    CascadeType.MERGE,
                    CascadeType.DETACH
            }
    )
    @JoinTable(
            name = "customer_auto_part",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "auto_part_id")
    )
    List<AutoPart> autoParts;

    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }

    public void addAutoPart(AutoPart autoPart) {
        if (autoPart == null) autoParts = new ArrayList<>();
        autoParts.add(autoPart);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AutoPart> getAutoParts() {
        return autoParts;
    }

    public void setAutoParts(List<AutoPart> autoParts) {
        this.autoParts = autoParts;
    }
}
