package edu.neu.khoury.madsea.chihweilo.data;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface IToDoDataSource {

    LiveData<List<ToDoItem>> getTodos();

    void insert(ToDoItem todo);

    void update(ToDoItem todo);
}
