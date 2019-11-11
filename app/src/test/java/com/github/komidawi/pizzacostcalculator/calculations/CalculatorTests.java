package com.github.komidawi.pizzacostcalculator.calculations;

import org.junit.Test;

import static com.github.komidawi.pizzacostcalculator.calculations.Calculator.calculateRatio;
import static com.github.komidawi.pizzacostcalculator.pizza.PizzaShape.CIRCLE;
import static com.github.komidawi.pizzacostcalculator.pizza.PizzaShape.SQUARE;
import static org.junit.Assert.assertEquals;

public class CalculatorTests {

    @Test
    public void test_42dia_20price_circle_ratioCalculation() {
        assertEquals(0.014435822502666245, calculateRatio(42, 20, CIRCLE), 0.01);
    }

    @Test
    public void test_42dia_20val_square_ratioCalculation() {
        assertEquals(0.022675736961451247, calculateRatio(42, 20, SQUARE), 0.01);
    }
}
