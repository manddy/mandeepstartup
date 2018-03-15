package com.mandeep.officialcode.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mandeep.officialcode.MainActivity;
import com.mandeep.officialcode.R;


/**
 * Created by vrishankgupta on 13/03/18.
 */

/**
 * For all firebase Stuffs that I needed while handling Auth
 */

public class FirebaseMethods {

        private static final String TAG = "FirebaseMethods";

        //firebase
        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthListener;
        private FirebaseDatabase mFirebaseDatabase;
        private DatabaseReference myRef;
        private String userID;  //unoque Id, not Datsmeid
        private boolean sucess;

        private Context mContext;

        public FirebaseMethods(Context context) {
            mAuth = FirebaseAuth.getInstance();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = mFirebaseDatabase.getReference();
            mContext = context;

            if(mAuth.getCurrentUser() != null){
                userID = mAuth.getCurrentUser().getUid();
            }
        }

        public boolean checkIfUsernameExists(String username, DataSnapshot datasnapshot){
            Log.d(TAG, "checkIfUsernameExists: checking if " + username + " already exists.");

            User user = new User();

            for (DataSnapshot ds: datasnapshot.child(userID).getChildren()){
                Log.d(TAG, "checkIfUsernameExists: datasnapshot: " + ds);

                user.setName(ds.getValue(User.class).getName());
                Log.d(TAG, "checkIfUsernameExists: username: " + user.getName());

                if(StringManipulations.expandUsername(user.getName()).equals(username)){
                    Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + user.getName());
                    return true;
                }
            }
            return false;
        }

        /**
         * Register a new email and password to Firebase Authentication
         * @param email
         * @param password
         * @param username
         */

        public boolean registerNewEmail(final String email, String password, final String username){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(mContext, R.string.auth_failed,
                                        Toast.LENGTH_SHORT).show();
                                sucess = false;

                            }
                            else if(task.isSuccessful()){
                                userID = mAuth.getCurrentUser().getUid();
                                Log.d(TAG, "onComplete: Authstate changed: " + userID);
                                Toast.makeText(mContext,"Success",Toast.LENGTH_SHORT).show();
                                mContext.startActivity(new Intent(mContext, MainActivity.class));
                                sucess = true;

                            }

                        }
                    });

            return sucess;
        }



        public void addNewUser(String name, String DatsmeId, String email, int age, double lattitude, double longitude,String photourl){
            User user = new User( name,DatsmeId,email,age,lattitude,longitude,photourl );

            myRef.child(mContext.getString(R.string.dbname_users))
                    .child(userID)
                    .setValue(user);
        }

}

