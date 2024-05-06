package app.taxifinderapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import app.taxifinderapi.model.Section;
import app.taxifinderapi.repository.SectionRepository;



@Service
public class SectionService {
    
    @Autowired
     SectionRepository sectionRepository;

    public Section save(Section Section){
        Section currSection = new Section(Section.getName(), Section.getId());
        return sectionRepository.save(currSection);
    }

    public List<Section> getAllSections() {
    return sectionRepository.findAll();    
    }


}
