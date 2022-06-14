package com.blofin.myjbox2d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blofin.myjbox2d.four.FourActivity;
import com.blofin.myjbox2d.three.ThreeActivity;
import com.blofin.myjbox2d.two.TwoActivity;
import com.blofin.myjbox2d.widget.FirstActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void firstClick(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, FirstActivity.class);
        startActivity(intent);
    }
    public void twoClick(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, TwoActivity.class);
        startActivity(intent);
    }
    public void threeClick(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ThreeActivity.class);
        startActivity(intent);
    }
    public void fourClick(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, FourActivity.class);
        startActivity(intent);
    }
}