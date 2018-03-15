package com.mandeep.officialcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mandeep.officialcode.Utils.FirebaseMethods;

import butterknife.BindView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.ProgressDialog.*;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private Context mContext;
    private String email, username, password,retypePassword,datsmeid;
    private int age;
    private EditText mEmail, mPassword,mRetypePassword, mUsername,mdatsmeid,mAge;
    private Button btnRegister;


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private String append = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mContext = SignupActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);
        Log.d(TAG, "onCreate: started.");

        initWidgets();
        setupFirebaseAuth();
        init();
    }




    /**
     * Initialize the activity widgets
     */
    private void initWidgets(){
        Log.d(TAG, "initWidgets: Initializing Widgets.");
        mEmail = findViewById(R.id.input_email);
        mUsername = findViewById(R.id.input_name);
        btnRegister = findViewById(R.id.btn_signup);
        mPassword = findViewById(R.id.input_password);
        mRetypePassword = findViewById(R.id.input_reEnterPassword);
        mdatsmeid = findViewById(R.id.input_id);
//        mAge = findViewById(R.id.input_age);
        mContext = SignupActivity.this;

    }


    private void init(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                age = 0;
                username = mUsername.getText().toString();
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();
                retypePassword = mRetypePassword.getText().toString();
                datsmeid = mdatsmeid.getText().toString();




                if(checkInputs(email, username, password,retypePassword,datsmeid))
                {
                    if(firebaseMethods.registerNewEmail(email, password, username))
                    {
                        startActivity(new Intent(mContext,profileuser.class));
                    }
                }
            }
        });
    }

    private boolean checkInputs(String email, String username, String password, String retypePassword,String id){
        Log.d(TAG, "checkInputs: checking inputs for null values.");
        if(email.equals("") || username.equals("") || password.equals("") || retypePassword.equals("")  || id.equals("") || !password.equals(retypePassword)
                || email == null || username == null || password == null || retypePassword == null || id == null){
            Toast.makeText(mContext, "All fields must be filled out correctly.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: checking string if null.");

        if(string.equals("")){
            return true;
        }
        else{
            return false;
        }
    }

     /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase =FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //checking if username already taken

                            if(firebaseMethods.checkIfUsernameExists(username,dataSnapshot))
                            {
                                myRef.push().getKey().substring(3,10);
                                Log.d(TAG, "onDataChange: username exists, appending random string" + append);
                                username = username+append;
                            }
                            //String name, String DatsmeId, String email, int age, double lattitude, double longitude,String photourl
//                            firebaseMethods.addNewUser(username,"","",0,0,0,"");
                            Toast.makeText(mContext, "Signup Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...s
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}