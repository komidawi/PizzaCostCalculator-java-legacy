package com.github.komidawi.pizzacostcalculator.pizza;

import lombok.Data;

@Data
public class PizzaModel {
    private final int diagonal;
    private final double price;
    private final double ratio;
}
