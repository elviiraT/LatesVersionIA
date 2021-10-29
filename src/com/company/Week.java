package com.company;

import java.util.LinkedList;

public class Week
{
    public Week(WeekType type)
    {
        this.type = type;
    }

    public LinkedList<Recipe> getDailyRecipe(int i)
    {
        LinkedList<Recipe> day =daysList[i];
        return day;
    }

    public WeekType getWeekType()
    {
        return type;
    }

    private WeekType type;
    private LinkedList[] daysList = {new LinkedList<Recipe>(),new LinkedList<Recipe>(),new LinkedList<Recipe>(),new LinkedList<Recipe>(),new LinkedList<Recipe>(),new LinkedList<Recipe>(),new LinkedList<Recipe>()};
            //{new DailyRecipes(Day.Monday), new DailyRecipes(Day.Tuesday), new DailyRecipes(Day.Wednesday),
           // new DailyRecipes(Day.Thursday), new DailyRecipes(Day.Friday), new DailyRecipes(Day.Saturday), new DailyRecipes(Day.Sunday)};
}
enum WeekType {Past, Current, Next}

