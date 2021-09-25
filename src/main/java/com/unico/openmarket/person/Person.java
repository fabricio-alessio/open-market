package com.unico.openmarket.person;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Person {
    @Id
    private long id;
    private String firstName;
    private String lastName;
}