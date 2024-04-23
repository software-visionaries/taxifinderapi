package app.taxifinderapi.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.taxifinderapi.model.Town;
import app.taxifinderapi.repository.TownRepository;

@Service
public class TownService {
    
    @Autowired
    TownRepository townRepository;

    public Town saveTown(Town Town){
        Town currTown = new Town(Town.getName());
        return townRepository.save(currTown);
    }

    @SuppressWarnings("null")
    public Optional<Town> findByTown_id(Long id) {
       return townRepository.findById(id);
    }

}
