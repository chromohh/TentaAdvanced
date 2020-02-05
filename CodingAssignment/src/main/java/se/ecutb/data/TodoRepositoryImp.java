package se.ecutb.data;

import org.springframework.stereotype.Component;
import se.ecutb.model.Todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("TodoRepositoryImp")

public class TodoRepositoryImp implements TodoRepository {
    private List<Todo> todoData = new ArrayList<>();

    @Override
    public Todo persist(Todo todo) {
        if(!todoData.stream().anyMatch(p -> todo.getTodoId() == p.getTodoId())){
            todoData.add(todo);
            return todo;
        }
        return todo;
    }

    @Override
    public Optional<Todo> findById(int todoId) {
        try{
            Optional<Todo> optionalTodo = todoData.stream().filter(p -> p.getTodoId() == todoId).findFirst();
            return optionalTodo;
        }catch(Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Todo> findByTaskDescriptionContains(String taskDescription) {
        if(taskDescription != null){
            return  todoData.stream()
                    .filter(p -> p.getTaskDescription().toLowerCase().contains(taskDescription.toLowerCase()))
                    .collect(Collectors.toList());
        }else{
            return  todoData.stream()
                    .filter(p -> p.getTaskDescription() == null)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Todo> findByDeadLineBefore(LocalDate endDate) {
        if(endDate != null){
            return todoData.stream()
                    .filter(p -> p.getDeadLine().isBefore(endDate))
                    .collect(Collectors.toList());
        }else{
            return null;
        }
    }

    @Override
    public List<Todo> findByDeadLineAfter(LocalDate startDate) {
        if(startDate != null){
            return todoData.stream()
                    .filter(p -> p.getDeadLine().isAfter(startDate))
                    .collect(Collectors.toList());
        }else{
            return null;
        }
    }

    @Override
    public List<Todo> findByDeadLineBetween(LocalDate start, LocalDate end) {
        if(start != null || end != null){
            return todoData.stream()
                    .filter(p -> p.getDeadLine().isAfter(start))
                    .filter(p -> p.getDeadLine().isBefore(end))
                    .collect(Collectors.toList());
        }else{
            return null;
        }
    }

    @Override
    public List<Todo> findByAssigneeId(int personId) {
        return todoData.stream()
                    .filter(p -> p.getAssignee() != null)
                    .filter(p -> p.getAssignee().getPersonId() == personId)
                    .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findAllUnassignedTasks() {
        return todoData.stream()
                .filter(p -> p.getAssignee() == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findAllAssignedTasks() {
        return todoData.stream()
                .filter(p -> p.getAssignee() != null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findByDone(boolean isDone) {
        return todoData.stream()
                .filter(Todo::isDone)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findAll() {
        return todoData;
    }

    @Override
    public boolean delete(int todoId) throws IllegalArgumentException {
        if(todoData.stream().anyMatch(p -> p.getTodoId() == todoId)){
            todoData.remove(todoData.stream().filter(p -> p.getTodoId() == todoId).findFirst().get());
            return true;
        }else if(!todoData.stream().anyMatch(p -> p.getTodoId() == todoId)){
            throw new IllegalArgumentException();
        }
        return false;
    }

    @Override
    public void clear() {
        todoData = new ArrayList<>();
    }
}
