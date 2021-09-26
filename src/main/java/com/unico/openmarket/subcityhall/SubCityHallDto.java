package com.unico.openmarket.subcityhall;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class SubCityHallDto {
    @NotNull(message = "SubCityHall.Code is mandatory")
    private Integer code;
    @NotBlank(message = "SubCityHall.Name is mandatory")
    private String name;
}
