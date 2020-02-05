package se.ecutb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ecutb.data.PersonRepository;
import se.ecutb.dto.PersonDto;
import se.ecutb.dto.PersonDtoWithTodo;
import se.ecutb.model.Address;
import se.ecutb.model.Person;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonServiceIml implements PersonService{

    @Autowired private PersonDtoConversionService personDtoConversionService;
    @Autowired private PersonRepository personRepository;
    @Autowired private CreatePersonService createPersonService;

    @Override
    public Person createPerson(String firstName, String lastName, String email, Address address) {
         if(personRepository.findAll().stream().filter(person -> email != person.getEmail()).findAny().isPresent()){
             personRepository.findAll().add(
                     createPersonService.create(firstName, lastName, email, address));
             return createPersonService.create(firstName, lastName, email, address);
         }
         return null;
    }

    @Override
    public List<PersonDto> findAll() {
        List<PersonDto> ret = personRepository.findAll().stream()
                .filter(person -> person != null)
                .map(person -> personDtoConversionService.convertToPersonDto(person))
                .collect(Collectors.toList());
        return ret;
    }

    @Override
    public PersonDto findById(int personId) throws IllegalArgumentException {
        if(personId > 0){
            return personRepository.findAll().stream().filter(person -> person.equals(personId))
                    .map(person -> personDtoConversionService.convertToPersonDto(person))
                    .findFirst().get();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Person findByEmail(String email) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<PersonDtoWithTodo> findPeopleWithAssignedTodos() {
        return null;
    }

    @Override
    public List<PersonDto> findAllPeopleWithNoTodos() {
        return null;
    }

    @Override
    public List<PersonDto> findPeopleByAddress(Address address) {
        return null;
    }

    @Override
    public List<PersonDto> findPeopleByCity(String city) {
        return null;
    }

    @Override
    public List<PersonDto> findByFullName(String fullName) {
        return null;
    }

    @Override
    public List<PersonDto> findByLastName(String lastName) {
        return null;
    }

    @Override
    public boolean deletePerson(int personId) throws IllegalArgumentException {
        return false;
    }
}
