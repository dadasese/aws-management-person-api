package com.challenge.personapi.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Person")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "the identification number field is required")
    @Column(unique = true, nullable = false, length = 20)
    private String identificationNumber;

    @NotBlank(message = "the name field is required")
    @Column(nullable = false, length = 150)
    private String name;

    @NotBlank(message = "the email field is required")
    @Email
    @Column(nullable = false, length = 100)
    private String email;

}
