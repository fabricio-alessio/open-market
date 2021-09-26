package com.unico.openmarket.subcityhall;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class SubCityHall {
    @Id
    private int id;
    private int code;
    private String name;
}
