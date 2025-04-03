package com.example.windowapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    private LinearLayout layoutSecond;
    private TextView textViewName, textViewAge, textViewGender;
    private Button btnChangeBackground, btnGoToNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        layoutSecond = findViewById(R.id.layoutSecond);
        textViewName = findViewById(R.id.textViewName);
        textViewAge = findViewById(R.id.textViewAge);
        textViewGender = findViewById(R.id.textViewGender);
        btnChangeBackground = findViewById(R.id.btnChangeBackground);
        btnGoToNext = findViewById(R.id.btnGoToNext);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String age = intent.getStringExtra("age");
        String gender = intent.getStringExtra("gender");

        textViewName.setText("Ваше имя - " + name);
        textViewAge.setText("Ваш возраст - " + age);
        textViewGender.setText("Ваш пол - " + gender);
        btnChangeBackground.setOnClickListener(view -> {
            Random random = new Random();
            int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            layoutSecond.setBackgroundColor(color);
        });
        btnGoToNext.setOnClickListener(view -> {
            Intent nextIntent = new Intent(SecondActivity.this, ThirdActivity.class);
            startActivity(nextIntent);
        });
    }
}