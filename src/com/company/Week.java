package com.company;

import java.util.LinkedList;

public class Week
{
    public Week(WeekType type)
    {
        this.type = type;
    }

    public DailyRecipes getDailyRecipe(int i)
    {
        DailyRecipes day =daysList[i];
        return day;
    }

    private WeekType type;
    private DailyRecipes[] daysList = {new DailyRecipes(Day.Monday), new DailyRecipes(Day.Tuesday), new DailyRecipes(Day.Wednesday),
            new DailyRecipes(Day.Thursday), new DailyRecipes(Day.Friday), new DailyRecipes(Day.Saturday), new DailyRecipes(Day.Sunday)};
}
enum WeekType {Past, Current, Next}

