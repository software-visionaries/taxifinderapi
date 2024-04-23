package app.taxifinderapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.taxifinderapi.model.Area;
import app.taxifinderapi.service.AreaService;

@RestController
public class AreaController {
   
    @Autowired
    AreaService areaService;


    @PostMapping("/add/Area")
    public Area addArea(@RequestBody Area Area){
        return areaService.saveArea(Area);
    }

    @GetMapping("/get/Area/{id}")
    public Optional<Area> findArea(@PathVariable Long id){
        return areaService.findArea(id);
    }

    @GetMapping("/getAll/Area")
    public List<Area> getAll(){
        return areaService.getAreas();
    }
}
