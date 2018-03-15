package com.mandeep.officialcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Animation fromup;        //animation for the logo page
    Animation fromleft;
    Animation fromright;
    ImageView logo;
    ImageView tv1;
    ImageView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo=findViewById(R.id.imageView12);
        tv1=findViewById(R.id.imageView13);
        tv2=findViewById(R.id.imageView10);
        fromup= AnimationUtils.loadAnimation(this,R.anim.fromup);
        fromright=AnimationUtils.loadAnimation(this,R.anim.fromleft);
        fromright=AnimationUtils.loadAnimation(this,R.anim.fromright);
        logo.setAnimation(fromup);
        tv1.setAnimation(fromleft);
        tv2.setAnimation(fromright);
        Thread thread=new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(2000);
                    Intent i=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(i);
                }

                 catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


        };
        thread.start();


    }

    @Override
    public void onClick(View view) {

    }
}
