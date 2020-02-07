package com.github.komidawi.pizzacostcalculator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.komidawi.pizzacostcalculator.recyclerview.PizzaAdapter;
import com.github.komidawi.pizzacostcalculator.listeners.AfterTextChangedListener;
import com.github.komidawi.pizzacostcalculator.pizza.PizzaModel;
import com.github.komidawi.pizzacostcalculator.pizza.PizzaShape;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import static androidx.recyclerview.widget.RecyclerView.LayoutManager;
import static com.github.komidawi.pizzacostcalculator.calculations.Calculator.calculateRatio;

public class MainActivity extends AppCompatActivity {
    private EditText diagonalInput;
    private EditText priceInput;
    private TextView ratioDisplay;
    private EditText nameInput;
    private PizzaAdapter pizzaAdapter;
    private TextWatcher afterTextChangedListener;
    private FileOutputStream savedPizzasOutputStream;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFileOutputStream();
        initializeAfterTextChangedListener();
        initializeViews();
    }

    private void handleAddPizzaButtonClick() {
        try {
            PizzaModel pizzaModel = createPizzaModel();
            pizzaAdapter.addPizza(pizzaModel);
            savedPizzasOutputStream.write((pizzaModel.toString() + "\n").getBytes());
            clearInputFields();
            diagonalInput.requestFocus();
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), R.string.fill_required_fields_message, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, R.string.data_saving_error, Toast.LENGTH_SHORT).show();
            Log.e(TAG, e.toString());
        }
    }

    private void initializeFileOutputStream() {
        try {
            File savedPizzas = new File(getApplicationContext().getFilesDir(), "savedPizzas.txt");
            savedPizzasOutputStream = new FileOutputStream(savedPizzas);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, R.string.data_reading_error, Toast.LENGTH_SHORT).show();
            Log.e(TAG, e.toString());
        }
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

    private PizzaModel createPizzaModel() {
        String name = nameInput.getText().toString();
        int diagonal = Integer.parseInt(diagonalInput.getText().toString());
        double price = Double.parseDouble(priceInput.getText().toString());
        double ratio = Double.parseDouble(ratioDisplay.getText().toString());

        return new PizzaModel(name, diagonal, price, ratio);
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
        nameInput = findViewById(R.id.pizza_name_input);
        initializeDiagonalInput();
        initializePriceInput();
        ratioDisplay = findViewById(R.id.ratio_display);
        initializeAddPizzaButton();
        initializeRecyclerView();
    }

    private void initializeDiagonalInput() {
        diagonalInput = findViewById(R.id.pizza_diagonal_input);
        diagonalInput.addTextChangedListener(afterTextChangedListener);
    }

    private void initializePriceInput() {
        priceInput = findViewById(R.id.pizza_price_input);
        priceInput.addTextChangedListener(afterTextChangedListener);
    }

    private void initializeAddPizzaButton() {
        Button addPizzaButton = findViewById(R.id.add_pizza_button);
        addPizzaButton.setOnClickListener(view -> handleAddPizzaButtonClick());
    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.added_pizza_models_view);
        recyclerView.setHasFixedSize(true);

        LayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        pizzaAdapter = new PizzaAdapter();
        recyclerView.setAdapter(pizzaAdapter);
    }

    private void clearInputFields() {
        nameInput.getText().clear();
        diagonalInput.getText().clear();
        priceInput.getText().clear();
    }
}
