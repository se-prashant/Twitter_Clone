package com.back4app.quickstartexampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    public void redirectUser(){
        if(ParseUser.getCurrentUser()!=null){
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
            startActivity(intent);
        }
    }
    public  void signUpLogIn(View view){
        final EditText usernameEditText = (EditText) findViewById(R.id.usrnameEditText);
        final EditText passEditText = (EditText) findViewById(R.id.passwordEditText);

        ParseUser.logInInBackground(usernameEditText.getText().toString(), passEditText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e==null){
                    Log.i("Login","Success!");
                    redirectUser();;
                }else {
                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(usernameEditText.getText().toString());
                    newUser.setPassword(passEditText.getText().toString());
                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null){
                                Log.i("Login","SignUp Success!");
                                redirectUser();
                            }else {
                                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Twitter: Login");
        redirectUser();
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }

}
