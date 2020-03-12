package com.github.komidawi.pizzacostcalculator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.komidawi.pizzacostcalculator.listeners.AfterTextChangedListener;
import com.github.komidawi.pizzacostcalculator.pizza.PizzaModel;
import com.github.komidawi.pizzacostcalculator.pizza.PizzaShape;
import com.github.komidawi.pizzacostcalculator.recyclerview.PizzaAdapter;
import com.github.komidawi.pizzacostcalculator.serializer.PizzaDataSerializer;

import java.util.List;
import java.util.Locale;

import static androidx.recyclerview.widget.RecyclerView.LayoutManager;
import static com.github.komidawi.pizzacostcalculator.calculations.Calculator.calculateRatio;

public class MainActivity extends AppCompatActivity {
    private EditText diagonalInput;
    private EditText priceInput;
    private TextView ratioDisplay;
    private RadioGroup shapeInput;
    private EditText nameInput;
    private PizzaAdapter pizzaAdapter;
    private TextWatcher afterTextChangedListener;
    private PizzaDataSerializer serializer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serializer = new PizzaDataSerializer(this);
        pizzaAdapter = new PizzaAdapter();
        initializeAfterTextChangedListener();
        initializeViews();
        loadData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        serializer.serializeData(pizzaAdapter.getPizzas());
    }

    private void loadData() {
        List<PizzaModel> pizzas = serializer.deserializeData();
        pizzaAdapter.setPizzas(pizzas);
    }

    private void handleAddPizzaButtonClick() {
        try {
            PizzaModel pizzaModel = createPizzaModel();

            pizzaAdapter.addPizza(pizzaModel);

            clearInputFields();
            diagonalInput.requestFocus();
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), R.string.fill_required_fields_message, Toast.LENGTH_SHORT).show();
        }
    }

    private PizzaModel createPizzaModel() {
        String name = nameInput.getText().toString();
        int diagonal = Integer.parseInt(diagonalInput.getText().toString());
        double price = Double.parseDouble(priceInput.getText().toString());
        double ratio = Double.parseDouble(ratioDisplay.getText().toString());
        PizzaShape shape = getCurrentCheckedShape();

        return new PizzaModel(name, diagonal, price, ratio, shape);
    }

    private void initializeViews() {
        nameInput = findViewById(R.id.pizza_name_input);
        initializeDiagonalInput();
        initializePriceInput();
        ratioDisplay = findViewById(R.id.ratio_display);
        initializeShapeInput();
        initializeAddPizzaButton();
        initializeRecyclerView();
    }

    private void initializeShapeInput() {
        shapeInput = findViewById(R.id.pizza_shape_buttons_group);
        shapeInput.setOnCheckedChangeListener((group, checkedId) -> {
            handlePropertiesChanged();
        });
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
        recyclerView.setAdapter(pizzaAdapter);
    }

    private void initializeAfterTextChangedListener() {
        afterTextChangedListener = new AfterTextChangedListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                handlePropertiesChanged();
            }
        };
    }

    private void handlePropertiesChanged() {
        String diagonal = diagonalInput.getText().toString();
        String price = priceInput.getText().toString();
        PizzaShape shape = getCurrentCheckedShape();

        if (!diagonal.isEmpty() && !price.isEmpty()) {
            double ratio = calculateRatio(Integer.parseInt(diagonal), Double.parseDouble(price), shape);
            String formattedRatio = String.format(Locale.getDefault(), "%.0f", ratio);
            ratioDisplay.setText(formattedRatio);
        }
    }

    private PizzaShape getCurrentCheckedShape() {
        int checkedRadioButtonId = shapeInput.getCheckedRadioButtonId();
        RadioButton checkedButton = findViewById(checkedRadioButtonId);
        String shapeString = checkedButton.getText().toString().toUpperCase();
        return PizzaShape.valueOf(shapeString);
    }

    private void clearInputFields() {
        nameInput.getText().clear();
        diagonalInput.getText().clear();
        priceInput.getText().clear();
    }
}
