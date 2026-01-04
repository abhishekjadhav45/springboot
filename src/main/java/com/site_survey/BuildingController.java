package com.site_survey;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buildings")
public class BuildingController {

    private final BuildingRepository buildingRepository;

    public BuildingController(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @GetMapping
    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    @PostMapping
    public Building createBuilding(@RequestBody Building building) {
        return buildingRepository.save(building);
    }
}