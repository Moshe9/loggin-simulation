package com.example.cut2016.loginsimulation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    TextView welcomeText;

    Register userRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome_activity);

        welcomeText = (TextView)findViewById(R.id.welcomeTxtVw);

        if (getIntent().getSerializableExtra("userRecord") != null)
        {
            userRecord = (Register) getIntent().getSerializableExtra("userRecord");
        }

        welcomeText.setText(getString(R.string.welcome_message, userRecord.getFullName()));
    }

    @Override
    public void onBackPressed() {
        //Execute your code here
        Intent intent = new Intent();

        intent.putExtra("userRecord", userRecord);

        setResult(Activity.RESULT_OK, intent);

        this.finish();
    }
}
