package edu.calvin.cs262.scrumptious;

/**
 * Created by tjluce on 11/6/15.
 *
 * Represents an Ingredient object.  Data for the object should be retrieved from the SQL database.
 * Contains the name and type of the ingredient.
 */
public class Ingredient {

    // Data
    private String name;
    private int id;
    private String type;

    // Constructor
    public Ingredient(String newName, int newID, String newType) {
        this.name = newName;
        this.id = newID;
        this.type = newType;
    }

    // Getters and Setters
    public String getName() { return name; }

    public int getId() {
        return id;
    }

    public String getType() { return type; }

    public void setName(String newName) { this.name = newName; }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String newType) { this.type = newType; }
}
