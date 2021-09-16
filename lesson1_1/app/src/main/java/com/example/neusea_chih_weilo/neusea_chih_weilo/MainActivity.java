package com.example.neusea_chih_weilo.neusea_chih_weilo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int mCount = 0;
    private TextView mShowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.toast_message,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    public void countUp(View view) {
        mCount++;
        if (mShowCount != null) {
            mShowCount.setText(Integer.toString(mCount));

            Button btnZero = (Button) findViewById(R.id.button_zero);
            btnZero.setBackgroundColor(Color.BLUE);
            if (mCount % 2 == 0) {
                view.setBackgroundColor(Color.RED);
            } else {
                view.setBackgroundColor(Color.GREEN);
            }
        }
    }

    public void setZero(View view){
        mCount = 0;
        view.setBackgroundColor(Color.LTGRAY);
        Button btnCount = (Button) findViewById(R.id.button_count);
        btnCount.setBackgroundResource(android.R.drawable.btn_default);
        mShowCount.setText(Integer.toString(0));
    }
}