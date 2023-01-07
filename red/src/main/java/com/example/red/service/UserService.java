package com.example.red.service;

import com.example.red.dto.Location;
import com.example.red.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {
    public static final String VENUS_VISITED = "venues_visited";
    private final GeoOperations<String, String> geoOperations;

    public UserService(GeoOperations<String, String> geoOperations) {
        this.geoOperations = geoOperations;
    }

    public void add(Location location) {
        Point point = new Point(location.getLng(), location.getLat());
        geoOperations.add(VENUS_VISITED, point, location.getName());
        //닉네임, 위경도, 유저아이디
    }

    public List<ResponseDto> nearByVenues(Location location) {
        List<ResponseDto> dto = new ArrayList<>();
        Circle circle = new Circle(new Point(location.getLng(), location.getLat()), new Distance(3, Metrics.KILOMETERS));
        // 백키로 반경에 있는 포인트들 뽑아오기
        GeoResults<RedisGeoCommands.GeoLocation<String>> res = geoOperations.radius(VENUS_VISITED, circle);
        System.out.println(res);
        assert res != null;
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> content = res.getContent();
//      x가 경도 y가 위도
        System.out.println(content);
        for (GeoResult<RedisGeoCommands.GeoLocation<String>> c : content) {
            ResponseDto responseDto = new ResponseDto();
            List<Point> position = geoOperations.position(VENUS_VISITED, c.getContent().getName());
            for (Point point : position) {
                responseDto.setLat(point.getX());
                responseDto.setLng(point.getY());
                responseDto.setName(c.getContent().getName());
                dto.add(responseDto);
            }
        }
        return dto;
    }

}
