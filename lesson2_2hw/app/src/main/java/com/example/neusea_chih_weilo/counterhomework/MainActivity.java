package com.example.neusea_chih_weilo.counterhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;
    private TextView mShowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.tCount);
        if (savedInstanceState != null) {
            if (savedInstanceState.getInt("counter") > 0) {
                mCount = savedInstanceState.getInt("counter");
                mShowCount.setText(Integer.toString(mCount));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCount > 0) {
            outState.putInt("counter", mCount);
        }
    }

    public void incrementNumber(View view) {
        mCount++;
        if (mShowCount != null) {
            mShowCount.setText(Integer.toString(mCount));
        }
    }


}