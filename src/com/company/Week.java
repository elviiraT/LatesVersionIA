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
        this.type = type; //defines whether it is the past, current or next week
        weekNumber = definingWeekNumber(type);
    }

    private int definingWeekNumber(WeekType w)
    // method that defines the week number
    {
        Calendar now = GregorianCalendar.getInstance();
        int number = now.get(Calendar.WEEK_OF_YEAR); //the current week number is gotten from the Gregorian calendar
        // checks what is the weekType of the week, if it is past 1 is subtracted from the number
        // if it is the next week 1 is added to the number
        if (w.equals(WeekType.Past))
            return number-1;
        else if (w.equals(WeekType.Current))
            return number;
        else
            return number+1;

    }

    public LinkedList<Recipe> getDailyRecipe(int i)
    // accessor to get the linked list of recipes of a day
    {
        LinkedList<Recipe> day = dailyRecipes[i];
        return day;
    }

    public WeekType getWeekType()
    // changes the weekType when the next week becomes current or the current week past
    {
        return type;
    }

    public void changeWeekType(WeekType newType)
    {
        this.type = newType;
    }

    public int getWeekNumber()
    {
        return weekNumber;
    }

    private int weekNumber;
    private WeekType type;
    private LinkedList[] dailyRecipes = {new LinkedList<Recipe>(), new LinkedList<Recipe>(), new LinkedList<Recipe>(),
            new LinkedList<Recipe>(), new LinkedList<Recipe>(), new LinkedList<Recipe>(), new LinkedList<Recipe>()};
    // Each week object contains an array of linked lists that have the variable name daysList, this list contains a linked list
    // of Recipes for each day of the week, these linked lists contain the recipes that have been added to the day in question in the calendar
}
enum WeekType {Past, Current, Next}
// the types of the weeks can only be past, current and next as such an enum class is created predefining these types

