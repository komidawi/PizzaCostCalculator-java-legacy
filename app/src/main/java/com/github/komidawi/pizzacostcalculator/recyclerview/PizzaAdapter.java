package com.github.komidawi.pizzacostcalculator.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.komidawi.pizzacostcalculator.R;
import com.github.komidawi.pizzacostcalculator.pizza.PizzaModel;

import java.util.ArrayList;
import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaAdapterViewHolder> {
    private List<PizzaModel> pizzas = new ArrayList<>();

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
        PizzaModel pizza = pizzas.get(position);
        setRemoveButtonOnClickListener(holder);
        holder.bind(pizza);
    }

    private void setRemoveButtonOnClickListener(@NonNull PizzaAdapterViewHolder holder) {
        holder.removePizza.setOnClickListener(view -> {
            int newPosition = holder.getAdapterPosition();
            pizzas.remove(newPosition);
            notifyItemRemoved(newPosition);
            notifyItemRangeChanged(newPosition, pizzas.size());
        });
    }

    public void addPizza(PizzaModel pizza) {
        pizzas.add(pizza);
        notifyDataSetChanged();
    }

    public void setPizzas(List<PizzaModel> pizzas) {
        this.pizzas = pizzas;
        notifyDataSetChanged();
    }

    public List<PizzaModel> getPizzas() {
        return pizzas;
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    class PizzaAdapterViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView diagonal;
        final TextView price;
        final TextView ratio;
        final TextView shape;
        final ImageButton removePizza;

        PizzaAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.pizza_model_item_name);
            diagonal = itemView.findViewById(R.id.pizza_model_item_diagonal);
            price = itemView.findViewById(R.id.pizza_model_item_price);
            ratio = itemView.findViewById(R.id.pizza_model_item_ratio);
            removePizza = itemView.findViewById(R.id.pizza_model_remove);
            shape = itemView.findViewById(R.id.pizza_model_item_shape);
        }

        void bind(PizzaModel pizza) {
            name.setText(pizza.getName());
            diagonal.setText(String.valueOf(pizza.getDiagonal()));
            price.setText(String.valueOf(pizza.getPrice()));
            ratio.setText(String.valueOf(pizza.getRatio()));
            shape.setText(pizza.getShape().toString());
        }
    }
}
