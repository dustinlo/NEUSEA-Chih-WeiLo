package edu.neu.khoury.madsea.chihweilo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int CREATE_REQUEST = 1;
    public static final int UPDATE_REQUEST = 2;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TaskDataSource instance;

    private ArrayList<TaskDetails> mTodoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("My Tasks");

        createTodoList();
        buildRecyclerView();

    }

    private void createTodoList() {
        instance = TaskDataSource.getInstance();
        mTodoList = (ArrayList<TaskDetails>) instance.getTasks();
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapter(mTodoList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Context context = mRecyclerView.getContext();
                Intent intent = new Intent(context, Task.class);
                intent.putExtra("index", position);
                startActivityForResult(intent, UPDATE_REQUEST);
            }
        });
    }

    public void createTask(View view) {
        Intent intent =new Intent(this, Task.class);
        startActivityForResult(intent, CREATE_REQUEST);
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
    
    public void showToastSuccess(){
        Toast toast = Toast.makeText(this, R.string.entry_added,
                                          Toast.LENGTH_SHORT);
        toast.show();
    }
    
    


}