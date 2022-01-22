package com.company;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.util.LinkedList;

public class Serialization
{
    // static fields that store the name of each file were data will be saved
    static String weeksFile = "weeks.ser";
    static String pastData = "pastData.ser";
    static String allRecipes = "allRecipes.ser";


    public static void saveWeeks(Week[] weeks)
    {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(weeksFile)))
        {
            out.writeObject(weeks);
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public static Week[] readWeeks()
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


    public static void saveRecipesList(LinkedList<Recipe> list, String file)
    {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file)))
                //overwrites past data
        {
            out.writeObject(list);
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public static LinkedList<Recipe> readRecipesList(String file)
    {
        LinkedList<Recipe> list = null;
        File f = new File(file);
        if(!f.exists())
        {
            list = new LinkedList<Recipe>();
            // if the file does not exist an empty LinkedList is returned
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


