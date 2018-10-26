package com.demo.mdb.spring2017finalassessment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /* TODO Part 1
         * Implement login. When the login button is pressed, attempt to login the user, and if
         * that is successful, go to the TabbedActivity. If the register button is pressed, go to
         * the RegisterActivity. Check the layout files for the IDs of the views used in this part
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


        ((Button) findViewById(R.id.login)).setOnClickListener(this);


        ((Button) findViewById(R.id.register)).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login || id == R.id.register) {
            String email = ((EditText) findViewById(R.id.email)).getText().toString();
            String password = ((EditText) findViewById(R.id.password)).getText().toString();
            if (id == R.id.login) {
                Utils.attemptLogin(LoginActivity.this, email, password);
            } else {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, TabbedActivity.class));
            }
        }
    }

}
