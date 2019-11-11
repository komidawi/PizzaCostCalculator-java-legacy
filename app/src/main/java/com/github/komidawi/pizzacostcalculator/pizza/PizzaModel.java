package com.github.komidawi.pizzacostcalculator.pizza;

public class PizzaModel {
    private int diagonal;
    private double price;
    private double ratio;

    public int getDiagonal() {
        return diagonal;
    }

    public double getPrice() {
        return price;
    }

    public double getRatio() {
        return ratio;
    }

    public void setDiagonal(int diagonal) {
        this.diagonal = diagonal;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
