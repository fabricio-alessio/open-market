package com.unico.openmarket.market;

import com.unico.openmarket.district.DistrictDto;
import com.unico.openmarket.subcityhall.SubCityHallDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class MarketDto {
    @NotNull(message = "Code is mandatory")
    private Long code;
    @NotNull(message = "Lng is mandatory")
    private Long lng;
    @NotNull(message = "Lat is mandatory")
    private Long lat;
    @NotNull(message = "Setcens is mandatory")
    private Long setcens;
    @NotNull(message = "Areap is mandatory")
    private Long areap;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Registry is mandatory")
    private String registry;
    @NotBlank(message = "Street is mandatory")
    private String street;
    @NotBlank(message = "Number is mandatory")
    private String number;
    @NotBlank(message = "Neighborhood is mandatory")
    private String neighborhood;
    private String reference;
    @Valid
    @NotNull(message = "District is mandatory")
    private DistrictDto district;
    @Valid
    @NotNull(message = "SubCityHall is mandatory")
    private SubCityHallDto subCityHall;
    @NotBlank(message = "Region5 is mandatory")
    private String region5;
    @NotBlank(message = "Region8 is mandatory")
    private String region8;
}
