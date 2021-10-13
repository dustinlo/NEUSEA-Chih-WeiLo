package edu.neu.khoury.madsea.chihweilo.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * A Singleton class to store all data used globally.
 */
// adapted from Adrienne, https://github.com/ahope/cs5520_project/tree/main/todo-list
public class ToDoDbDataSource implements IToDoDataSource {

    private ToDoDao mToDoDao;

    public ToDoDbDataSource(Application application) {
        ToDoRoomDatabase db = ToDoRoomDatabase.getDbInstance(application);
        this.mToDoDao = db.toDoDao();
    }

    @Override
    public LiveData<List<ToDoItem>> getTodos() {
        return mToDoDao.getTodos();
    }

    @Override
    public void insert(ToDoItem todo) {
        ToDoRoomDatabase.databaseWriteExecutor.execute(() -> {
            mToDoDao.insertTodo(todo);
        });
    }

    @Override
    public void update(ToDoItem todo) {
        mToDoDao.updateTodo(todo);
    }
}
