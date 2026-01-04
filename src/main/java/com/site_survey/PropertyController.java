package com.site_survey;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyRepository propertyRepository;
    private final S3FileService s3FileService;
    private final BulkImportService bulkImportService;
    private final SpaceRepository spaceRepository;

    public PropertyController(PropertyRepository propertyRepository, S3FileService s3FileService, BulkImportService bulkImportService, SpaceRepository spaceRepository) {
        this.propertyRepository = propertyRepository;
        this.s3FileService = s3FileService;
        this.bulkImportService = bulkImportService;
        this.spaceRepository = spaceRepository;
    }

    @GetMapping
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @PostMapping
    public Property createProperty(@RequestBody Property property) {
        return propertyRepository.save(property);
    }

    @PostMapping("/upload-plan")
    public String uploadFloorPlan(@RequestParam("file") MultipartFile file) throws IOException {
        return s3FileService.uploadFile(file);
    }

    @PostMapping("/import-spaces")
    public List<Space> importSpaces(@RequestParam("file") MultipartFile file) throws Exception {
        List<Space> spaces;
        String filename = file.getOriginalFilename();
        if (filename != null && filename.endsWith(".csv")) {
            spaces = bulkImportService.parseCsv(file);
        } else if (filename != null && filename.endsWith(".xlsx")) {
            spaces = bulkImportService.parseXlsx(file);
        } else {
            throw new IllegalArgumentException("Unsupported file type");
        }
        return spaceRepository.saveAll(spaces);
    }
}