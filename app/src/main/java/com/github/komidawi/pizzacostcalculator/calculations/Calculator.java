package com.github.komidawi.pizzacostcalculator.calculations;

import com.github.komidawi.pizzacostcalculator.pizza.PizzaShape;

public class Calculator {
    private static final int SQ_CENTIMETERS_IN_SQ_METRE = 10000;

    // Calculates price / area ratio in currency units per one square meter of pizza
    public static double calculateRatio(int diagonal, double price, PizzaShape pizzaShape) {
        double area = calculateArea(diagonal, pizzaShape);
        return (price / area) * SQ_CENTIMETERS_IN_SQ_METRE;
    }

    private static double calculateArea(int diagonal, PizzaShape pizzaShape) {
        switch (pizzaShape) {
            case CIRCLE:
                return calculateCircleArea(diagonal);

            case SQUARE:
                return calculateSquareArea(diagonal);

            default:
                throw new IllegalArgumentException("Provided pizza shape is incorrect");
        }
    }

    private static double calculateCircleArea(int diagonal) {
        return Math.PI * Math.pow(diagonal / 2, 2);
    }

    private static double calculateSquareArea(int diagonal) {
        return Math.pow(diagonal, 2) / 2;
    }
}

