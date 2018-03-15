package com.mandeep.officialcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Mandeepsingh on 3/1/2018.
 */

public class profileuser extends AppCompatActivity implements View.OnClickListener {
    Button b;
    String username;
    TextView nammi;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.profile);
            //nammi=(TextView)findViewById(R.id.conman);
            Bundle bundle = getIntent().getExtras();
            username = bundle.getString("PersonName");
            b=(Button)findViewById(R.id.button2);
            b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent(profileuser.this,MapsActivity.class);
        i.putExtra("PersonName",username);
        startActivity(i);
    }
}
