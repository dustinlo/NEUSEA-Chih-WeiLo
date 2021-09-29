package edu.neu.khoury.madsea.chihweilo;

import java.util.ArrayList;
import java.util.List;

/**
 * A Singleton class to store all data used globally.
 */
public final class TaskDataSource {

    private static TaskDataSource instance;
    private List<TaskDetails> listTodo;

    /**
     * This is a private constructor to instantiate the instance.
     */
    private TaskDataSource(){}

    /**
     * Static method to provide access to this singleton class.
     * @return a TaskDataSource object
     */
    public static TaskDataSource getInstance(){
        if (instance == null) {
            instance = new TaskDataSource();
        }

        return instance;
    }

    public void set(int index, TaskDetails task ) {
        listTodo.set(index, task);
    }

    public TaskDetails get(int index) {
        if (listTodo == null) {
            listTodo = new ArrayList<>();
        }
        if (index < listTodo.size()) {
            TaskDetails task = listTodo.get(index);
            return new TaskDetails(task.getTitle(), task.getDetails(),
                    task.getDateDeadline(), task.getTag(), task.isRemind(), task.getDateRemind());
        }
        return null;
    }

    public void addTodo(TaskDetails task) {
        if (listTodo == null) {
            listTodo = new ArrayList<>();
        }
        listTodo.add(task);
    }

    // I understand this is not ideal because it reveals the reference.
    public List<TaskDetails> getTasks() {
        if (listTodo == null) {
            listTodo = new ArrayList<>();
        }
        return listTodo;
    }
}
