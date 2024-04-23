package app.taxifinderapi.service;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import app.taxifinderapi.model.Area;
import app.taxifinderapi.repository.AreaRepository;

@Service
public class AreaService {
  
    @Autowired
    AreaRepository areaRepository;

    public Area saveArea(Area Area){
        Area currArea = new Area(Area.getName());
        return areaRepository.save(currArea);
    }
    
    public Optional<Area> findArea( @PathVariable Long id){
        return areaRepository.findById(id);
    }

    public List<Area> getAreas(){
        return areaRepository.findAll();
    }
}
