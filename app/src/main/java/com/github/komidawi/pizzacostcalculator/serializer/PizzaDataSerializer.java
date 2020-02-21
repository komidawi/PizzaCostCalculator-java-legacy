package com.github.komidawi.pizzacostcalculator.serializer;

import android.app.Activity;
import android.util.Log;

import com.github.komidawi.pizzacostcalculator.pizza.PizzaModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PizzaDataSerializer {
    private static final String PIZZA_DATA_FILENAME = "savedPizzas.txt";
    private static final String TAG = PizzaDataSerializer.class.getSimpleName();

    private File pizzaData;
    private static Gson gson = new Gson();

    public PizzaDataSerializer(Activity activity) {
        pizzaData = new File(activity.getApplicationContext().getFilesDir(), PIZZA_DATA_FILENAME);
    }

    public void serializeData(List<PizzaModel> pizzas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pizzaData))) {
            for (PizzaModel pizza : pizzas) {
                writer.append(gson.toJson(pizza)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }

    public List<PizzaModel> deserializeData() {
        List<PizzaModel> pizzas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(pizzaData))) {
            for (String line; (line = reader.readLine()) != null; ) {
                PizzaModel pizza = gson.fromJson(line, PizzaModel.class);
                pizzas.add(pizza);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }

        return pizzas;
    }
}
