package com.example.neusea_chih_weilo.neusea_chih_weilo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

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
            Button count = (Button) findViewById(R.id.button_count);
            Button zero = (Button) findViewById(R.id.button_zero);
            Drawable buttonDrawable = count.getBackground();
            Drawable buttonDrawableZero = zero.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
            buttonDrawableZero = zero.getBackground();
            if (mCount % 2 == 0) {
                DrawableCompat.setTint(buttonDrawable, Color.RED);
            } else {
                DrawableCompat.setTint(buttonDrawable, Color.GREEN);
            }
            DrawableCompat.setTint(buttonDrawableZero, Color.RED);
            zero.setBackground(buttonDrawableZero);
            count.setBackground(buttonDrawable);
        }
    }

    public void setZero(View view){
        mCount = 0;
        Button zero = (Button) findViewById(R.id.button_zero);
        Drawable buttonDrawableZero = zero.getBackground();
        DrawableCompat.setTint(buttonDrawableZero, Color.LTGRAY);
        zero.setBackground(buttonDrawableZero);
        mShowCount.setText(Integer.toString(0));
    }
}