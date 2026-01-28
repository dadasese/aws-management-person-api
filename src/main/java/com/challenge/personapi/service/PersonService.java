package com.challenge.personapi.service;

import com.challenge.personapi.dto.PersonRequest;
import com.challenge.personapi.dto.PersonResponse;
import com.challenge.personapi.entity.Person;
import com.challenge.personapi.exception.PersonAlreadyExistsException;
import com.challenge.personapi.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional
    public PersonResponse savePerson(PersonRequest personRequest){
        if (personRepository.existsByIdentificationNumber(personRequest.getIdentificationNumber())){
            throw new PersonAlreadyExistsException("unexpected error: potential duplicate information");
        }

        Person person = new Person();
        person.setIdentificationNumber(personRequest.getIdentificationNumber());
        person.setName(personRequest.getName());
        person.setEmail(personRequest.getEmail());

        Person saved = personRepository.save(person);

        return mapToPerson(saved);

    }

    @Transactional(readOnly = true)
    public PersonResponse searchPerson(String numberIdentification){
        Person person = personRepository.findByIdentificationNumber(numberIdentification)
                .orElseThrow(() -> new PersonAlreadyExistsException("person not found"));

        return mapToPerson(person);
    }

    private PersonResponse mapToPerson(Person person){
        return PersonResponse.builder()
                .id(person.getId())
                .identificationNumber(person.getIdentificationNumber())
                .name(person.getName())
                .email(person.getEmail())
                .build();
    }
}
