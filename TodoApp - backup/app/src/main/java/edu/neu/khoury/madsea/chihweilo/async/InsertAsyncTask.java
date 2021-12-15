package edu.neu.khoury.madsea.chihweilo.async;

import android.os.AsyncTask;

import edu.neu.khoury.madsea.chihweilo.data.ToDoDao;
import edu.neu.khoury.madsea.chihweilo.data.ToDoItem;

public class InsertAsyncTask extends AsyncTask<ToDoItem, Void, Void> {

    private ToDoDao mToDoDao;

    public InsertAsyncTask(ToDoDao dao) {mToDoDao = dao;}

    @Override
    protected Void doInBackground(ToDoItem... toDoItems) {
        mToDoDao.insertTodo(toDoItems);
        return null;
    }
}
