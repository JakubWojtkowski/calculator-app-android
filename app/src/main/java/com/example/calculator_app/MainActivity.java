package com.example.calculator_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result, solution;
    MaterialButton buttonAC, buttonModulo, buttonDot, buttonChangeSign;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        solution = findViewById(R.id.solution);

        assignId(buttonAC, R.id.button_ac);
        assignId(buttonModulo, R.id.button_modulo);
        assignId(buttonChangeSign, R.id.button_changeSign);
        assignId(buttonDot, R.id.button_dot);
        assignId(buttonDivide, R.id.button_divide);
        assignId(buttonMultiply, R.id.button_multiply);
        assignId(buttonPlus, R.id.button_add);
        assignId(buttonMinus, R.id.button_substract);
        assignId(buttonEquals, R.id.button_sum);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);


    }

    private void assignId(MaterialButton button, int buttonId) {
        button = findViewById(buttonId);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solution.getText().toString();

        if(buttonText.equals("AC")) {
            solution.setText("");
            result.setText("0");
            return;
        }

        if(buttonText.equals("=")) {
            solution.setText(result.getText());
//            solution.setText("");
            return;
        } else {
            dataToCalculate = dataToCalculate+buttonText;
        }

        solution.setText(dataToCalculate);

        String finalResult = calculateData(dataToCalculate);

        if(!finalResult.equals("Error")) {
            result.setText(finalResult);
        }
    }

    String calculateData(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();

            double resultValue = Double.parseDouble(finalResult);
            finalResult = String.format("%.2f", resultValue);

            // np. 3+3 = 6.00  -> .00 jest nam niepotrzebne
            if(finalResult.endsWith(".00")) {
                finalResult = finalResult.replace(".00", "");
            }

            return finalResult;
        } catch (Exception e) {
            return "Error";
        }
    }
}