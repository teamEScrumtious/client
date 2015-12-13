package edu.calvin.cs262.scrumptious;

import java.util.ArrayList;

/**
 * Created by tjluce on 11/4/15.
 * Represents a Recipe object.  Data for the object should be retrieved from the SQL database.
 * Contains a name, a list of ingredients, cooking instructions, serving size, and whether or not
 * it is bookmarked.
 *
 * Based on tutorial at http://www.softwarepassion.com/android-series-custom-listview-items-and-adapters/
 */
public class Recipe {

    // Data
    private String recipeName;
    private int id;
    //private String recipeType;
    private ArrayList<IngredientQuantity> ingredients;
    private String instructions;
    private boolean bookmarked;
    private int servings;
    //private ArrayList<String> notes = new ArrayList<String>();
    private String note;

    // Constructor
    public Recipe(String newRecipeName, int newID, String newInstructions, ArrayList<IngredientQuantity> newIngredients, boolean newBookmarked, int newServings, String newNote) {
        this.recipeName = newRecipeName;
        this.id = newID;
        //this.recipeType = newRecipeType;
        this.instructions = newInstructions;
        this.ingredients = newIngredients;
        this.bookmarked = newBookmarked;
        this.servings = newServings;
        this.note = newNote;

    }

    // Add note
//    public void addNote(String note) {
//        notes.add(note);
//    }

    // Getters
    public String getName() {
        return recipeName;
    }

    public int getId() { return id; }

//    public String getRecipeType() {
//        return recipeType;
//    }

    public String getInstructions() {
        return instructions;
    }

    public ArrayList<IngredientQuantity> getIngredients() {
        return ingredients;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public int getServings() {
        return servings;
    }

//    public ArrayList<String> getNotes() {
//        return notes;
//    }

    public String getNote() { return note; }

    // Setters
    public void setName(String newRecipeName) {
        this.recipeName = newRecipeName;
    }

    public void setId(int newID) { this.id = newID; }

//    public void setRecipeType(String newRecipeType) {
//        this.recipeType = newRecipeType;
//    }

    public void setInstructions(String newInstructions) {
        this.instructions = newInstructions;
    }

    public void setIngredients(ArrayList<IngredientQuantity> ingredients) {
        this.ingredients = ingredients;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setNote(String newNote) { this.note = newNote; }
}