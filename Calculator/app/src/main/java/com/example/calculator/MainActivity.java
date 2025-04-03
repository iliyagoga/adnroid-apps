package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText resultField;
    private String currentInput = "";
    private double tempValue = 0;
    private String currentOperator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultField = findViewById(R.id.resultField);

        setNumberButton(R.id.button0, "0");
        setNumberButton(R.id.button1, "1");
        setNumberButton(R.id.button2, "2");
        setNumberButton(R.id.button3, "3");
        setNumberButton(R.id.button4, "4");
        setNumberButton(R.id.button5, "5");
        setNumberButton(R.id.button6, "6");
        setNumberButton(R.id.button7, "7");
        setNumberButton(R.id.button8, "8");
        setNumberButton(R.id.button9, "9");

        setOperatorButton(R.id.buttonAdd, "+");
        setOperatorButton(R.id.buttonSubtract, "-");
        setOperatorButton(R.id.buttonMultiply, "*");
        setOperatorButton(R.id.buttonDivide, "/");

        findViewById(R.id.buttonDot).setOnClickListener(v -> appendDot());
        findViewById(R.id.buttonClear).setOnClickListener(v -> clear());
        findViewById(R.id.buttonEqual).setOnClickListener(v -> calculate());
        findViewById(R.id.buttonSquareRoot).setOnClickListener(v -> squareRoot());
        findViewById(R.id.buttonInverse).setOnClickListener(v -> inverse());
        findViewById(R.id.buttonSign).setOnClickListener(v -> changeSign());
    }

    private void setNumberButton(int buttonId, String value) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> appendNumber(value));
    }

    private void setOperatorButton(int buttonId, String operator) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> setOperator(operator));
    }

    private void appendNumber(String number) {
        currentInput += number;
        resultField.setText(currentInput);
    }

    private void appendDot() {
        if (!currentInput.contains(".")) {
            currentInput += ".";
            resultField.setText(currentInput);
        }
    }

    private void setOperator(String operator) {
        if (!currentInput.isEmpty()) {
            tempValue = Double.parseDouble(currentInput);
            currentInput = "";
            currentOperator = operator;
        }
    }

    private void calculate() {
        if (!currentInput.isEmpty()) {
            double currentNumber = Double.parseDouble(currentInput);
            double result = 0;

            switch (currentOperator) {
                case "+":
                    result = tempValue + currentNumber;
                    break;
                case "-":
                    result = tempValue - currentNumber;
                    break;
                case "*":
                    result = tempValue * currentNumber;
                    break;
                case "/":
                    if (currentNumber != 0) {
                        result = tempValue / currentNumber;
                    } else {
                        Toast.makeText(this, "Ошибка: деление на ноль", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
            }

            resultField.setText(String.valueOf(result));
            currentInput = String.valueOf(result);
            currentOperator = "";
        }
    }

    private void clear() {
        currentInput = "";
        tempValue = 0;
        currentOperator = "";
        resultField.setText("0");
    }

    private void squareRoot() {
        if (!currentInput.isEmpty()) {
            double value = Double.parseDouble(currentInput);
            resultField.setText(String.valueOf(Math.sqrt(value)));
        }
    }

    private void inverse() {
        if (!currentInput.isEmpty()) {
            double value = Double.parseDouble(currentInput);
            if (value != 0) {
                resultField.setText(String.valueOf(1 / value));
            } else {
                Toast.makeText(this, "Ошибка: деление на ноль", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void changeSign() {
        if (!currentInput.isEmpty()) {
            double value = Double.parseDouble(currentInput);
            resultField.setText(String.valueOf(value * -1));
            currentInput = String.valueOf(value * -1);
        }
    }
}
