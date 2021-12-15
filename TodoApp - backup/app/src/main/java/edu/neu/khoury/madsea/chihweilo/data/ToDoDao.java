package edu.neu.khoury.madsea.chihweilo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ToDoDao {

    @Query("SELECT * FROM Todo")
    LiveData<List<ToDoItem>> getTodos();

    @Query("SELECT * FROM Todo where uid = :uid")
    LiveData<List<ToDoItem>> getTodoById(int uid);

    @Insert
    void insertTodo(ToDoItem... todo);

    @Update
    void updateTodo(ToDoItem... todo);

    @Delete
    void deleteTodo(ToDoItem... todo);
}
