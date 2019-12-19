package com.github.komidawi.pizzacostcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import com.github.komidawi.pizzacostcalculator.listeners.AfterTextChangedListener;
import com.github.komidawi.pizzacostcalculator.pizza.PizzaShape;

import java.util.Locale;

import static com.github.komidawi.pizzacostcalculator.calculations.Calculator.calculateRatio;

public class MainActivity extends AppCompatActivity {
    private EditText diagonalInput;
    private EditText priceInput;
    private TextView ratioDisplay;
    private AfterTextChangedListener afterTextChangedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeAfterTextChangedListener();
        initializeViews();
    }

    private void initializeAfterTextChangedListener() {
        afterTextChangedListener = new AfterTextChangedListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                handlePropertiesChanged();
            }
        };
    }

    private void initializeViews() {
        initializeDiagonalInput();
        initializePriceInput();
        ratioDisplay = findViewById(R.id.ratio_display);
    }

    private void initializeDiagonalInput() {
        diagonalInput = findViewById(R.id.diagonal_input);
        diagonalInput.addTextChangedListener(afterTextChangedListener);
    }

    private void initializePriceInput() {
        priceInput = findViewById(R.id.price_input);
        priceInput.addTextChangedListener(afterTextChangedListener);
    }

    private void handlePropertiesChanged() {
        String diagonal = diagonalInput.getText().toString();
        String price = priceInput.getText().toString();

        if (!diagonal.isEmpty() && !price.isEmpty()) {
            double ratio = calculateRatio(Integer.parseInt(diagonal), Double.parseDouble(price), PizzaShape.CIRCLE);
            String formattedRatio = String.format(Locale.getDefault(), "%.0f", ratio);
            ratioDisplay.setText(formattedRatio);
        }
    }
}
