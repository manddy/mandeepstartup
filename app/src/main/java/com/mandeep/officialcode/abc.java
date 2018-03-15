package com.mandeep.officialcode;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Mandeepsingh on 3/1/2018.
 */

public class abc extends AppCompatActivity {
   Button button;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);
        //button=(Button)findViewById(R.id.gtm);
        //button.setOnClickListener(this);

    }

//    @Override
//    public void onClick(View view) {
//        Intent i=new Intent(abc.this,MapsActivity.class);
//        i.putExtra("PersonName",  _nameText.getText().toString().trim());
//    }
}
