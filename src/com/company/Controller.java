package com.company;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.io.File;

public class Controller
{
    public Controller()
    {
        mainWindow = new MainWindow(this, "Current Week", weeks[1]);   //creates the mainWindow with the current week
        allRecipes = new LinkedList<>();
        pastData = new LinkedList<>();
        recipesPath = System.getProperty("user.home") + "/recipeImages/";
        new File(recipesPath).mkdirs();
    }


    public String createImageFile(String name, String fileName)
    {
        String pathName = recipesPath + name;
        new File(pathName).mkdirs();
        Path CopyTo = Paths.get(pathName);
        Path CopyFrom = Paths.get(fileName);
        try {
            Files.copy(CopyFrom, CopyTo, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathName;
    }

    public DisplayImage constructDisplayImage(String imagePath)
    {
        DisplayImage window = null;
        try {
            window = new DisplayImage(this,imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return window;
    }

    public AddRecipe constructAddRecipe()
    {
        AddRecipe window = null;
        window = new AddRecipe(this);
        return window;
    }

    public SearchEngine constructSearchEngine()
    {
        SearchEngine window = null;
        window = new SearchEngine(this);
        return window;
    }

    public EnterNameOfRecipe constructEnterNameOfRecipe(Week w, int num)
    {
        EnterNameOfRecipe window = null;
        window = new EnterNameOfRecipe(this,w, num);
        return window;
    }

    public NoSuggestions constructNoSuggestion()
    {
        NoSuggestions window = null;
        window = new NoSuggestions(this);
        return window;
    }

    public SuggestionWindow constructSuggestionWindow()
    {
        SuggestionWindow window = null;
        window = new SuggestionWindow(this);
        return window;
    }

    public SuggestionSetups constructSuggestionSetups()
    {
        SuggestionSetups window = null;
        window = new SuggestionSetups(this);
        return window;
    }

    public MainWindow constructMainWindow(String title, Week week)
    {
        MainWindow window = null;
        window = new MainWindow(this, title, week);
        return window;
    }

    public void AddRecipeToCalendar(Week w, int num, String RecipeName)
    {
        for (int i = 0; i < allRecipes.size(); i++)
        {
            if (RecipeName.equals(allRecipes.get(i).getName()))  // compares to the name of the recipe at index i
            {
                w.getDailyRecipe(num).addToList(allRecipes.get(i));
                mainWindow.UpdateCalendar();
            }
        }
    }

    public void RemoveRecipeFromCalendar(Week w, int c, int r)
    {
        w.getDailyRecipe(c).removeFromList(r);
        mainWindow.UpdateCalendar();
    }

    public String SearchRecipeImagePath(Recipe r)
    {
        String path = null;
        for (int i = 0; i < allRecipes.size(); i++)
        {
            if (r == allRecipes.get(i))
                path = allRecipes.get(i).getImage();
        }
        return path;
    }

    public void AddRecipeToProgram(String nameOfRecipe, String category1, String category2, String originalPathOfImage)
    {
        allRecipes.add(new Recipe(nameOfRecipe, category1, category2, createImageFile(nameOfRecipe, originalPathOfImage)));
    }

    public boolean CheckIfRecipeNameExists(String name)
    {
        boolean exist = false;
        for (int i = 0; i < allRecipes.size() && !exist ; i++)
        {
            if (allRecipes.get(i).getName().equals(name));
            exist = true;
        }
        return exist;
    }

    private MainWindow mainWindow;
    private AddRecipe addRecipe;
    private SearchEngine searchEngine;
    private EnterNameOfRecipe enterNameOfRecipe;
    private NoSuggestions noSuggestions;
    private SuggestionSetups suggestionSetups;
    private SuggestionWindow suggestionWindow;
    private DisplayImage displayImage;
    public Week[] weeks= {new Week(WeekType.Past), new Week(WeekType.Current), new Week(WeekType.Next)};
    public LinkedList<Recipe> pastData;
    public LinkedList<Recipe> allRecipes;
    public String recipesPath;
}