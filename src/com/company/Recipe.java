package com.company;
import java.io.Serializable;

public class Recipe implements Serializable
{
    public Recipe (String name, String category1, String category2, String image)
    {
        this.name = name;
        this.category1 = category1;
        this.category2 = category2;
        this.image = image;
    }
    public String getName()
        {
            return name;
        }

    public String getCategory1()
    {
        return category1;
    }

    public String getCategory2()
    {
        return category2;
    }

    public String getImage()
    {
        return image;
    }

    public String toString()
    {
        return name;
    }
    // toString method that returns the name of the Recipe so the recipe would be presented in the
    // calendar with its name


    private String name;
    private String category1;
    private String category2;
    private String image;
}

