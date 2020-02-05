package se.ecutb.service;

import org.springframework.stereotype.Component;
import se.ecutb.dto.TodoDto;
import se.ecutb.model.Todo;

@Component
public class TodoDtoConversionImp implements TodoDtoConversionService{
    @Override
    public TodoDto convertToDto(Todo todo) {
        try{
        TodoDto ret = new TodoDto(todo.getTodoId(),
                todo.getTaskDescription(),
                todo.getDeadLine(),
                todo.getAssignee().getPersonId(),
                todo.isDone());
        return  ret;
        }
        catch(NullPointerException e) {
            TodoDto ret = new TodoDto(todo.getTodoId(),
                todo.getTaskDescription(),
                todo.getDeadLine(),
                0,
                todo.isDone());
        return ret;
        }
    }
}
