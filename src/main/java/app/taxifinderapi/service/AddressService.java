package app.taxifinderapi.service;

import java.util.Optional;

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

    @Transactional
    public ResponseEntity<?> addAddress(Long user_id, AddressRequest addressRequest) {

        User user = userRepository.findById(user_id).orElse(null);

        Town tTown = new Town();
        tTown.setName(addressRequest.getTown());
        townRepository.save(tTown);

        Area tArea = new Area();
        tArea.setName(addressRequest.getArea());
        tArea.setTown(tTown);
        areaRepository.save(tArea);

        Section tSection = new Section();
        tSection.setName(addressRequest.getSection());
        tSection.setArea(tArea);
        sectionRepository.save(tSection);

        Address tAddress = new Address(tTown, tArea, tSection);
        if (user != null) {
            tAddress.setUser(user);
            addressRepository.save(tAddress);
        }

        return ResponseEntity.ok().body("Address successfully added");
    }

}
