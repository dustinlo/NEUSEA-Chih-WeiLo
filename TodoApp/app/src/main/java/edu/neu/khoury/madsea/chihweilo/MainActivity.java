package edu.neu.khoury.madsea.chihweilo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.khoury.madsea.chihweilo.data.ToDoItem;
import edu.neu.khoury.madsea.chihweilo.data.ToDoItemRepository;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnToDoListener {

    // vars
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
//    private ToDoViewModel viewModel;

    private ToDoItemRepository mToDoRepository;
    private List<ToDoItem> mTodoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("My Tasks");

        mToDoRepository = new ToDoItemRepository(this);
        retrieveToDos();

        buildRecyclerView();
    }

    private void retrieveToDos(){
        mToDoRepository.retrieveToDos().observe(this, new Observer<List<ToDoItem>>() {
            @Override
            public void onChanged(List<ToDoItem> toDoItems) {
                if (mTodoList.size() >0){
                    mTodoList.clear();
                }
                if (toDoItems!= null){
                    mTodoList.addAll(toDoItems);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapter(mTodoList, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    // listener binding to fab
    public void createTask(View view) {
        Intent intent =new Intent(this, ToDoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                showToastSuccess();
            }
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void editToDo(int position) {
        Intent intent = new Intent(this, ToDoActivity.class);
        intent.putExtra("selected_todo", mTodoList.get(position));
        startActivity(intent);
    }

    @Override
    public void checkToDo(int position, boolean isChecked) {
        ToDoItem todo = mTodoList.get(position);
        todo.setChecked(isChecked);
        mToDoRepository.updateToDo(todo);
    }


    // helper
    public void showToastSuccess(){
        Toast toast = Toast.makeText(this, R.string.entry_added,
                                          Toast.LENGTH_SHORT);
        toast.show();
    }
}