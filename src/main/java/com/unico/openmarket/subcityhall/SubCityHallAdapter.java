package com.unico.openmarket.subcityhall;

public class SubCityHallAdapter {

    public static SubCityHallDto adaptSubCityHallDto(SubCityHall subCityHall) {

        return SubCityHallDto.builder()
                .code(subCityHall.getCode())
                .name(subCityHall.getName())
                .build();
    }

    public static SubCityHall adaptSubCityHall(SubCityHallDto subCityHallDto) {

        return SubCityHall.builder()
                .code(subCityHallDto.getCode())
                .name(subCityHallDto.getName())
                .build();
    }
}
