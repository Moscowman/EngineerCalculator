package ru.varasoft.engineercalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private ExpressionTree<String> expressionTree = new ExpressionTree<String>(255);
    private ExpressionDrawer expressionDrawer = new ExpressionDrawer();

    private final View.OnClickListener digitsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            expressionTree.addString(button.getText().toString());
        }
    };

    private final View.OnClickListener functionButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            expressionTree.addString(String.format("_%s", button.getText().toString()));
        }
    };

    private final View.OnClickListener buttonACListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expressionTree.clearFormula();
        }
    };

    private final View.OnClickListener buttonPointListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expressionTree.tryToPlacePoint();
        }
    };

    private final View.OnClickListener buttonPlusMinusListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expressionTree.tryToInvertSign();
        }
    };

    private final View.OnClickListener buttonPiListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expressionTree.addString("_pi");
        }
    };

    private final View.OnClickListener buttonSqrtListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expressionTree.addString("_sqrt");
        }
    };

    private final View.OnClickListener buttonBackspaceListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expressionTree.backspace();
        }
    };

    private final View.OnClickListener buttonResultListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expressionTree.calculate();

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDigitsButtons();
        initFunctionButtons();
        initOtherButtons();
    }
    private void initDigitsButtons() {
        Button button0 = findViewById(R.id.button_0);
        button0.setOnClickListener(digitsListener);
        Button button1 = findViewById(R.id.button_1);
        button1.setOnClickListener(digitsListener);
        Button button2 = findViewById(R.id.button_2);
        button2.setOnClickListener(digitsListener);
        Button button3 = findViewById(R.id.button_3);
        button3.setOnClickListener(digitsListener);
        Button button4 = findViewById(R.id.button_4);
        button4.setOnClickListener(digitsListener);
        Button button5 = findViewById(R.id.button_5);
        button5.setOnClickListener(digitsListener);
        Button button6 = findViewById(R.id.button_6);
        button6.setOnClickListener(digitsListener);
        Button button7 = findViewById(R.id.button_7);
        button7.setOnClickListener(digitsListener);
        Button button8 = findViewById(R.id.button_8);
        button8.setOnClickListener(digitsListener);
        Button button9 = findViewById(R.id.button_9);
        button9.setOnClickListener(digitsListener);
        Button button_plus = findViewById(R.id.button_plus);
        button_plus.setOnClickListener(digitsListener);
        Button button_minus = findViewById(R.id.button_minus);
        button_minus.setOnClickListener(digitsListener);
        Button button_multiply = findViewById(R.id.button_multiply);
        button_multiply.setOnClickListener(digitsListener);
        Button button_divide = findViewById(R.id.button_divide);
        button_divide.setOnClickListener(digitsListener);
        Button button_left_bracket = findViewById(R.id.button_left_bracket);
        button_left_bracket.setOnClickListener(digitsListener);
        Button button_right_bracket = findViewById(R.id.button_right_bracket);
        button_right_bracket.setOnClickListener(digitsListener);
    }

    private void initFunctionButtons() {
        Button buttonSin = findViewById(R.id.button_sin);
        buttonSin.setOnClickListener(functionButtonsListener);
        Button buttonCos = findViewById(R.id.button_cos);
        buttonCos.setOnClickListener(functionButtonsListener);
        Button buttonTan = findViewById(R.id.button_tan);
        buttonTan.setOnClickListener(functionButtonsListener);
        Button button_e = findViewById(R.id.button_e);
        button_e.setOnClickListener(digitsListener);
    }

    private void initOtherButtons() {
        Button buttonAC = findViewById(R.id.button_ac);
        buttonAC.setOnClickListener(buttonACListener);
        Button buttonPoint = findViewById(R.id.button_point);
        buttonPoint.setOnClickListener(buttonPointListener);
        Button buttonPlusMinus = findViewById(R.id.button_plus_minus);
        buttonPlusMinus.setOnClickListener(buttonPlusMinusListener);
        Button buttonPi = findViewById(R.id.button_pi);
        buttonPi.setOnClickListener(buttonPiListener);
        Button buttonSqrt = findViewById(R.id.button_sqrt);
        buttonSqrt.setOnClickListener(buttonSqrtListener);
        Button buttonResult = findViewById(R.id.button_result);
        buttonResult.setOnClickListener(buttonResultListener);
        Button buttonBackspace = findViewById(R.id.button_backspace);
        buttonBackspace.setOnClickListener(buttonBackspaceListener);

    }
}