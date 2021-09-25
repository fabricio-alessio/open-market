package com.unico.openmarket.util;

import com.unico.openmarket.district.District;
import com.unico.openmarket.district.DistrictDto;
import com.unico.openmarket.market.Market;
import com.unico.openmarket.market.MarketDto;
import com.unico.openmarket.subcityhall.SubCityHall;
import com.unico.openmarket.subcityhall.SubCityHallDto;

public class EntityHelper {

    public static final long AREA_P = 83748374l;
    public static final long LAT = -4598239839l;
    public static final long LNG = -2398694869l;
    public static final long SETCENS = 352343l;
    public static final String NEIGHBORHOOD = "Neighborhood 1";
    public static final String STREET = "Street";
    public static final String NUMBER = "S/N";
    public static final String REFERENCE = "Reference 1";
    public static final String REGION5 = "Region 5";
    public static final String REGION8 = "Region 8";
    public static final String REGISTRY = "Registry";
    private static final int NEW_DISTRICT_ID = 1;
    private static final int NEW_SUB_CITY_HALL_ID = 2;

    public static DistrictDto createNewDistrictDto(int code) {

        return DistrictDto.builder()
                .code(code)
                .name("District " + code)
                .build();
    }

    public static SubCityHallDto createNewSubCityHallDto(int code) {

        return SubCityHallDto.builder()
                .code(code)
                .name("SubCityHall " + code)
                .build();
    }

    public static MarketDto createNewMarketDto(long code, DistrictDto districtDto, SubCityHallDto subCityHallDto) {

        return MarketDto.builder()
                .code(code)
                .areap(AREA_P)
                .lat(LAT)
                .lng(LNG)
                .name("Market " + code)
                .neighborhood(NEIGHBORHOOD)
                .street(STREET)
                .number(NUMBER)
                .reference(REFERENCE)
                .region5(REGION5)
                .region8(REGION8)
                .registry(REGISTRY)
                .setcens(SETCENS)
                .district(districtDto)
                .subCityHall(subCityHallDto)
                .build();
    }

    public static Market createNewMarket(long code) {

        return Market.builder()
                .code(code)
                .areap(AREA_P)
                .lat(LAT)
                .lng(LNG)
                .name("Market " + code)
                .neighborhood(NEIGHBORHOOD)
                .street(STREET)
                .number(NUMBER)
                .reference(REFERENCE)
                .region5(REGION5)
                .region8(REGION8)
                .registry(REGISTRY)
                .setcens(SETCENS)
                .districtId(NEW_DISTRICT_ID)
                .subCityHallId(NEW_SUB_CITY_HALL_ID)
                .build();
    }

    public static District createNewDistrict(int code) {

        return District.builder()
                .id(NEW_DISTRICT_ID)
                .code(code)
                .name("District " + code)
                .build();
    }

    public static SubCityHall createNewSubCityHall(int code) {

        return SubCityHall.builder()
                .id(NEW_SUB_CITY_HALL_ID)
                .code(code)
                .name("SubCityHall " + code)
                .build();
    }
}
