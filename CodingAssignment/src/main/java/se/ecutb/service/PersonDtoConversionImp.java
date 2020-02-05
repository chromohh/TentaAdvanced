package se.ecutb.service;

import org.springframework.stereotype.Component;
import se.ecutb.dto.PersonDto;
import se.ecutb.dto.PersonDtoWithTodo;
import se.ecutb.model.Person;
import se.ecutb.model.Todo;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonDtoConversionImp implements PersonDtoConversionService {

    @Override
    public PersonDto convertToPersonDto(Person person) {
        PersonDto ret = new PersonDto(person.getPersonId(), person.getFirstName(), person.getLastName());
        return ret;
    }

    @Override
    public PersonDtoWithTodo convertToPersonDtoWithTodo(Person person, List<Todo> assignedTodos) {
        TodoDtoConversionService asd = new TodoDtoConversionImp();
        PersonDtoWithTodo ret = new PersonDtoWithTodo(person.getPersonId(),
                person.getFirstName(),
                person.getLastName(),
                assignedTodos.stream().map(todo -> asd.convertToDto(todo)).collect(Collectors.toList()));
        return ret;
    }
}
