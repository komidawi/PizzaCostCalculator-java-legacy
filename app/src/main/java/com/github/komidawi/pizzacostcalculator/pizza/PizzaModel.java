package com.github.komidawi.pizzacostcalculator.pizza;

import lombok.Data;

@Data
public class PizzaModel {
    private final String name;
    private final int diagonal;
    private final double price;
    private final double ratio;
}
