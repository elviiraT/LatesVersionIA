package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.util.LinkedList;

public class Serialization
{
    static String weeksFile = "weeks";


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
}


