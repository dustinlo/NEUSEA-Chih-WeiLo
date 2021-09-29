package edu.neu.khoury.madsea.chihweilo;

import androidx.appcompat.app.AppCompatActivity;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Task extends AppCompatActivity {

    private EditText mTitle;
    private EditText mDetails;
    private EditText mDeadline;
    private Spinner mTag;
    private CheckBox mRemind;
    private EditText mDateRemind;
    private Calendar myCalender;
    private SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
    private String title;
    private String details;
    private Date deadline;
    private int tagIndex;
    private boolean isRemind;
    private Date dateRemind;
    private boolean isUpdate = false;
    private int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        getSupportActionBar().setTitle("New Task");

        findViews();

        //spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrTagTodo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTag.setAdapter(adapter);

        // deadline date picker
        getDateByDateTimePicker(R.id.editDateDeadline);

        // checkbox event listener
        // set the remind date to enabled if checked, otherwise, disabled.
        mRemind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   if (b == true) {
                       mDateRemind.setEnabled(true);
                       getDateByDateTimePicker(mDateRemind.getId());
                   } else {
                       mDateRemind.setEnabled(false);
                       mDateRemind.setText("");
                   }
               }
           }
        );

        Intent intent = getIntent();
        position =intent.getIntExtra("index", -1);
        if (position != -1) {
            isUpdate = true;
            getSupportActionBar().setTitle("Edit Task");
            TaskDataSource instance = TaskDataSource.getInstance();
            TaskDetails task = instance.get(position);
            if (task != null) {
                // title
                title = task.getTitle();
                if (!title.trim().isEmpty()) {
                    mTitle.setText(title);
                }
                // description
                details = task.getDetails();
                if (!details.trim().isEmpty()) {
                    mDetails.setText(details);
                }
                // tag
                mTag.setSelection(task.getTag());
                // Deadline
                deadline = task.getDateDeadline();
                if (deadline != null) {
                    mDeadline.setText(inputDateFormat.format(deadline));
                }
                //isReminded
                isRemind = task.isRemind();
                if (isRemind) {
                    mRemind.setChecked(true);
                    mDateRemind.setEnabled(true);
                } else {
                    mDateRemind.setEnabled(false);
                }
                dateRemind = task.getDateRemind();
                if (dateRemind != null) {
                    mDateRemind.setText(inputDateFormat.format(dateRemind));
                }
            }
        }

    }

    private void getDateByDateTimePicker(int viewId) {

        // Click on deadline date to pop up a date picker
        // source: https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
        final Calendar myCalendar = Calendar.getInstance();

        EditText editDate = (EditText) findViewById(viewId);
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
                new DatePickerDialog(Task.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }



    public void AddTodo(View view) throws ParseException {
        TaskDataSource instance = TaskDataSource.getInstance();
        findViews();
        title = mTitle.getText().toString();
        details = mDetails.getText().toString();
        tagIndex = mTag.getSelectedItemPosition();

        try {
             deadline = inputDateFormat.parse(mDeadline.getText().toString());
        } catch (Exception e){
             deadline = null;
        }

        isRemind =mRemind.isChecked();
        dateRemind = null;
        try {
            dateRemind = (isRemind == true)
                    ? inputDateFormat.parse(mDateRemind.getText().toString()) : null;
        } catch (Exception e) {
        }

        if (title.trim().isEmpty()){
            Toast toast = Toast.makeText(this, "Title cannot be empty.",
                                          Toast.LENGTH_SHORT);
            toast.show();
        } else {
            TaskDetails task = new TaskDetails(title, details, deadline, tagIndex, isRemind, dateRemind);
            if (isUpdate) {
                instance.set(position, task);
            } else {
                instance.addTodo(task);
            }
            Intent replyIntent = new Intent();
            setResult(RESULT_OK, replyIntent);
            finish();
        }
        
    }

    private void findViews() {
        mTitle = findViewById(R.id.txtTitle);
        mDetails = findViewById(R.id.txtDetails);
        mTag = findViewById(R.id.spnTags);
        mDeadline = findViewById(R.id.editDateDeadline);
        mRemind = findViewById(R.id.chkRemindMe);
        mDateRemind = findViewById(R.id.editDateRemind);
    }

    public void cancelCreateTodo(View view) {
        Intent replyIntent = new Intent();
//        replyIntent.putExtra(EXTRA_REPLY, reply);
        setResult(RESULT_CANCELED,replyIntent);
        finish();
    }
}