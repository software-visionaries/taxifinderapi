package app.taxifinderapi.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.taxifinderapi.model.Town;
import app.taxifinderapi.service.TownService;

@RestController
public class TownController {
    
    @Autowired
    TownService townService;

    @PostMapping("/add/Town")
    public Town save(@RequestBody Town town) {
    return townService.saveTown(town);
    }

    @GetMapping("/get/Town/{id}")
    public Optional<Town> findTown(@PathVariable Long id){
        return townService.findByTown_id(id);
    }
}
