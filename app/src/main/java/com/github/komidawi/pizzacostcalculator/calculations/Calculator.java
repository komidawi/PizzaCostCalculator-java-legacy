package com.github.komidawi.pizzacostcalculator.calculations;

import com.github.komidawi.pizzacostcalculator.pizza.PizzaShape;

public class Calculator {
    public static double calculateRatio(double diagonal, double price, PizzaShape pizzaShape) {
        double area = calculateArea(diagonal, pizzaShape);
        return price / area;
    }

    private static double calculateArea(double diagonal, PizzaShape pizzaShape) {
        switch (pizzaShape) {
            case CIRCLE:
                return calculateCircleArea(diagonal);

            case SQUARE:
                return calculateSquareArea(diagonal);

            default:
                throw new IllegalArgumentException("Provided pizza shape is incorrect");
        }
    }

    private static double calculateCircleArea(double diagonal) {
        return Math.PI * Math.pow(diagonal / 2, 2);
    }

    private static double calculateSquareArea(double diagonal) {
        return Math.pow(diagonal, 2) / 2;
    }
}

