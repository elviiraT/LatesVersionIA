package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.util.LinkedList;

public class Serialization
{
    static String weeksFile = "weeks.ser";
    static String pastData = "pastData.ser";
    static String allRecipes = "allRecipes.ser";


    public static void SaveWeeks(Week[] weeks)
    {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(weeksFile)))
        {
            out.writeObject(weeks);
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public static Week[] ReadWeeks()
    {
        Week[] weeks = null;
        File f = new File(weeksFile);
        if(!f.exists())
        {
            weeks = new Week[] {new Week(WeekType.Past), new Week(WeekType.Current), new Week(WeekType.Next)};
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(weeksFile)))
        {
            weeks = (Week[]) in.readObject();

        } catch (Exception e)
        {
            System.out.println(e);
        }
        return weeks;
    }


    public static void SaveRecipesList(LinkedList<Recipe> list, String file)
    {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file)))
        {
            out.writeObject(list);
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public static LinkedList<Recipe> ReadRecipesList(String file)
    {
        LinkedList<Recipe> list = null;
        File f = new File(file);
        if(!f.exists())
        {
            list = new LinkedList<Recipe>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file)))
        {
            list = (LinkedList<Recipe>) in.readObject();

        } catch (Exception e)
        {
            System.out.println(e);
        }
        return list;
    }
}


