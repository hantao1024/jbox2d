package com.blofin.myjbox2d.five;

import android.animation.ValueAnimator;
import android.content.Context;
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

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.blofin.myjbox2d.R;
import com.blofin.myjbox2d.four.JBoxCollisionView;

public class FiveActivity extends AppCompatActivity implements View.OnClickListener {

    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
        lottieAnimationView=findViewById(R.id.animation);
        initView();
    }

    private void initView() {
        // 代码设置动画文件
        lottieAnimationView.setAnimation("main.json");
//        // 开始播放动画，首次播放会有短暂延迟，因为加载动画文件需要时间
//        lottieAnimationView.playAnimation();
        lottieAnimationView.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = animation.getAnimatedFraction();//播放进度
                // TODO:
                if (progress==1) {
                    finish();
                }
            }
        });
        //监听json文件加载完成
        lottieAnimationView.addLottieOnCompositionLoadedListener(new LottieOnCompositionLoadedListener() {
            @Override
            public void onCompositionLoaded(LottieComposition composition) {
                //此时渲染了动画第一帧
            }
        });

        // 将json文件下载到本地，再进行渲染，需要考虑耗时长的情况
//        lottieAnimationView.setAnimationFromUrl("");
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    public void onClick(View v) {

    }
    public void testClick(View view){

    }
}