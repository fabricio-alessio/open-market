package com.unico.openmarket.district;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class DistrictDto {
    @NotNull(message = "District.Code is mandatory")
    private Integer code;
    @NotBlank(message = "District.Name is mandatory")
    private String name;
}
