package se.ecutb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ecutb.data.IdSequencers;
import se.ecutb.model.AbstractTodoFactory;
import se.ecutb.model.Person;
import se.ecutb.model.Todo;

import java.time.LocalDate;

@Component
public class CreateTodoServiceimp extends AbstractTodoFactory implements CreateTodoService {
    @Autowired
    private IdSequencers sequencers;

    @Override
    public Todo createTodo(String taskDescription, LocalDate deadLine, Person assignee) throws IllegalArgumentException {
        if (assignee != null){
        Todo ret = createTodoItem(sequencers.nextTodoId(), taskDescription, deadLine, assignee);
        return ret;
        }
        return createTodo(taskDescription, deadLine);
    }

    @Override
    public Todo createTodo(String taskDescription, LocalDate deadLine) throws IllegalArgumentException {
        if(taskDescription != null && deadLine != null) {
            Todo ret = createTodoItem(sequencers.nextTodoId(), taskDescription, deadLine);
            return ret;
        }
        throw new IllegalArgumentException();
    }
}
