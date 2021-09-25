package com.unico.openmarket.market;

import com.unico.openmarket.district.District;
import com.unico.openmarket.district.DistrictDto;
import com.unico.openmarket.subcityhall.SubCityHall;
import com.unico.openmarket.subcityhall.SubCityHallDto;

public class MarketAdapter {

    public static MarketDto adaptMarketDto(Market market, DistrictDto districtDto, SubCityHallDto subCityHallDto) {

        return MarketDto.builder()
                .code(market.getCode())
                .lat(market.getLat())
                .lng(market.getLng())
                .areap(market.getAreap())
                .district(districtDto)
                .subCityHall(subCityHallDto)
                .name(market.getName())
                .neighborhood(market.getNeighborhood())
                .number(market.getNumber())
                .reference(market.getReference())
                .region5(market.getRegion5())
                .region8(market.getRegion8())
                .registry(market.getRegistry())
                .setcens(market.getSetcens())
                .street(market.getStreet())
                .build();
    }

    public static Market adaptMarket(MarketDto marketDto, District district, SubCityHall subCityHall) {

        return Market.builder()
                .code(marketDto.getCode())
                .lat(marketDto.getLat())
                .lng(marketDto.getLng())
                .areap(marketDto.getAreap())
                .districtId(district.getId())
                .subCityHallId(subCityHall.getId())
                .name(marketDto.getName())
                .neighborhood(marketDto.getNeighborhood())
                .number(marketDto.getNumber())
                .reference(marketDto.getReference())
                .region5(marketDto.getRegion5())
                .region8(marketDto.getRegion8())
                .registry(marketDto.getRegistry())
                .setcens(marketDto.getSetcens())
                .street(marketDto.getStreet())
                .build();
    }
}
