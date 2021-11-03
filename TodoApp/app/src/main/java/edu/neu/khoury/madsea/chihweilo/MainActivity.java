package edu.neu.khoury.madsea.chihweilo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import edu.neu.khoury.madsea.chihweilo.data.ToDoItem;
import edu.neu.khoury.madsea.chihweilo.data.ToDoItemRepository;
import edu.neu.khoury.madsea.chihweilo.notification.AlarmReceiver;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnToDoListener {

    // vars
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ToDoItemRepository mToDoRepository;
    private List<ToDoItem> mTodoList = new ArrayList<>();

    private SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);

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

            // Loop through the list and add notification
                mTodoList.forEach(toDoItem -> {
                    try {
                        createNotification(toDoItem);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    public void createNotification(ToDoItem todo) throws ParseException {
        if (todo.getDateRemind() == null || todo.getDateRemind().trim().isEmpty()) {
            return;
        }
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        if (intent.hasExtra("todo")) {
            intent.removeExtra("todo");
        }
        intent.putExtra("todo", todo.getTitle());
        int id = ThreadLocalRandom.current().nextInt(1, 100000 + 1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDateFormat.parse(todo.getDateRemind()));
        alarmManager.setExact(AlarmManager.RTC, cal.getTimeInMillis(), pendingIntent);
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
}