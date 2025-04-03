package com.example.convertation;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputValue;
    private Button convertButton;
    private TextView resultText1;
    private TextView resultText2;
    private TextView resultText3;
    private TextView resultText4;
    private TextView resultText5;
    private TextView resultText6;
    private TextView resultInt1;
    private TextView resultInt2;
    private TextView resultInt3;
    private TextView resultInt4;
    private TextView resultInt5;
    private TextView resultInt6;
    private RadioGroup unitFromGroup, unitToGroup;
    private Spinner languageSpinner;

    private TextView languageTitle;

    private boolean isEnglish = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = findViewById(R.id.input_value);
        convertButton = findViewById(R.id.convert_button);
        resultText1 = findViewById(R.id.result_text_1);
        resultText2 = findViewById(R.id.result_text_2);
        resultText3 = findViewById(R.id.result_text_3);
        resultText4 = findViewById(R.id.result_text_4);
        resultText5 = findViewById(R.id.result_text_5);
        resultText6 = findViewById(R.id.result_text_6);
        resultInt1 = findViewById(R.id.result_int_1);
        resultInt2 = findViewById(R.id.result_int_2);
        resultInt3 = findViewById(R.id.result_int_3);
        resultInt4 = findViewById(R.id.result_int_4);
        resultInt5 = findViewById(R.id.result_int_5);
        resultInt6 = findViewById(R.id.result_int_6);
        unitFromGroup = findViewById(R.id.unit_from_group);
        unitToGroup = findViewById(R.id.unit_to_group);
        languageSpinner = findViewById(R.id.language_spinner);

        languageTitle = findViewById(R.id.language_name);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0) {
                    isEnglish = true;
                } else {
                    isEnglish = false;
                }
                updateUI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        convertButton.setOnClickListener(v -> {
            double input=0;
            if(inputValue.getText().length()>0){
                input = Double.parseDouble(inputValue.getText().toString());
            }
            int fromId = unitFromGroup.getCheckedRadioButtonId();
            int toId = unitToGroup.getCheckedRadioButtonId();
            int fromIndex = getSelectedUnitIndex(fromId);
            int toIndex = getSelectedUnitIndex(toId);


            clear();
            if (fromIndex == 0) {
                resultInt1.setText("" + input);
            }
            if (fromIndex == 1) {
                resultInt2.setText("" + input);
            }
            if (fromIndex == 2) {
                resultInt3.setText("" + input);
            }
            if (toIndex == 3) {
                resultInt4.setText("" + convert(input));
            }
            if (toIndex == 4) {
                resultInt5.setText("" + convert(input));
            }
            if (toIndex == 5) {
                resultInt6.setText("" + convert(input));
            }

        });
    }

    private double convert(double input) {
        double result = 0;
        int fromId = unitFromGroup.getCheckedRadioButtonId();
        int toId = unitToGroup.getCheckedRadioButtonId();

        double[][] conversionRates = {
                {1, 100, 0.001, 0.000621371, 3.28084, 39.3701},
                {1000, 1, 100000, 0.621371, 3280.84, 39370.1},
                {0.01, 0.00001, 1, 0.00000621371, 0.0328084, 0.393701},
                {1609.34, 1.60934, 160934, 1, 5280, 63360},
                {0.3048, 0.0003048, 30.48, 0.000189394, 1, 12},
                {0.0254, 0.0000254, 2.54, 0.000015783, 0.0833333, 1}
        };

        int fromIndex = getSelectedUnitIndex(fromId);
        int toIndex = getSelectedUnitIndex(toId);

        if (fromIndex == -1 || toIndex == -1) {

            return 0;
        }

        result = input * conversionRates[fromIndex][toIndex];
        return result;
    }

    private int getSelectedUnitIndex(int radioButtonId) {
        if (radioButtonId == R.id.radio_meters) {
            return 0;
        } else if (radioButtonId == R.id.radio_kilometers) {
            return 1;
        } else if (radioButtonId == R.id.radio_centimeters) {
            return 2;
        } else if (radioButtonId == R.id.radio_miles) {
            return 3;
        } else if (radioButtonId == R.id.radio_feet) {
            return 4;
        } else if (radioButtonId == R.id.radio_inches) {
            return 5;
        } else {
            return -1;
        }
    }

    private void updateUI() {
        String[]  convert = getResources().getStringArray(R.array.convert) ;
        String[]  lang = getResources().getStringArray(R.array.language_name) ;
        String[] units = isEnglish ? getResources().getStringArray(R.array.units) : getResources().getStringArray(R.array.units_ru);

        languageTitle.setText(isEnglish ? lang[0] : lang[1]);
        ((RadioButton) findViewById(R.id.radio_meters)).setText(units[0]);
        ((RadioButton) findViewById(R.id.radio_kilometers)).setText(units[1]);
        ((RadioButton) findViewById(R.id.radio_centimeters)).setText(units[2]);
        ((RadioButton) findViewById(R.id.radio_miles)).setText(units[3]);
        ((RadioButton) findViewById(R.id.radio_feet)).setText(units[4]);
        ((RadioButton) findViewById(R.id.radio_inches)).setText(units[5]);

        convertButton.setText(isEnglish ? convert[0] : convert[1]);

        resultText1.setText(units[0]);
        resultText2.setText(units[1]);
        resultText3.setText(units[2]);
        resultText4.setText(units[3]);
        resultText5.setText(units[4]);
        resultText6.setText(units[5]);

    }

    private void clear(){
        resultInt1.setText(""+0);
        resultInt2.setText(""+0);
        resultInt3.setText(""+0);
        resultInt4.setText(""+0);
        resultInt5.setText(""+0);
        resultInt6.setText(""+0);
    }
}
