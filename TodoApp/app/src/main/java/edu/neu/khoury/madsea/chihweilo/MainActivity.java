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
import edu.neu.khoury.madsea.chihweilo.viewmodel.ToDoViewModel;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ToDoViewModel viewModel;

    private ArrayList<ToDoItem> mTodoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("My Tasks");

        buildViewModel();
        buildRecyclerView();
//        viewModel.getAllToDoList();
    }

    private void buildViewModel(){
        viewModel = new ViewModelProvider(this).get(ToDoViewModel.class);

        viewModel.getListOfToDos().observe(this, todos-> {
            mAdapter.setToDoList(todos);
        });
    }
    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapter(this, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

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
        Context context = mRecyclerView.getContext();
        Intent intent = new Intent(context, ToDoActivity.class);
        intent.putExtra("index", position);
        startActivity(intent);
    }

    @Override
    public void checkToDo(ToDoItem todo, boolean isChecked) {
        todo.setChecked(isChecked);
        viewModel.editToDo(todo);
    }


    // helper
    public void showToastSuccess(){
        Toast toast = Toast.makeText(this, R.string.entry_added,
                                          Toast.LENGTH_SHORT);
        toast.show();
    }
}