package com.demo.mdb.spring2017finalassessment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /* TODO Part 2
        * Implement registration. If the imageView is clicked, set it to an image from the gallery
        * and store the image as a Uri instance variable (also change the imageView's image to this
        * Uri. If the create new user button is pressed, call createUser using the email and password
        * from the edittexts. Remember that it's email2 and password2 now!
        */
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("You have signed in!", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("You have signed out.", "onAuthStateChanged:signed_out");
                }
            }
        };
        ((Button) findViewById(R.id.createnewuser)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.imageView)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.createnewuser || id == R.id.imageView) {
            String name = ((EditText) findViewById(R.id.name)).getText().toString();
            String email = ((EditText) findViewById(R.id.email2)).getText().toString();
            String password = ((EditText) findViewById(R.id.password2)).getText().toString();
            String cpassword = ((EditText) findViewById(R.id.confirmpassword)).getText().toString();
            if (!password.equals(cpassword)){
                Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            }else if (id == R.id.createnewuser) {
                Utils.attemptSignup(RegisterActivity.this, email, password);
            } else if (id == R.id.imageView) {
                RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, TabbedActivity.class));

//                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//                final String key = ref.child("users").child("imageurl").push().getKey();
//                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//                StorageReference picref = storageRef.child(key + ".png");
//                picref.putFile(data.getData()).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(RegisterActivity.this, "Failure", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                        Set up and store image
//                    }
//                });

            }
        }
    }

    private void createUser(final String email, final String password) {
        /* TODO Part 2.1
         * This part's long, so listen up!
         * Create a user, and if it fails, display a Toast.
         *
         * If it works, we're going to add their image to the database. To do this, we will need a
         * unique user id to identify the user (push isn't the best answer here. Do some Googling!)
         *
         * Now, if THAT works (storing the image), set the name and photo uri of the user (hint: you
         * want to update a firebase user's profile.)
         *
         * Finally, if updating the user profile works, go to the TabbedActivity
         */

//        SEE UTILS FOR CREATING USER

    }

}
