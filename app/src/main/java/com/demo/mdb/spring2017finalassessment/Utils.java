package com.demo.mdb.spring2017finalassessment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by hp on 3/14/2017.
 */

public class Utils {
    public static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    /* TODO Part 5
     * implement getRandomPhrase on a thread pool of size 1. Use a callable to make a GET request on
     * this urlString: "https://api.whatdoestrumpthink.com/api/v1/quotes/random". You'll probably
     * need to actually go to the URL to see the JSON structure to know what String you want (don't
     * worry, it's a very simple JSON file.)
     *
     * convertStreamToString has been provided
     *
     * Note: if you can't remember how to use a Callable, you can get partial credit without one!
     */

    private class GetUrlContentTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String content = "", line;
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }
            return content;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {
            // this is executed on the main thread after the process is over
            // update your UI here
            displayMessage(result);
        }
    }

    static String getRandomPhrase() throws Exception {
        return GetUrlContentTask.execute("https://api.whatdoestrumpthink.com/api/v1/quotes/random");
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static void attemptLogin(final Context context, String email, String password) {
        if (!email.equals("") && !password.equals("")) {
            LoginActivity.mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Log in", "signInWithEmail:onComplete:" + task.isSuccessful());

                            if (!task.isSuccessful()) {
                                Log.w("Aww", "signInWithEmail:failed", task.getException());
                                Toast.makeText(context, "Sign in failed!", Toast.LENGTH_SHORT).show();
                            } else {
                                context.startActivity(new Intent(context, TabbedActivity.class));
                            }
                        }
                    });
        }
    }

    public static void attemptSignup(final Context context, String email, String password) {

        if (!email.equals("") && !password.equals("")) {
            RegisterActivity.mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity)context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Yay", "createUserWithEmail:onComplete:" + task.isSuccessful());

                            if (!task.isSuccessful()) {
                                Log.d("Yay", task.getException().getMessage());
                                Toast.makeText(context, "Failed Signup", Toast.LENGTH_SHORT).show();
                            } else {
                                context.startActivity(new Intent(context, TabbedActivity.class));
                            }
                        }
                    });
        }
    }
}
