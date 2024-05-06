package app.taxifinderapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.taxifinderapi.dto.AddressDTO;
import app.taxifinderapi.service.AddressService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/sign-up")


public class AddressController {

    @Autowired
    AddressService addressService;


    @PostMapping("/add/Address/{user_id}")
    public ResponseEntity<String> addAddress(@PathVariable Long user_id,@RequestBody AddressDTO address) {
        return addressService.postAddress(user_id, address);
    }

    @GetMapping("/get/Address/{id}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Long id){
        AddressDTO address = addressService.getAddress(id);
        return ResponseEntity.ok().body(address); 
    }
}
