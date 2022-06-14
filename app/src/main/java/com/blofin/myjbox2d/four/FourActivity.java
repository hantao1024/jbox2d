package com.blofin.myjbox2d.four;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blofin.myjbox2d.R;
import com.blofin.myjbox2d.three.ThreeActivity;
import com.blofin.myjbox2d.two.TwoActivity;
import com.blofin.myjbox2d.widget.FirstActivity;

public class FourActivity extends AppCompatActivity implements SensorEventListener,View.OnClickListener {
    private JBoxCollisionView mobike_jbox_view;
    private TextView mobike_jbox_jump;

    private SensorManager sensorManager;
    private Sensor sensor;

    private int[] imgs = {
//            R.mipmap.share_fb,
//            R.mipmap.share_kongjian,
//            R.mipmap.share_pyq,
//            R.mipmap.share_qq,
            R.mipmap.share_tw,
            R.mipmap.share_wechat,
            R.mipmap.share_weibo,
//            R.mipmap.icon_0002,
//            R.mipmap.icon_0003,
//            R.mipmap.icon_0004,
//            R.mipmap.icon_0005,
//            R.mipmap.icon_0006,
//            R.mipmap.icon_0001
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobike_layout);
    }

    private void initView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER|Gravity.TOP;
        for (int i = 0; i < imgs.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imgs[i]);
            imageView.setTag(R.id.wd_view_circle_tag, true);
            mobike_jbox_view.addView(imageView, layoutParams);
        }
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mobike_jbox_view = (JBoxCollisionView) findViewById(R.id.mobike_jbox_view);
        mobike_jbox_jump = (TextView) findViewById(R.id.mobike_jbox_jump);
        mobike_jbox_jump.setOnClickListener(this);
        initView();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1] * 2.0f;
            mobike_jbox_view.onSensorChanged(-x, y);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == mobike_jbox_jump.getId()){
            mobike_jbox_view.onRandomChanged();
        }
    }
    public void testClick(View view){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER|Gravity.TOP;
        for (int i = 0; i < imgs.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imgs[i]);
            imageView.setTag(R.id.wd_view_circle_tag, true);
            mobike_jbox_view.addView(imageView, layoutParams);
        }
    }
}