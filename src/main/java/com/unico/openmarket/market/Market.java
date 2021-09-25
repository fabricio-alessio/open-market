package com.unico.openmarket.market;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Market {
    @Id
    private long id;
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
    private int districtId;
    private int subCityHallId;
    private String region5;
    private String region8;
}