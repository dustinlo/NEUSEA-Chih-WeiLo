package edu.neu.khoury.madsea.chihweilo.data;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.neu.khoury.madsea.chihweilo.async.DeleteAsyncTask;
import edu.neu.khoury.madsea.chihweilo.async.InsertAsyncTask;
import edu.neu.khoury.madsea.chihweilo.async.UpdateAsyncTask;

public class ToDoItemRepository {

    private ToDoRoomDatabase mToDoDatabase;
    private static ToDoItemRepository singleton;

    public ToDoItemRepository(Context context){
        mToDoDatabase = ToDoRoomDatabase.getDbInstance(context);
    }

    public void insertToDo(ToDoItem todo){
        new InsertAsyncTask(mToDoDatabase.getTodoDao()).execute(todo);
    }

    public void updateToDo(ToDoItem todo) {
        new UpdateAsyncTask(mToDoDatabase.getTodoDao()).execute(todo);
    }

    public LiveData<List<ToDoItem>> retrieveToDos(){
        return mToDoDatabase.getTodoDao().getTodos();
    }

    public void deleteToDo(ToDoItem todo){
        new DeleteAsyncTask(mToDoDatabase.getTodoDao()).execute(todo);
    }
}
