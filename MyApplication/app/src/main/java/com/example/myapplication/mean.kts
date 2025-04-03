package com.example.threescreensapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge;
    private RadioGroup radioGroupGender;
    private Button btnSubmit, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnReset = findViewById(R.id.btnReset);

        btnSubmit.setOnClickListener(view -> {
        String name = editTextName.getText().toString();
        String age = editTextAge.getText().toString();
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();

        if (name.isEmpty() || age.isEmpty() || selectedGenderId == -1) {
            Toast.makeText(MainActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedGender = findViewById(selectedGenderId);
        String gender = selectedGender.getText().toString();

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("age", age);
        intent.putExtra("gender", gender);
        startActivity(intent);
    });

        btnReset.setOnClickListener(view -> {
        editTextName.setText("");
        editTextAge.setText("");
        radioGroupGender.clearCheck();
    });
    }
}
