package com.unico.openmarket.district;

public class DistrictAdapter {

    public static DistrictDto adaptDistrictDto(District district) {

        return DistrictDto.builder()
                .code(district.getCode())
                .name(district.getName())
                .build();
    }

    public static District adaptDistrict(DistrictDto district) {

        return District.builder()
                .code(district.getCode())
                .name(district.getName())
                .build();
    }
}
