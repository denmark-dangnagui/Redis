package com.example.red.controller;

import com.example.red.dto.Location;
import com.example.red.dto.ResponseDto;
import com.example.red.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {
    private final UserService userService;

    public LocationController(UserService geoService) {
        this.userService = geoService;
    }

    @PostMapping("/location")
    public ResponseEntity<String> addLocation(@RequestBody Location location) {
        userService.add(location);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/location/nearby")
    public ResponseEntity<List<ResponseDto>> locations(@RequestBody Location location) {
        List<ResponseDto> responseDtos = userService.nearByVenues(location);
        return ResponseEntity.ok(responseDtos);
    }
}
