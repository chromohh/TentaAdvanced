package se.ecutb.data;


public class IdSequencerimp implements IdSequencers{

    private int personId;
    private int todoId;

    @Override
    public int nextPersonId() {
        return ++personId;
    }

    @Override
    public int nextTodoId() {
        return ++todoId;
    }

    @Override
    public void clearPersonId() {
        personId = 0;

    }

    @Override
    public void clearTodoId() {
        todoId = 0;
    }
}
