package com.site_survey;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String planUrl; // URL to the floor plan

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL)
    private List<Space> spaces;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPlanUrl() { return planUrl; }
    public void setPlanUrl(String planUrl) { this.planUrl = planUrl; }
    public Building getBuilding() { return building; }
    public void setBuilding(Building building) { this.building = building; }
    public List<Space> getSpaces() { return spaces; }
    public void setSpaces(List<Space> spaces) { this.spaces = spaces; }
}