package com.unico.openmarket.district;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistrictDto {
    private int code;
    private String name;
}
