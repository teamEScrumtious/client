package edu.calvin.cs262.scrumptious;

import java.util.Date;

/**
 * Created by tjluce on 11/10/15.
 * Models a dish (or a meal, if you prefer).
 */
public class Dish {

    // Data
    Recipe recipe = null;
    int servings;

    // Constructor
    public Dish(Recipe newRecipe, int newServings) {
        recipe = newRecipe;
        servings = newServings;
    }

    // Getter and Setters
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }
}
