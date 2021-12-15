package edu.neu.khoury.madsea.chihweilo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.neu.khoury.madsea.chihweilo.data.ToDoItem;
import edu.neu.khoury.madsea.chihweilo.data.ToDoItemRepository;
import edu.neu.khoury.madsea.chihweilo.databinding.ActivityToDoBinding;

public class ToDoActivity extends AppCompatActivity {

    private ActivityToDoBinding binding;
    private SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
    private boolean editMode;
    private LiveData<List<ToDoItem>> toDoItemList;
    private static final String TAG = "";
    private ToDoItem todo;
    private Calendar myCalender;
    private EditText mTitle;
    private EditText mDetails;
    private Spinner mTag;
    private EditText mDeadline;
    private CheckBox mRemind;
    private EditText mDateRemind;

    private ToDoItemRepository repository;


    public static ToDoActivity newInstance() {
        return new ToDoActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        repository = new ToDoItemRepository(this);

        getComponents();
        addComponentBehaviors();


        if (getIntent().hasExtra("selected_todo")) {
            todo = getIntent().getParcelableExtra("selected_todo");
            editMode = true;
            getSupportActionBar().setTitle("Edit ToDo");
        } else {
            todo = new ToDoItem();
            editMode = false;
            todo.setChecked(false);
            getSupportActionBar().setTitle("Create ToDo");
        }
        populateFieldsForUpdate();

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrUpdateTask();
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addOrUpdateTask() {

        String title = mTitle.getText().toString();
        String details = mDetails.getText().toString();
        int tagIndex = mTag.getSelectedItemPosition();
        Date deadline;
            try{
                deadline = inputDateFormat.parse(mDeadline.getText().toString());
            } catch (Exception e) {
                deadline = null;
            }

        boolean isRemind = mRemind.isChecked();
        Date dateRemind;
        try {
            dateRemind = (isRemind == true)
                    ? inputDateFormat.parse(mDateRemind.getText().toString()) : null;
        } catch (Exception e) {
            dateRemind = null;
        }

        if (title.trim().isEmpty()){
            Toast.makeText(this, "Title cannot be empty.",
                                          Toast.LENGTH_SHORT).show();
            return;
        } else {
            todo.setTitle(title);
            if (details != null) {
                todo.setDetails(details);
            }
            todo.setTag(tagIndex);

            if (deadline != null) {
                todo.setDateDeadline(inputDateFormat.format(deadline));
            }
            todo.setReminded(isRemind);
            if(isRemind == true && dateRemind != null) {
                todo.setDateRemind(inputDateFormat.format(dateRemind));
            } else {
                todo.setDateRemind("");
            }

            if (editMode == true) {
                repository.updateToDo(todo);
            } else {
                repository.insertToDo(todo);
            }
        }
        finish();
    }


    private void populateFieldsForUpdate() {
        if (editMode == true) {
            // title
            String title = todo.getTitle();
            if (!title.trim().isEmpty()) {
                mTitle.setText(title);
            }
            // description
            String details = todo.getDetails();
            if (!details.trim().isEmpty()) {
                mDetails.setText(details);
            }
            // tag
            mTag.setSelection(todo.getTag());
            // Deadline
            Date deadline;
            try{
                deadline = inputDateFormat.parse(todo.getDateDeadline());
            } catch (Exception e) {
                deadline = null;
            }
            if (deadline != null) {
                mDeadline.setText(inputDateFormat.format(deadline));
            }
            //isReminded
            boolean isRemind = todo.isReminded();
            if (isRemind) {
                mRemind.setChecked(true);
                mDateRemind.setEnabled(true);
            } else {
                mDateRemind.setText("");
                mDateRemind.setEnabled(false);
            }
            Date dateRemind;
            try{
                dateRemind = inputDateFormat.parse(todo.getDateRemind());
            } catch (Exception e) {
                dateRemind = null;
            }
            if (dateRemind != null) {
                mDateRemind.setText(inputDateFormat.format(dateRemind));
            }
        }

    }

    private void addComponentBehaviors() {
        //spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrTagTodo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTag.setAdapter(adapter);

        // deadline date picker
        getDateByDateTimePicker(mDeadline);

        // checkbox event listener
        // set the remind date to enabled if checked, otherwise, disabled
        mRemind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   if (b == true) {
                       mDateRemind.setEnabled(true);
                       getDateByDateTimePicker(mDateRemind);
                   } else {
                       mDateRemind.setEnabled(false);
                       mDateRemind.setText("");
                   }
               }
           }
        );
    }

    private void getDateByDateTimePicker(EditText editDate) {

        // Click on deadline date to pop up a date picker
        // source: https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ToDoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void getComponents() {
        mTitle = findViewById(R.id.txtTitle);
        mDetails = findViewById(R.id.txtDetails);
        mTag = findViewById(R.id.spnTags);
        mDeadline = findViewById(R.id.editDateDeadline);
        mRemind = findViewById(R.id.chkRemindMe);
        mDateRemind = findViewById(R.id.editDateRemind);
    }
}
