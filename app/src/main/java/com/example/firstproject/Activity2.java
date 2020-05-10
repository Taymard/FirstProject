package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextDirectionHeuristic;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_stats);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String type = intent.getStringExtra("type");
        String weakness = intent.getStringExtra("weaknesses");
        String height = intent.getStringExtra("height");
        String weigth = intent.getStringExtra("weight");
        String prev = intent.getStringExtra("prev");
        String next = intent.getStringExtra("next");
        String img = intent.getStringExtra("img");

        TextView Name = (TextView) findViewById(R.id.textView1);
        TextView Type = (TextView) findViewById(R.id.textView2);
        TextView Weakness = (TextView) findViewById(R.id.textView3);
        TextView Height = (TextView) findViewById(R.id.textView4);
        TextView Weigth = (TextView) findViewById(R.id.textView5);
        TextView Prev = (TextView) findViewById(R.id.textView6);
        TextView Next = (TextView) findViewById(R.id.textView7);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Name.setText("Name : " + name);
        Type.setText("Type : "+ type);
        Weakness.setText("Weak to : "+ weakness);
        Height.setText("Height : " + height);
        Weigth.setText("Weight : " + weigth);
        Prev.setText("Previous Evolution : " + prev);
        Next.setText("Next Evolution : " + next);
        Glide.with(this).load(img).into(imageView);


    }
}
