package edu.neu.khoury.madsea.chihweilo.data;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

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

    }

//    public static ToDoItemRepository getSingleton(Application application){
//        if(singleton == null){
//            singleton = new ToDoItemRepository(application);
//        }
//        return singleton;
//    }
//
//    public List<ToDoItem> asList() {
//        return mToDoDataSource.getTodos().getValue();
//    }
//
//
//    public LiveData<List<ToDoItem>> getAllTodos() {
//        return mToDoDataSource.getTodos();
//    }
//
//
//
//    public void addToDo(ToDoItem newToDo) {
//        mToDoDataSource.insert(newToDo);
//    }
//    public void updateToDo(ToDoItem todo) {
//        mToDoDataSource.update(todo);
//    }

//    @NonNull
//    @Override
//    public Iterator<ToDoItem> iterator() {
//        return mToDoDataSource.getTodos().getValue().iterator();
//    }
//
//    @Override
//    public void forEach(@NonNull Consumer<? super ToDoItem> action) {
//        mToDoDataSource.getTodos().getValue().forEach(action);
//    }
//
//    @NonNull
//    @Override
//    public Spliterator<ToDoItem> spliterator() {
//        return mToDoDataSource.getTodos().getValue().spliterator();
//    }


}
