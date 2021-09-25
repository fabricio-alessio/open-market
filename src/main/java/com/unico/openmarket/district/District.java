package com.unico.openmarket.district;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class District {
    @Id
    private int id;
    private int code;
    private String name;
}
