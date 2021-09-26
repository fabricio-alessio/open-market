package com.unico.openmarket.util;

import com.unico.openmarket.district.District;
import com.unico.openmarket.district.DistrictDto;
import com.unico.openmarket.market.Market;
import com.unico.openmarket.market.MarketDto;
import com.unico.openmarket.subcityhall.SubCityHall;
import com.unico.openmarket.subcityhall.SubCityHallDto;

import java.util.Map;

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
    public static final int NEW_DISTRICT_ID = 1;
    public static final int NEW_SUB_CITY_HALL_ID = 2;

    public static final String FIELD_NAME = "NAME";
    public static final String FIELD_DISTRICT_ID = "DISTRICT_ID";
    public static final String FIELD_SUB_CITY_HALL_ID = "SUB_CITY_HALL_ID";
    public static final String FIELD_REGION5 = "REGION5";
    public static final String FIELD_NEIGHBORHOOD = "NEIGHBORHOOD";

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
                .marketName("Market " + code)
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

    public static Market createNewMarket(long code, Map<String, String> map) {


        return Market.builder()
                .code(code)
                .areap(AREA_P)
                .lat(LAT)
                .lng(LNG)
                .marketName(map.getOrDefault(FIELD_NAME, ""))
                .neighborhood(map.getOrDefault(FIELD_NEIGHBORHOOD, NEIGHBORHOOD))
                .street(STREET)
                .number(NUMBER)
                .reference(REFERENCE)
                .region5(map.getOrDefault(FIELD_REGION5, REGION5))
                .region8(REGION8)
                .registry(REGISTRY)
                .setcens(SETCENS)
                .districtId(Integer.parseInt(map.getOrDefault(FIELD_DISTRICT_ID, "" + NEW_DISTRICT_ID)))
                .subCityHallId(Integer.parseInt(map.getOrDefault(FIELD_SUB_CITY_HALL_ID, "" + NEW_SUB_CITY_HALL_ID)))
                .build();
    }

    public static District createDistrict(int code) {

        return District.builder()
                .id(NEW_DISTRICT_ID)
                .code(code)
                .name("District " + code)
                .build();
    }

    public static SubCityHall createSubCityHall(int code) {

        return SubCityHall.builder()
                .id(NEW_SUB_CITY_HALL_ID)
                .code(code)
                .name("SubCityHall " + code)
                .build();
    }

    public static District createNewDistrict(int code) {

        return District.builder()
                .code(code)
                .name("District " + code)
                .build();
    }

    public static SubCityHall createNewSubCityHall(int code) {

        return SubCityHall.builder()
                .code(code)
                .name("SubCityHall " + code)
                .build();
    }
}
