package com.company;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Recipe
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


    private String name;
    private String category1;
    private String category2;
    private String image;
}

