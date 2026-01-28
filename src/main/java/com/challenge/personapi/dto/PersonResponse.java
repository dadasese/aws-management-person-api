package com.challenge.personapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {
    private Long id;
    private String identificationNumber;
    private String name;
    private String email;

}
