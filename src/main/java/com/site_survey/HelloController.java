package com.site_survey;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class HelloController {

    private final S3FileService s3FileService;
    private final BulkImportService bulkImportService;
    private final PropertyRepository propertyRepository;

    public HelloController(S3FileService s3FileService, BulkImportService bulkImportService, PropertyRepository propertyRepository) {
        this.s3FileService = s3FileService;
        this.bulkImportService = bulkImportService;
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/user")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/user")
    public String submitUser(User user, Model model) {
        model.addAttribute("user", user);
        return "user-result";
    }

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload-form";
    }

    @PostMapping("/upload-plan")
    public String uploadPlan(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        String url = s3FileService.uploadFile(file);
        model.addAttribute("url", url);
        return "upload-result";
    }

    @PostMapping("/import-spaces")
    public String importSpaces(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        List<Space> spaces;
        String filename = file.getOriginalFilename();
        if (filename != null && filename.endsWith(".csv")) {
            spaces = bulkImportService.parseCsv(file);
        } else if (filename != null && filename.endsWith(".xlsx")) {
            spaces = bulkImportService.parseXlsx(file);
        } else {
            model.addAttribute("error", "Unsupported file type");
            return "upload-form";
        }
        // For demo, just add to model, in real, save and show preview
        model.addAttribute("spaces", spaces);
        return "import-preview";
    }

    @GetMapping("/hierarchy")
    public String showHierarchy(Model model) {
        List<Property> properties = propertyRepository.findAll();
        model.addAttribute("properties", properties);
        return "hierarchy";
    }
}