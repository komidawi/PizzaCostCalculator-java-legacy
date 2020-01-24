package com.github.komidawi.pizzacostcalculator.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.komidawi.pizzacostcalculator.R;
import com.github.komidawi.pizzacostcalculator.pizza.PizzaModel;

import java.util.ArrayList;
import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaAdapterViewHolder> {
    private List<PizzaModel> addedPizzas = new ArrayList<>();

    @NonNull
    @Override
    public PizzaAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.pizza_model_list_item, parent, false);
        return new PizzaAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaAdapterViewHolder holder, int position) {
        PizzaModel pizza = addedPizzas.get(position);
        holder.bind(pizza);
    }

    @Override
    public int getItemCount() {
        return addedPizzas == null ? 0 : addedPizzas.size();
    }

    public void addPizza(PizzaModel pizza) {
        addedPizzas.add(pizza);
        notifyDataSetChanged();
    }

    class PizzaAdapterViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView diagonal;
        final TextView price;
        final TextView ratio;

        PizzaAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.pizza_model_item_name);
            diagonal = itemView.findViewById(R.id.pizza_model_item_diagonal);
            price = itemView.findViewById(R.id.pizza_model_item_price);
            ratio = itemView.findViewById(R.id.pizza_model_item_ratio);
        }

        void bind(PizzaModel pizza) {
            name.setText(pizza.getName());
            diagonal.setText(String.valueOf(pizza.getDiagonal()));
            price.setText(String.valueOf(pizza.getPrice()));
            ratio.setText(String.valueOf(pizza.getRatio()));
        }
    }
}
