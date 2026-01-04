package com.site_survey;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/floors")
public class FloorController {

    private final FloorRepository floorRepository;

    public FloorController(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    @GetMapping
    public List<Floor> getAllFloors() {
        return floorRepository.findAll();
    }

    @PostMapping
    public Floor createFloor(@RequestBody Floor floor) {
        return floorRepository.save(floor);
    }
}