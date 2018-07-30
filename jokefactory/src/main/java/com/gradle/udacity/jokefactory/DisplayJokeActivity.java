package com.gradle.udacity.jokefactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        TextView textview = findViewById(R.id.joke_text);

        String JokeResult;

        Intent intent = getIntent();

        JokeResult = intent.getStringExtra(getString(R.string.joke_tag));

        if (JokeResult != null) {
            textview.setText(JokeResult);
        } else {
            textview.setText(getResources().getString(R.string.sad_day));
        }
    }
}
