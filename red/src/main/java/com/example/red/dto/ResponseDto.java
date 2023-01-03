package com.example.red.dto;

import lombok.Data;

import java.awt.*;

@Data
public class ResponseDto {
    private String name;
    private Double lat;
    private Double lng;
    private Long userId;
}
