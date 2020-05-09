package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_stats);

        Intent intent = getIntent();
        String stat = intent.getStringExtra(ListAdapter.EXTRA_TEXT);

        TextView textView = (TextView) findViewById(R.id.textView);

        textView.setText(stat);

    }
}
