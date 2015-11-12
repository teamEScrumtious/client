package edu.calvin.cs262.scrumptious;

import android.app.Application;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by tjluce on 11/9/15.
 */
public class Scrumptious extends Application {
    // Create the global arrays of objects
    public ArrayList<Ingredient> arrayofIngredients = new ArrayList<Ingredient>();
    public ArrayList<Recipe> arrayOfRecipes = new ArrayList<Recipe>();
    public ArrayList<IngredientQuantity> immortalityIngredients = new ArrayList<IngredientQuantity>();
    public ArrayList<IngredientQuantity> polyjuiceIngredients = new ArrayList<IngredientQuantity>();
    public WeekPlan weekPlan  = new WeekPlan();

    public Scrumptious() {
        // Add to the array of ingredients
        arrayofIngredients.add(new Ingredient("Salt", "Spices"));
        arrayofIngredients.add(new Ingredient("Human Souls", "Spices"));
        arrayofIngredients.add(new Ingredient("Eye of Newt", "Meats"));
        arrayofIngredients.add(new Ingredient("Fluxweed", "Spices"));
        arrayofIngredients.add(new Ingredient("Knotgrass", "Produce"));
        arrayofIngredients.add(new Ingredient("Lacewing Flies", "Meats"));
        arrayofIngredients.add(new Ingredient("Leeches", "Meats"));
        arrayofIngredients.add(new Ingredient("Horn of Bicorn", "Dairy"));
        arrayofIngredients.add(new Ingredient("Boomslang Skin", "Meats"));
        arrayofIngredients.add(new Ingredient("Human Hair", "Spices"));
        arrayofIngredients.add(new Ingredient("Bezoar", "Spices"));
        arrayofIngredients.add(new Ingredient("Mistletoe Berries", "Spices"));
        arrayofIngredients.add(new Ingredient("Unicorn Horn", "Spices"));
        arrayofIngredients.add(new Ingredient("Standard Ingredient", "Spices"));

        // Add to the array of recipes
        immortalityIngredients.add(new IngredientQuantity(arrayofIngredients.get(0), "tbsp", 3));
        immortalityIngredients.add(new IngredientQuantity(arrayofIngredients.get(1), "", 3));
        immortalityIngredients.add(new IngredientQuantity(arrayofIngredients.get(2), "cup", 3));
        polyjuiceIngredients.add(new IngredientQuantity(arrayofIngredients.get(3), "measures", 3));
        polyjuiceIngredients.add(new IngredientQuantity(arrayofIngredients.get(4), "bundles", 2));
        polyjuiceIngredients.add(new IngredientQuantity(arrayofIngredients.get(5), "cups", 3));
        polyjuiceIngredients.add(new IngredientQuantity(arrayofIngredients.get(6), "", 4));
        arrayOfRecipes.add(new Recipe("Immortality", "Throw it into a pot add some black magic and boom... Literally.", immortalityIngredients, true, 2));
        arrayOfRecipes.add(new Recipe("Polyjuice Potion", "Polyjuice Potion', '1. Add the fluxweed to the cauldron 2. Add the knot grass 3. Stir 3 times clockwise 4. Wave your wand then let the potion brew for 80 minutes 5. Add the leeches, 6. Crush two cups of lacewing flies in a mortar then add, 7. Heat for 30 seconds on low heat.", polyjuiceIngredients, false, 3));

        // Create some test dishes
        Dish testDish1 = new Dish(arrayOfRecipes.get(0), 4);
        Dish testDish2 = new Dish(arrayOfRecipes.get(0), 2);
        Dish testDish3 = new Dish(arrayOfRecipes.get(0), 1);
        Dish testDish4 = new Dish(arrayOfRecipes.get(1), 8);
        Dish testDish5 = new Dish(arrayOfRecipes.get(1), 2);
        Dish testDish6 = new Dish(arrayOfRecipes.get(1), 5);

        // Create some test days
        Day testSunday = new Day(Calendar.NOVEMBER, 15, 2015);
        testSunday.setBreakfast(testDish1);
        testSunday.setLunch(testDish2);
        testSunday.setDinner(testDish3);

        Day testMonday = new Day(Calendar.NOVEMBER, 16, 2015);
        testMonday.setBreakfast(testDish4);
        testMonday.setLunch(testDish5);
        testMonday.setDinner(testDish6);

        Day testTuesday = new Day(Calendar.NOVEMBER, 17, 2015);
        testTuesday.setBreakfast(testDish1);
        testTuesday.setLunch(testDish3);
        testTuesday.setDinner(testDish5);

        Day testWednesday = new Day(Calendar.NOVEMBER, 18, 2015);
        testWednesday.setBreakfast(testDish2);
        testWednesday.setLunch(testDish4);
        testWednesday.setDinner(testDish6);

        Day testThursday = new Day(Calendar.NOVEMBER, 19, 2015);
        testThursday.setBreakfast(testDish1);
        testThursday.setLunch(testDish5);
        testThursday.setDinner(testDish1);

        Day testFriday = new Day(Calendar.NOVEMBER, 20, 2015);
        testFriday.setBreakfast(testDish4);
        testFriday.setLunch(testDish4);
        testFriday.setDinner(testDish4);

        Day testSaturday = new Day(Calendar.NOVEMBER, 21, 2015);
        testSaturday.setBreakfast(testDish5);
        testSaturday.setLunch(testDish3);
        testSaturday.setDinner(testDish2);

        // Create a test week plan
        weekPlan.setSunday(testSunday);
        weekPlan.setMonday(testMonday);
        weekPlan.setTuesday(testTuesday);
        weekPlan.setWednesday(testWednesday);
        weekPlan.setThursday(testThursday);
        weekPlan.setFriday(testFriday);
        weekPlan.setSaturday(testSaturday);

    }
}
