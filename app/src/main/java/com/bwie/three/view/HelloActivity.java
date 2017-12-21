package com.bwie.three.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bwie.three.MainActivity;
import com.bwie.three.R;

public class HelloActivity extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        img = (ImageView) findViewById(R.id.Splash_img);

        //设置动画
        ObjectAnimator translationY = ObjectAnimator.ofFloat(img, "translationY", 0f, 500f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(img, "alpha", 0f, 1f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(img, "rotation", 0f, 360f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(img, "scaleY", 2f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(img, "scaleX", 2f, 1f);

        //组合动画
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(translationY).with(alpha).with(rotation).with(scaleY).with(scaleX);
        animatorSet.setDuration(3000);
        animatorSet.start();

        //动画监听
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //结束后跳转
                Intent intent=new Intent(HelloActivity.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


    }
}
