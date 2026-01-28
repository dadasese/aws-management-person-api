package com.challenge.personapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest {

    @NotBlank(message = "the identification number field is required")
    private String identificationNumber;

    @NotBlank(message = "the name field is required")
    private String name;

    @NotBlank(message = "the email field is required")
    @Email(message = "the email field format must be valid")
    private String email;
}
