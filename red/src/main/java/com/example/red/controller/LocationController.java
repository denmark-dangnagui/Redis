package com.example.red.controller;

import com.example.red.dto.GeoDto;
import com.example.red.dto.Location;
import com.example.red.dto.ResponseDto;
import com.example.red.service.GeoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationController {
    private final GeoService geoService;

    public LocationController(GeoService geoService) {
        this.geoService = geoService;
    }

    @PostMapping("/location")
    public ResponseEntity<String> addLocation(@RequestBody Location location) {
        geoService.add(location);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/location/nearby")
    public ResponseEntity<List<ResponseDto>> locations(@RequestBody GeoDto geoDto) {
        List<ResponseDto> responseDtos = geoService.nearByVenues(geoDto.getLng(), geoDto.getLat());
        return ResponseEntity.ok(responseDtos);
    }
}
