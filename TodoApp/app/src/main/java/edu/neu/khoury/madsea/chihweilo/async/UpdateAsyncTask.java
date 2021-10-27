package edu.neu.khoury.madsea.chihweilo.async;

import android.os.AsyncTask;

import edu.neu.khoury.madsea.chihweilo.data.ToDoDao;
import edu.neu.khoury.madsea.chihweilo.data.ToDoItem;

public class UpdateAsyncTask extends AsyncTask<ToDoItem, Void, Void> {

    private ToDoDao mToDoDao;

    public UpdateAsyncTask(ToDoDao dao) {mToDoDao = dao;}

    @Override
    protected Void doInBackground(ToDoItem... toDoItems) {
        mToDoDao.updateTodo(toDoItems);
        return null;
    }
}
