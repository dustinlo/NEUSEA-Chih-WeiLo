package edu.neu.khoury.madsea.chihweilo.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ToDoItem.class}, version = 1)
public abstract class ToDoRoomDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "todos_db";
    public static ToDoRoomDatabase instance;

    public static ToDoRoomDatabase getDbInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ToDoRoomDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract ToDoDao getTodoDao();
}
