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

    public SuggestionWindow constructSuggestionWindow(String suggestedRecipe, Week w, int PlaceOfDay)
    {
        SuggestionWindow window = null;
        window = new SuggestionWindow(this, suggestedRecipe, w, PlaceOfDay);
        return window;
    }

    public SuggestionSetups constructSuggestionSetups(Week week, int placeOfDay)
    {
        SuggestionSetups window = null;
        window = new SuggestionSetups(this, week, placeOfDay);
        return window;
    }

    public MainWindow constructMainWindow(String title, Week week)
    {
        MainWindow window = null;
        window = new MainWindow(this, title, week);
        return window;
    }











    public void AddRecipeToCalendar(Week w, int day, String RecipeName)
    // iterates through all the recipes saved in the program and checks if the recipe name given in the parameters corresponds to
    // the name of a recipe in the list of all recipes and then if it finds a match it adds the recipe to the given day of the given week
    {
        for (int i = 0; i < allRecipes.size(); i++) // iterates through all recipes
        {
            if (RecipeName.equals(allRecipes.get(i).getName()))  // compares the recipe name to the name of the recipe at index i
            {
                w.getDailyRecipe(day).add(allRecipes.get(i));
                //if a matching name is found, the recipe is added to the list of recipes of the given day of the given week
                mainWindow.UpdateCalendar();
            }
        }
    }

    public void RemoveRecipeFromCalendar(Week w, int placeOfDay, int indexOfRecipe)
    // removes the recipe at the given index of the given week and day of week
    {
        w.getDailyRecipe(placeOfDay).remove(indexOfRecipe);
        mainWindow.UpdateCalendar();
    }


   // public String SearchRecipeImagePath(Recipe r)
   // {
   //     String path = null;
   //     for (int i = 0; i < allRecipes.size(); i++)
   //     {
   //         if (r == allRecipes.get(i))
   //             path = allRecipes.get(i).getImage();
   //     }
   //     return path;
   // }

    public void AddRecipeToProgram(String nameOfRecipe, String category1, String category2, String originalPathOfImage)
    {
        allRecipes.add(new Recipe(nameOfRecipe, category1, category2, createImageFile(nameOfRecipe, originalPathOfImage)));
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

    public Recipe SuggestARecipe (String category1, String category2, Week w, int placeOfDay)   //method that returns the recipe from the both categories that the user has selected
    {
        suggestionList = new LinkedList<>();
        int placeOfWeek;
        if (w.getWeekType().equals(WeekType.Next))
            placeOfWeek = 2;
        else
            placeOfWeek = 1;

        for (int week = placeOfWeek; week >= 0; week--)
        {
                AddingRecipesOfAWeekToSuggestionList(week, placeOfDay, category1, category2);
                placeOfDay = 6;
        }

        AddingRecipesOFromAListToSuggestionList(pastData, category1, category2);
        AddingRecipesOFromAListToSuggestionList(allRecipes, category1, category2);

        if (!suggestionList.isEmpty())
            return suggestionList.removeFirst();
        else
            return null;
    }

    public boolean MatchingCategory(Recipe r, String cat1, String cat2)
    {
        boolean match = false;
        if ((cat1.equals("all") && cat2.equals("all")) || (cat1.equals("all") && cat2.equals(r.getCategory2())) || (cat1.equals(r.getCategory1()) && cat2.equals("all")) || (cat1.equals(r.getCategory1()) && cat2.equals(r.getCategory2())))
            //checks if the recipe belongs to the given options of category 1 and category 2, if a category is "all" all options of the category are accepted
            match = true;
        return match;
    }

    public boolean NotFound(LinkedList<Recipe> list,Recipe r)
    {
        boolean notFound = true;
        for (int i = 0; i < list.size() && notFound; i++)
            if (list.get(i).equals(r))
                notFound = false;
        return notFound;
    }

    public void AddingRecipesOfAWeekToSuggestionList(int placeOfWeek, int placeOfDay, String category1, String category2)
    {
        for (int day = placeOfDay; day >= 0; day--)
        {
            int NumberOfRecipesOfDay = weeks[placeOfWeek].getDailyRecipe(day).size();
            LinkedList<Recipe> ListOfRecipesOfDay = weeks[placeOfWeek].getDailyRecipe(day);
            for(int recipe = 0; recipe < NumberOfRecipesOfDay; recipe++)
            {
                Recipe compare = ListOfRecipesOfDay.get(recipe);
                if (MatchingCategory(compare, category1, category2) && NotFound(suggestionList, compare))
                    suggestionList.addFirst(compare);
            }
        }
    }

    public void AddingRecipesOFromAListToSuggestionList (LinkedList<Recipe> list, String category1, String category2)
    {
        for (int indexOfRecipe = 0; indexOfRecipe < list.size(); indexOfRecipe++)
        {
            Recipe check = list.get(indexOfRecipe);
            if (MatchingCategory(check , category1, category2) && NotFound(list, check))
                suggestionList.addFirst(check);
        }
    }


    public MainWindow mainWindow;
    public Week[] weeks= {new Week(WeekType.Past), new Week(WeekType.Current), new Week(WeekType.Next)};
    public LinkedList<Recipe> pastData;
    public LinkedList<Recipe> allRecipes;
    public String recipesPath;
    public LinkedList<Recipe> suggestionList;
}