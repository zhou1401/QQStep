package com.kaige.qqsetup;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAnimation();
        Button bt_again = (Button) findViewById(R.id.bt_again);
//        pneumonoultramicroscopicsilicovolcanoconiosis(肺尘病)
        bt_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });


    }

    private void startAnimation() {
        final QQStepView qqStepView=(QQStepView)findViewById(R.id.step_view);
        qqStepView.setStepMax(4000);
        //属性动画 后面讲的内容
        ValueAnimator valueAnimator= ObjectAnimator.ofFloat(0,3000);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentStep=(float)animation.getAnimatedValue();
                qqStepView.setmCurrentStep((int)currentStep);
            }
        });
        valueAnimator.start();
    }
}
