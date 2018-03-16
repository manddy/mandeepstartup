package com.mandeep.officialcode;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mandeepsingh on 3/1/2018.
 */

public class profileuser extends AppCompatActivity {
    public static int RESULT_LOAD_IMAGE_01 = 1;

    Button btnAddImage;
    Button btnDiscover;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.profile);
            //nammi=(TextView)findViewById(R.id.conman);
            //Bundle bundle = getIntent().getExtras();
            //username = bundle.getString("PersonName");
            //b=(Button)findViewById(R.id.button2);
            //b.setOnClickListener(this);

        btnAddImage = findViewById(R.id.btnAddImage);
        btnDiscover = findViewById(R.id.btnDiscover);

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE_01);
            }
        });

        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(profileuser.this , MapsActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE_01 && resultCode == RESULT_OK && null != data) {
            Uri selectImage = data.getData();
            String[] filePathcolumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectImage, filePathcolumn,
                    null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathcolumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView ivMemeImage = findViewById(R.id.ivProfileImage);
            ivMemeImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

    }

}
