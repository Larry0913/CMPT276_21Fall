package com.example.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {

    ImageView mainIconImageView;
    AnimatorSet animatorSet;
    private boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mainIconImageView = findViewById(R.id.welcome_main_icon);
        startAnimators();
    }

    private void startAnimators() {
        Animator moveAnimator = ObjectAnimator.ofFloat(mainIconImageView, "translationX", -500, 0);
        Animator rotateAnimator = ObjectAnimator.ofFloat(mainIconImageView, "rotation", 0, 360);
        Animator scaleXAnimator = ObjectAnimator.ofFloat(mainIconImageView, "scaleX", 0, 1);
        Animator scaleYAnimator = ObjectAnimator.ofFloat(mainIconImageView, "scaleY", 0, 1);

        animatorSet = new AnimatorSet();
        animatorSet.play(moveAnimator).with(rotateAnimator).with(scaleXAnimator).with(scaleYAnimator);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(3000);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                skip(mainIconImageView);
            }
        });
        animatorSet.setStartDelay(500);
        animatorSet.start();
    }


    public void skip(View view) {
        //if finished ,return, avoid to open main activity twice when user clicked skip button and animation end!
        if (finished) return;

        startActivity(new Intent(this, MainActivity.class));
        finished = true;
        finish();
    }
}