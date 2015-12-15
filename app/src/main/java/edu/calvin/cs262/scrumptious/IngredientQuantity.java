package edu.calvin.cs262.scrumptious;

/**
 * Created by tjluce on 11/6/15.
 *
 * Represents the relation between an Ingredient and a Recipe.  Contains an Ingredient, and the unit
 * and quantity needed.  Should never be stored as a variable, just in an array in a Recipe object.
 * Data for the object should be retrieved from the SQL database.
 */
public class IngredientQuantity {

    // Data
    private Ingredient ingredient;
    private int id;
    private String unit;
    private double quantity;

    // Constructor
    public IngredientQuantity(Ingredient ingredient, int newID, String unit, float quantity) {
        this.ingredient = ingredient;
        this.id = newID;
        this.unit = unit;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
