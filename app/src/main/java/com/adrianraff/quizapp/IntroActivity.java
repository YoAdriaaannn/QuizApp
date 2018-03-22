package com.adrianraff.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class IntroActivity extends AppCompatActivity {


    /**
     *
     * Set up variables to be used app wide starting with user name, score, total right and total wrong aswers
     */

     EditText userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    public void startApp(){
        userName = findViewById(R.id.editText_user_name);
    }
}
