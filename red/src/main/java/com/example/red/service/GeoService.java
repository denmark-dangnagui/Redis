package com.example.red.service;

import com.example.red.dto.Location;
import com.example.red.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.ReactiveGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.domain.geo.GeoLocation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GeoService {
    public static final String VENUS_VISITED = "venues_visited";
    private final GeoOperations<String, String> geoOperations;

    public GeoService(GeoOperations<String, String> geoOperations) {
        this.geoOperations = geoOperations;
    }

    public void add(Location location) {
        Point point = new Point(location.getLng(), location.getLat());
        geoOperations.add(VENUS_VISITED, point, location.getName());
        //닉네임, 위경도, 유저아이디
    }

    public List<ResponseDto> nearByVenues(Double lng, Double lat) {
        List<ResponseDto> dto = new ArrayList<>();
        Circle circle = new Circle(new Point(lng, lat), new Distance(100, Metrics.KILOMETERS));

        // 백키로 반경에 있는 포인트들 뽑아오기
        GeoResults<RedisGeoCommands.GeoLocation<String>> res = geoOperations.radius(VENUS_VISITED, circle);
        System.out.println(res);

        assert res != null;
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> content = res.getContent();

        System.out.println(content);
        for (GeoResult<RedisGeoCommands.GeoLocation<String>> c : content) {
            System.out.println(c.getContent().getName());
            System.out.println(c.getDistance());
        }

//        for (GeoResult<RedisGeoCommands.GeoLocation<String>> g : content) {
//            ResponseDto responseDto = new ResponseDto();
//            List<Point> points = geoOperations.position(VENUS_VISITED, g.getContent().getName());
//
//            responseDto.setLat();
//            responseDto.setLng(g.getContent().getPoint().getY());
//            responseDto.setName(g.getContent().getName());
//            dto.add(responseDto);
//        }
        return dto;
    }

}
