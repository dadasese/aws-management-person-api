package com.challenge.personapi.controller;


import com.challenge.personapi.dto.PersonRequest;
import com.challenge.personapi.dto.PersonResponse;
import com.challenge.personapi.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class personController {

    private final PersonService personService;

    /**
    *  Endpoint for save person
    *  POST /api/person
    * */
    @PostMapping
    public ResponseEntity<PersonResponse> savePerson(@Valid @RequestBody PersonRequest personRequest){
        PersonResponse personResponse = personService.savePerson(personRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(personResponse);
    }

    /**
     *  Endpoint for search person by number identification
     *  GET /api/person/{numberIdentification}
     * */
    @GetMapping("/{numberIdentification}")
    public ResponseEntity<PersonResponse> searchPerson(@PathVariable String numberIdentification){
        PersonResponse personResponse = personService.searchPerson(numberIdentification);
        return ResponseEntity.ok(personResponse);
    }


}
