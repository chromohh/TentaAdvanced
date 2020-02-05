package se.ecutb.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ecutb.model.Address;
import se.ecutb.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component("PersonRepositoryImp")

public class PersonRepositoryImp implements PersonRepository{

    private List<Person> personData = new ArrayList<>();

    @Override
    public Optional<Person> findById(int personId) {
       try{
           Optional<Person> optionalPeron = personData.stream().filter(p -> p.getPersonId() == personId).findFirst();
           return optionalPeron;
       }catch(Exception e){
           e.printStackTrace();
           return Optional.empty();
       }
    }

    @Override
    public Person persist(Person person) throws IllegalArgumentException {
        if(!personData.stream().anyMatch(p -> p.getEmail().equals(person.getEmail()))){
        personData.add(person);
        return person;
        }else if(personData.stream().anyMatch(p -> p.getEmail().equals(person.getEmail()))){
            throw new IllegalArgumentException();
        }
        return person;

    }

    @Override
    public Optional<Person> findByEmail(String email) {
        try{
            Optional<Person> optionalPeron = personData.stream().filter(p -> p.getEmail().equalsIgnoreCase(email)).findFirst();
            return optionalPeron;
        }catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Person> findByAddress(Address address) {
        if(address != null){
            return  personData.stream()
                .filter(person -> address.equals(person.getAddress()))
                .collect(Collectors.toList());
        }else{
            return  personData.stream()
                    .filter(person -> person.getAddress() == null)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Person> findByCity(String city) {
        if(city != null){
            return  personData.stream()
                    .filter(p -> p.getAddress() != null)
                    .filter(person -> city.equalsIgnoreCase(person.getAddress().getCity()))
                    .collect(Collectors.toList());
        }else{
            return  personData.stream()
                    .filter(person -> person.getAddress().getCity() == null)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Person> findByLastName(String lastName) {
        if(lastName != null){
            return  personData.stream()
                    .filter(p -> p.getLastName() != null)
                    .filter(person -> lastName.equalsIgnoreCase(person.getLastName()))
                    .collect(Collectors.toList());
        }else{
            return  personData.stream()
                    .filter(person -> person.getLastName() == null)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Person> findByFullName(String fullName) {
        if(fullName != null) {
            return personData.stream()
                    .filter(p -> p.getFirstName() != null || p.getLastName() != null)
                    .filter(person -> String.join( " " ,person.getFirstName(),person.getLastName()).equalsIgnoreCase(fullName))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<Person> findAll() {
        return personData;
    }

    @Override
    public boolean delete(int personId) throws IllegalArgumentException {
        if(personData.stream().anyMatch(person -> person.getPersonId() == personId)){
            personData.remove(personData.stream().filter(person -> person.getPersonId() == personId).findFirst().get());
            return true;
        }else if(!personData.stream().anyMatch(person -> person.getPersonId() == personId)){
            throw new IllegalArgumentException();
        }
        return false;
    }

    @Override
    public void clear() {

        personData = new ArrayList<>();

    }
}
