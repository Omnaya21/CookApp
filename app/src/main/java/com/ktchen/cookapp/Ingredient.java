package com.ktchen.cookapp;

public class Ingredient {
    private String name;
    private float quantity;
    private String unit;

    public Ingredient( float quantity, String unit,String name) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Ingredient(String name){
        this.name=name;
        quantity=0;
        unit= " ";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String ToString() {
        String ingredient = quantity + unit + name;
        return ingredient;
    }
}
