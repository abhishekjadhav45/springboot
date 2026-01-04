package com.site_survey;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Floor> floors;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }
    public List<Floor> getFloors() { return floors; }
    public void setFloors(List<Floor> floors) { this.floors = floors; }
}