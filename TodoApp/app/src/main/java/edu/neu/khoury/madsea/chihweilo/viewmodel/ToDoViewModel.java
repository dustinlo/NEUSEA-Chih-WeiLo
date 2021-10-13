package edu.neu.khoury.madsea.chihweilo.viewmodel;

import android.app.Application;
import android.text.BoringLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import edu.neu.khoury.madsea.chihweilo.data.ToDoItem;
import edu.neu.khoury.madsea.chihweilo.data.ToDoItemRepository;

public class ToDoViewModel extends AndroidViewModel {

    private LiveData<List<ToDoItem>> listOfToDos;
    private MutableLiveData<String> title = new MutableLiveData<>();
    private MutableLiveData<String> deadline = new MutableLiveData<>();
    private MutableLiveData<Boolean> createdOrUpdated = new MutableLiveData<>();

    private ToDoItemRepository repository;

    public ToDoViewModel(@NonNull Application application) {
        super(application);
//        listOfToDos = new MutableLiveData<>();
        repository = ToDoItemRepository.getSingleton(application);
        if(title == null) {
            title = new MutableLiveData<>();
            title.setValue("");
        }
        if(deadline == null) {
            deadline = new MutableLiveData<>();
            deadline.setValue("");
        }
        listOfToDos = repository.getAllTodos();

        createdOrUpdated.setValue(Boolean.FALSE);
    }

    public LiveData<List<ToDoItem>> getListOfToDos() {
        return listOfToDos;
    }

    public void insertToDo(ToDoItem todo) {
        repository.addToDo(todo);
        createdOrUpdated.setValue(Boolean.TRUE);
    }

    public void editToDo(ToDoItem todo) {
        repository.updateToDo(todo);
        createdOrUpdated.setValue(Boolean.TRUE);
    }
}
