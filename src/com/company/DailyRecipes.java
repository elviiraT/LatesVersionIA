package com.company;
import java.util.Arrays;
import java.util.LinkedList;

public class DailyRecipes
{
    public DailyRecipes(Day day)
    {
        this.day = day;
        recipes = new LinkedList<Recipe>();
        recipes.add(new Recipe("Click to select","","",""));
    }

    public Day getDay()
    {
        return day;
    }

    public LinkedList<Recipe> getRecipes()
    {
        return recipes;
    }

    public void addToList(Recipe r)
    {
        recipes.add(r);
    }

    public Recipe getRecipe(int i)
    {
        return recipes.get(i);
    }

    public void removeFromList(int i)
    {
        recipes.remove(i);
    }

    private Day day;
    private LinkedList<Recipe> recipes;
}
enum Day {Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday}
