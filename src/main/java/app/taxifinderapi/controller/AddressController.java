package app.taxifinderapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.payload.request.AddressRequest;
import app.taxifinderapi.service.AddressService;

@RestController
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping("/add/address/{user_id}")
    public ResponseEntity<?> addAddress(@PathVariable Long user_id, @RequestBody AddressRequest addressRequest){
        return addressService.addAddress(user_id, addressRequest);
    }
}
