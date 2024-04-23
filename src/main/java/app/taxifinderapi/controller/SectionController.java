package app.taxifinderapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import app.taxifinderapi.model.Section;
import app.taxifinderapi.repository.SectionRepository;
import app.taxifinderapi.service.SectionService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class SectionController {
    
    @Autowired
    private SectionService sectionService;

    @PostMapping("/add/Sec")
    public Section saveSection(@RequestBody Section section) {
    return sectionService.save(section);
    }

    @GetMapping("/get/Sec")
    public List<Section> getAllSections() {
        return sectionService.getAllSections();
    }
    
}
