package com.unico.openmarket.market;

import com.unico.openmarket.district.DistrictDto;
import com.unico.openmarket.subcityhall.SubCityHallDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarketDto {
    private long code;
    private long lng;
    private long lat;
    private long setcens;
    private long areap;
    private String name;
    private String registry;
    private String street;
    private String number;
    private String neighborhood;
    private String reference;
    private DistrictDto district;
    private SubCityHallDto subCityHall;
    private String region5;
    private String region8;
}
