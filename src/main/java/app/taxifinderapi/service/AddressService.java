package app.taxifinderapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import app.taxifinderapi.dto.AddressDTO;
import app.taxifinderapi.exceptions.UserException;
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
    SectionRepository sectionRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    TownRepository townRepository;

    @Autowired
    AddressRepository addressRepository;


    public AddressDTO getAddress(Long id){
        Address address = addressRepository.findById(id).orElse(null);
        AddressDTO addressDTO = new AddressDTO(address.getTown().getName(), 
        address.getArea().getName(), 
        address.getSection().getName());
        return addressDTO;
    }

    @Transactional
    public ResponseEntity<String> postAddress(Long user_id, AddressDTO userAddress) {

        User user = userRepository.findById(user_id).orElseGet(() -> {
            throw new UserException("user with this ID does not exist");
        });

        Town town = new Town();
        town.setName(userAddress.getTown());
        townRepository.save(town);

        Area area = new Area();
        area.setName(userAddress.getArea());
        area.setTown(town);
        areaRepository.save(area);

        Section section = new Section();
        section.setName(userAddress.getSection());
        section.setArea(area);
        sectionRepository.save(section);

        Address address = new Address();
        address.setArea(area);
        address.setSection(section);
        address.setTown(town);

        if (user != null) {
            address.setUser(user);
            addressRepository.save(address);
            return ResponseEntity.ok().body("address was added successfully");
        }
        return ResponseEntity.badRequest().body("Address failed to add");
    }

}
