package com.unico.openmarket.scripts;

import com.unico.openmarket.district.DistrictDto;
import com.unico.openmarket.market.MarketDto;
import com.unico.openmarket.subcityhall.SubCityHallDto;

import java.util.Optional;

public class CsvLineOpenMarketAdapter {

    private static final String ID_FIELD = "ID";
    private static final int FIELD_NUMBER = 17;

    private static final int CODE_POS = 0;
    private static final int LNG_POS = 1;
    private static final int LAT_POS = 2;
    private static final int SETCENS_POS = 3;
    private static final int AREAP_POS = 4;
    private static final int DISTRICT_CODE_POS = 5;
    private static final int DISTRICT_NAME_POS = 6;
    private static final int SUB_CITY_HALL_CODE_POS = 7;
    private static final int SUB_CITY_HALL_NAME_POS = 8;
    private static final int REGION5_POS = 9;
    private static final int REGION8_POS = 10;
    private static final int MARKET_NAME_POS = 11;
    private static final int REGISTRY_POS = 12;
    private static final int STREET_POS = 13;
    private static final int NUMBER_POS = 14;
    private static final int NEIGHBORHOOD_POS = 15;
    private static final int REFERENCE_POS = 16;

    public static Optional<MarketDto> adaptFromLine(String line) {

        final String[] fields = splitCsvLine(line);
        if (ID_FIELD.equals(fields[CODE_POS])) {
            return Optional.empty();
        }

        final var district = DistrictDto.builder()
                .code(Integer.parseInt(fields[DISTRICT_CODE_POS]))
                .name(fields[DISTRICT_NAME_POS])
                .build();

        final var subCityHall = SubCityHallDto.builder()
                .code(Integer.parseInt(fields[SUB_CITY_HALL_CODE_POS]))
                .name(fields[SUB_CITY_HALL_NAME_POS])
                .build();

        final var marketDto = MarketDto.builder()
                .code(Long.parseLong(fields[CODE_POS]))
                .lng(Long.parseLong(fields[LNG_POS]))
                .lat(Long.parseLong(fields[LAT_POS]))
                .setcens(Long.parseLong(fields[SETCENS_POS]))
                .areap(Long.parseLong(fields[AREAP_POS]))
                .district(district)
                .subCityHall(subCityHall)
                .region5(fields[REGION5_POS])
                .region8(fields[REGION8_POS])
                .name(fields[MARKET_NAME_POS])
                .registry(fields[REGISTRY_POS])
                .street(fields[STREET_POS])
                .number(fields[NUMBER_POS])
                .neighborhood(fields[NEIGHBORHOOD_POS])
                .reference(fields[REFERENCE_POS])
                .build();

        return Optional.of(marketDto);
    }

    private static String[] splitCsvLine(String line) {

        final String[] fields = line.split(",");
        if (fields.length < FIELD_NUMBER) {
            String[] newFields = new String[FIELD_NUMBER];
            for (int i=0; i<fields.length; i++) {
                newFields[i] = fields[i];
            }
            newFields[FIELD_NUMBER-1] = "";
            return newFields;
        }

        return fields;
    }
}
