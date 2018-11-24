package com.nishant.collegeportal.collegeportal;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nishant.shad0w.collegeportal.R;

public class SplashActivity extends AppCompatActivity {

    ImageView iv;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);

        iv= (ImageView) findViewById(R.id.imageView2);

        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.first);//rotate tag rotates the picture

        iv.setAnimation(animation);
    }
}
