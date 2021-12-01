package com.company;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class Week implements Serializable
{
    public Week(WeekType type)
    {
        this.type = type;
        weekNumber = definingWeekNumber(type);
    }

    private int definingWeekNumber(WeekType w)
    // method that defines the week number
    {
        Calendar now = GregorianCalendar.getInstance();
        int number = now.get(Calendar.WEEK_OF_YEAR);
        if (w.equals(WeekType.Past))
            return number-1;
        else if (w.equals(WeekType.Current))
            return number;
        else
            return number+1;

    }
    public LinkedList<Recipe> getDailyRecipe(int i)
    {
        LinkedList<Recipe> day = daysList[i];
        return day;
    }

    public WeekType getWeekType()
    {
        return type;
    }

    public int getWeekNumber()
    {
        return weekNumber;
    }

    private int weekNumber;
    private WeekType type;
    private LinkedList[] daysList = {new LinkedList<Recipe>(), new LinkedList<Recipe>(), new LinkedList<Recipe>(),
            new LinkedList<Recipe>(), new LinkedList<Recipe>(), new LinkedList<Recipe>(), new LinkedList<Recipe>()};
    // Each week object contains an array of linked lists that have the variable name daysList, this list contains a linked list
    // of Recipes for each day of the week, these linked lists contain the recipes that have been added to the day in question
}
enum WeekType {Past, Current, Next}
// the types of the weeks can only be past, current and next as such an enum class is created predefining these types

