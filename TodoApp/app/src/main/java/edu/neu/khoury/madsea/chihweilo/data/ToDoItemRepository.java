package edu.neu.khoury.madsea.chihweilo.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

// adapted from Adrienne, https://github.com/ahope/cs5520_project/tree/main/todo-list
public class ToDoItemRepository implements Iterable<ToDoItem> {

    private IToDoDataSource mToDoDataSource;

    private static ToDoItemRepository singleton;

    private ToDoItemRepository(Application application){
        mToDoDataSource = new ToDoDbDataSource(application);
    }

    public static ToDoItemRepository getSingleton(Application application){
        if(singleton == null){
            singleton = new ToDoItemRepository(application);
        }
        return singleton;
    }

    public List<ToDoItem> asList() {
        return mToDoDataSource.getTodos().getValue();
    }


    public LiveData<List<ToDoItem>> getAllTodos() {
        return mToDoDataSource.getTodos();
    }



    public void addToDo(ToDoItem newToDo) {
        mToDoDataSource.insert(newToDo);
    }
    public void updateToDo(ToDoItem todo) {
        mToDoDataSource.update(todo);
    }

    @NonNull
    @Override
    public Iterator<ToDoItem> iterator() {
        return mToDoDataSource.getTodos().getValue().iterator();
    }

    @Override
    public void forEach(@NonNull Consumer<? super ToDoItem> action) {
        mToDoDataSource.getTodos().getValue().forEach(action);
    }

    @NonNull
    @Override
    public Spliterator<ToDoItem> spliterator() {
        return mToDoDataSource.getTodos().getValue().spliterator();
    }


}
