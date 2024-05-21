package app.taxifinderapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import app.payload.request.AddressRequest;
import app.taxifinderapi.model.Address;
import app.taxifinderapi.model.Area;
import app.taxifinderapi.model.Section;
import app.taxifinderapi.model.Town;
import app.taxifinderapi.model.User;
import app.taxifinderapi.repository.AddressRepository;
import app.taxifinderapi.repository.AreaRepository;
import app.taxifinderapi.repository.SectionRepository;
import app.taxifinderapi.repository.TownRepository;
import app.taxifinderapi.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    TownRepository townRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    QuestionService questionService;

    @Transactional
    public ResponseEntity<?> addAddress(Long user_id, AddressRequest addressRequest) {

        User user = userRepository.findById(user_id).orElse(null);

        String townName = questionService.word(addressRequest.getTown());
        String areaName = questionService.word(addressRequest.getArea());
        String sectionName = addressRequest.getSection();

        System.out.println(townName + " " + areaName + " " + sectionName);

        Town town = townRepository.findByName(townName);
        Area area = areaRepository.findByName(areaName);
        Section section = sectionRepository.findByName(sectionName);

        Address address = new Address();

        if (town == null) {
            Town nTown = new Town();
            nTown.setName(townName);
            townRepository.save(nTown);
            address.setTown(nTown);
        } else {
            address.setTown(town);
        }

        if (area == null) {
            Area nArea = new Area();
            nArea.setName(areaName);
            nArea.setTown(address.getTown()); // Set the town for the new area
            areaRepository.save(nArea);
            address.setArea(nArea);
        } else {
            address.setArea(area);
        }

        if (section == null) {
            Section nSection = new Section();
            nSection.setName(sectionName);
            nSection.setArea(address.getArea()); // Set the area for the new section
            sectionRepository.save(nSection);
            address.setSection(nSection);
        } else {
            address.setSection(section);
        }

        if (user != null) {
            address.setUser(user);
            addressRepository.save(address);
        }

        return ResponseEntity.ok().body("Address successfully added");
    }

}
