package se.ecutb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ecutb.data.TodoRepository;
import se.ecutb.dto.TodoDto;
import se.ecutb.model.Person;
import se.ecutb.model.Todo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoServiceImp implements TodoService {

    @Autowired private CreateTodoService createTodoService;
    @Autowired private TodoDtoConversionService todoDtoConversionService;
    @Autowired private TodoRepository todoRepository;

    @Override
    public Todo createTodo(String taskDescription, LocalDate deadLine, Person assignee) {
        Todo ret = createTodoService.createTodo(taskDescription, deadLine, assignee);
        todoRepository.findAll().add(ret);
        return ret;
    }

    @Override
    public TodoDto findById(int todoId) throws IllegalArgumentException {
        List<TodoDto> todoDtos = todoRepository.findAll().stream().map(todo -> todoDtoConversionService.convertToDto(todo)).collect(Collectors.toList());
        if(todoId > 0 && todoId < 1000){
        return todoDtos.stream().filter(todoDto -> todoDto.getTodoId() == todoId).findFirst().get();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<TodoDto> findByTaskDescription(String taskDescription) {
        if(taskDescription != null){
        return todoRepository.findByTaskDescriptionContains(taskDescription)
                .stream().map(todo -> todoDtoConversionService.convertToDto(todo))
                .collect(Collectors.toList());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<TodoDto> findByDeadLineBefore(LocalDate endDate) {
        if(endDate != null) {
            return todoRepository.findByDeadLineBefore(endDate)
                    .stream().map(todo -> todoDtoConversionService.convertToDto(todo))
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<TodoDto> findByDeadLineAfter(LocalDate startDate) {
        if(startDate != null) {
            return todoRepository.findByDeadLineAfter(startDate)
                    .stream().map(todo -> todoDtoConversionService.convertToDto(todo))
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<TodoDto> findByDeadLineBetween(LocalDate startDate, LocalDate endDate) {
        if(startDate != null && endDate != null) {
            return todoRepository.findByDeadLineBetween(startDate, endDate)
                    .stream().map(todo -> todoDtoConversionService.convertToDto(todo))
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<TodoDto> findAssignedTasksByPersonId(int personId) {
        if(personId > 0) {
            return todoRepository.findByAssigneeId(personId)
                    .stream().map(todo -> todoDtoConversionService.convertToDto(todo))
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<TodoDto> findUnassignedTasks() {
            return todoRepository.findAll()
                    .stream().filter(p -> p.getAssignee() == null)
                    .map(todo -> todoDtoConversionService.convertToDto(todo))
                    .collect(Collectors.toList());
    }

    @Override
    public List<TodoDto> findAssignedTasks() {
        return todoRepository.findAll()
                .stream().filter(p -> p.getAssignee() != null)
                .map(todo -> todoDtoConversionService.convertToDto(todo))
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoDto> findByDoneStatus(boolean done) {
        return todoRepository.findAll()
                .stream().filter(p -> p.isDone() == done)
                .map(todo -> todoDtoConversionService.convertToDto(todo))
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoDto> findAll() {
        return todoRepository.findAll()
                    .stream().map(todo -> todoDtoConversionService.convertToDto(todo))
                    .collect(Collectors.toList());
    }

    @Override
    public boolean delete(int todoId) throws IllegalArgumentException {
        if(todoRepository.findAll().stream().filter(todo -> todo.getTodoId() == todoId).findFirst().isPresent()){
            todoRepository.findAll().remove(todoRepository.findAll().stream()
                    .filter(todo -> todo.getTodoId() == todoId)
                    .findFirst().get());
            return true;
        }else{
            return false;
        }
    }
}
