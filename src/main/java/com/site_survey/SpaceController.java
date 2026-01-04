package com.site_survey;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    private final SpaceRepository spaceRepository;

    public SpaceController(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }

    @GetMapping
    public List<Space> getAllSpaces() {
        return spaceRepository.findAll();
    }

    @PostMapping
    public Space createSpace(@RequestBody Space space) {
        return spaceRepository.save(space);
    }
}