package com.mandeep.officialcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.ProgressDialog.*;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private DatabaseReference mDataBase;
    @BindView(R.id.input_name)
    EditText _nameText;
    @BindView(R.id.input_id)
    EditText _IdText;
    @BindView(R.id.input_email)
    EditText _email;
    @BindView(R.id.input_age)
    EditText _age;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        progressDialog=new ProgressDialog(this);
        mDataBase= FirebaseDatabase.getInstance().getReference("USERS");
        firebaseAuth=FirebaseAuth.getInstance();
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }
    private void saveUserInformation()
    {
        String naam=_nameText.getText().toString().trim();
        String id=_IdText.getText().toString().trim();
        String mail=_email.getText().toString();
        String age=_age.getText().toString().trim();
        String password=_passwordText.getText().toString().trim();
        Userinformation userinformation=new Userinformation(naam,id,mail,age,password,0,0);
        mDataBase.child(id).setValue(userinformation);
//        progressDialog.setMessage("Registering user...");
//        progressDialog.show();
//        firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                progressDialog.dismiss();
//                if (task.isSuccessful()){
//                    FirebaseUser user = firebaseAuth.getCurrentUser(); //You Firebase user
//                    // user registered, start profile activity
//                    Toast.makeText(SignupActivity.this,"Account Created",Toast.LENGTH_LONG).show();
//
//                    finish();
//                    //startActivity(new Intent(getApplicationContext(), UserMainPage.class));
//                }
//                else{
//                    Toast.makeText(SignupActivity.this,"Could not create account. Please try again",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
    public void signup() {

        //Log.d(TAG, "Signup");

       //if (!validate()) {
         //   onSignupFailed();
           //return;
       // }
        //_signupButton.setEnabled(false);

        saveUserInformation();
//        _nameText.getText().clear();;
//        _IdText.getText().clear();
//        _email.getText().clear();;
//        _age.getText().clear();
//        _passwordText.getText().clear();
//        _reEnterPasswordText.getText().clear();
       Intent i=new Intent(SignupActivity.this,profileuser.class);
        i.putExtra("PersonName",  _nameText.getText().toString().trim());
        startActivity(i);




    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String id = _IdText.getText().toString();
        String email = _email.getText().toString();
        String age = _age.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (id.isEmpty()) {
            _IdText.setError("Enter Valid Address");
            valid = false;
        } else {
            _IdText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _email.setError("enter a valid email address");
            valid = false;
        } else {
            _email.setError(null);
        }

        if (age.isEmpty() || age.length()!=10) {
            _age.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _age.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}