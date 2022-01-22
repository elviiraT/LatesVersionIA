package com.company;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class Controller
{
    public Controller()
    {
        now = GregorianCalendar.getInstance();
        weeks = checksIfSerializedWeeksAreStillRelevant(Serialization.readWeeks());
        pastData = Serialization.readRecipesList(Serialization.pastData);
        allRecipes = Serialization.readRecipesList(Serialization.allRecipes);
        calendarView = new CalendarView(this, "Current Week", weeks[1]);
        //creates the calendarView with the current week

        recipesPath = System.getProperty("user.home") + "/recipeImages/";
        // the pathname to the directory where the image files will be stored
        new File(recipesPath).mkdirs();
        // creates a directory where the image files of the recipes will be stored
    }

    public AddRecipe constructAddRecipe()
    {
        AddRecipe window = new AddRecipe(this);
        return window;
    }

    public SearchEngine constructSearchEngine()
    {
        SearchEngine window = new SearchEngine(this);
        return window;
    }

    public AddRecipeToCalendar constructAddRecipeToCalendar(Week w, int num)
    {
        AddRecipeToCalendar window = new AddRecipeToCalendar(this,w, num);
        return window;
    }

    public NoSuggestions constructNoSuggestion()
    {
        NoSuggestions window = new NoSuggestions(this);
        return window;
    }

    public MissingInformation constructMissingInformation()
    {
        MissingInformation window = new MissingInformation(this);
        return window;
    }

    public SuggestionWindow constructSuggestionWindow(String suggestedRecipe, Week w, int placeOfDay)
    {
        SuggestionWindow window = new SuggestionWindow(this, suggestedRecipe, w, placeOfDay);
        return window;
    }

    public SuggestionSetups constructSuggestionSetups(Week w, int placeOfDay)
    {
        SuggestionSetups window = new SuggestionSetups(this, w, placeOfDay);
        return window;
    }

    public CalendarView constructCalendarView(String title, Week w)
    {
        CalendarView window = new CalendarView(this, title, w);
        return window;
    }






    private Week[] checksIfSerializedWeeksAreStillRelevant(Week[] weeks)
    // method that checks the week number of the weeks that have been serialized and compares them to the current week
    // the method returns the serialized weeks if they are still relevant or if they are not it returns new Week objects
    // and adds the data from the weeks to the pastData list
    {
        int currentWeekNumber = now.get(Calendar.WEEK_OF_YEAR);
        if (weeks!=null) {
            if (weeks[1].getWeekNumber() == currentWeekNumber)
                // if the week number of the Current week in the serialized data is equal to the
                // currentWeekNumber the method returns all the serialized weeks
                return weeks;
            else if (weeks[2].getWeekNumber() == currentWeekNumber)
            // if the week number of the Next week (which is at index 2) is equal to currentWeekNumber the method returns a new Week array with
            // the Next weekin the place of the Current week and the Current week in the place of the Past week, and in the place of the Next week
            // it creates a new Week object
            {
                addingToPastDataList(weeks[0]); // adds data of irrelevant weeks to the pastData list
                weeks[1].changeWeekType(WeekType.Past);
                //Changes the weekType of the week as they change place in the array in this case from Current weekType to Past weekType
                weeks[2].changeWeekType(WeekType.Current);
                return new Week[]{weeks[1], weeks[2], new Week(WeekType.Next)};
            } else if (weeks[2].getWeekNumber() == currentWeekNumber - 1)
            // if the week number of the Next week in the is equal to currentWeekNumber-1 the method
            // returns a new Week array with the Next week in place of the Past week
            // and in place of the Current and Next week it creates a new Week object
            {
                addingToPastDataList(weeks[0]);
                addingToPastDataList(weeks[1]);
                weeks[2].changeWeekType(WeekType.Past);
                return new Week[]{weeks[2], new Week(WeekType.Current), new Week(WeekType.Next)};

            } else
            // otherwise it returns a new Week array with new Week objects
            {
                addingToPastDataList(weeks[0]);
                addingToPastDataList(weeks[1]);
                addingToPastDataList(weeks[2]);
                return new Week[]{new Week(WeekType.Past), new Week(WeekType.Current), new Week(WeekType.Next)};
            }
        }
        return new Week[]{new Week(WeekType.Past), new Week(WeekType.Current), new Week(WeekType.Next)};
    }



    private void addingToPastDataList ( Week w)
    // adds the recipes contained in a Week object presented in the calendar to the pastData list
    {
        for (int day = 1; day <= 6; day--)
            // loops from the first day of the week to the last one because the Recipes are added
            // at the beginning of the list and the most recent Recipes have to be at the beginning of the list
        {
            int NumberOfRecipesOfDay = w.getDailyRecipe(day).size();
            LinkedList<Recipe> ListOfRecipesOfDay = w.getDailyRecipe(day);
            for(int indexOfRecipe = 0; indexOfRecipe < NumberOfRecipesOfDay; indexOfRecipe++)
            // loops through all the recipes of a day and adds them at the beginning of the past data list
            {
                    pastData.addFirst(ListOfRecipesOfDay.get(indexOfRecipe));
            }
        }
    }





    public void openImageOfSelectedRecipe(String nameOfRecipe) throws IOException
    // method that opens the image file of the recipe which has its name passed in the parameters
    {
        for (int i = 0; i < allRecipes.size(); i++)
        // loops through all the recipes and compare the entered string to the name of each recipe
        {
            if (nameOfRecipe.equals(allRecipes.get(i).getName()))
                // if the strings match, the accessor of the recipe's image file's pathname is used
                // to open the image file
            {
                File image = new File(allRecipes.get(i).getImage());
                Desktop desktop = Desktop.getDesktop();
                if(image.exists()) desktop.open(image);
            }
        }
    }

    public void addRecipeToCalendar(Week w, int day, String RecipeName)
    // iterates through all the recipes saved in the program and checks if the recipe name given in the parameters corresponds to
    // the name of a recipe in the list of all recipes and then if it finds a match it adds the recipe to the given day of the given week
    {
        for (int i = 0; i < allRecipes.size(); i++) // iterates through all recipes
        {
            if (RecipeName.equals(allRecipes.get(i).getName()))  // compares the recipe name to the name of the recipe at index i
            {
                w.getDailyRecipe(day).add(allRecipes.get(i));
                //if a matching name is found, the recipe is added to the list of recipes of the given day of the given week
                calendarView.updateCalendar();
            }
        }
    }

    public void removeRecipeFromCalendar(Week w, int placeOfDay, int indexOfRecipe)
    // removes the recipe at the given index of the given week and day of week
    {
        w.getDailyRecipe(placeOfDay).remove(indexOfRecipe);
        calendarView.updateCalendar();
    }



    public void addRecipeToProgram(String nameOfRecipe, String category1, String category2, String originalPathOfImage)
    {
        //Method called by AddRecipe class that creates a new Recipe object and stores it in allRecipes
        allRecipes.add(new Recipe(nameOfRecipe, category1, category2, createImageFile(nameOfRecipe, originalPathOfImage)));
    }

    public String createImageFile(String name, String filePath)
    // method called when a Recipe object is created, it creates a file
    // for the image of the recipe and returns the pathname of the image
    // so it can be stored in the Recipe object
    // The methods parameters consist of the recipe's name and the original
    // pathname of the image file that needs to be stored
    {
        String pathname = recipesPath + name;
        // creates the pathname for the image file that will store
        // it in the directory with all the image files
        new File(pathname).mkdirs();
        Path CopyTo = Paths.get(pathname);
        Path CopyFrom = Paths.get(filePath);
        try {
            Files.copy(CopyFrom, CopyTo, StandardCopyOption.REPLACE_EXISTING);
            // copies image in the original file to the newly created file
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathname;
    }

    public boolean checkIfRecipeNameExists(String name)
    // method that checks if a recipe name already exists
    // returns true if it exists and false it doesn't
    {
        boolean exist = false;
        for (int i = 0; i < allRecipes.size() && !exist ; i++) // iterates through already existing recipes in allRecipes list
            if (allRecipes.get(i).getName().equals(name))
                // checks if the name given in the method's parameters equals to the name of the recipe in the allRecipe list
                exist = true;
        return exist;
    }




    public Recipe suggestARecipe (String category1, String category2, Week w, int placeOfDay)
    //method that creates a new suggestion list (according to the both categories that the user has selected)
    // and returns the first recipe from that list (the oldest recipes are added at the beginning of the SuggestionList)
    // parameters include also the place of the week and the place of the day of that week to which
    // the user wants to add the suggestion to from where to start adding recipes
    {
        suggestionList = new LinkedList<>();
        int placeOfWeek; //variable that will store the place of the week
        if (w.getWeekType().equals(WeekType.Next))
            // Does not include the Past week because the user would not plan the meals of a past week
            placeOfWeek = 2;
        else
            placeOfWeek = 1;

        for (int week = placeOfWeek; week >= 0; week--)
            //goes through the week to which the suggestion is made and all the weeks before
        {
                addingRecipesOfWeeksToAList(weeks[week], placeOfDay, category1, category2);
                // calls a method that will iterate through the linked list of recipes of each day of the week and check
                // if the recipe corresponds to the users options of category 1 and category 2, if it does correspond the recipe
                // will be added to the suggestion list
                placeOfDay = 6;
                // initially placeOfDay is equal to the place of the day to which the user wants to add the recipe
                // because the suggestion function only takes into account the data before that day
                // After going through the recipes of the week to which the suggestion is added placeOfDay is changed
                // to 6 so that the method would iterate through all the days of the past weeks
        }

        addingRecipesToSuggestionList(pastData, category1, category2);
        // method that adds recipes to the suggestion list from the pasta data list
        addingRecipesToSuggestionList(allRecipes, category1, category2);
        // method that adds recipes to the suggestion list from the list containing all recipes in case there is a recipe
        // that has never been added to the calendar before

        if (!suggestionList.isEmpty())
            return suggestionList.removeFirst();
        // returns and removes the first recipe in the suggestionList
        else
            return null;
    }

    private void addingRecipesToSuggestionList (LinkedList<Recipe> list, String category1, String category2)
    // method that iterates through a list containing recipes and adds a recipe to the suggestion list if the
    // recipe corresponds to the right category 1 and 2
    {
            for (int indexOfRecipe = 0; indexOfRecipe < list.size(); indexOfRecipe++)
                // loops through all recipes in the list
                // recipes at the end of pastData are the oldest ones and they have to be at the beginning of the suggestionList
                // as such it loops from recipes at the begining of the pastData list to the end of it
            {
                Recipe compare = list.get(indexOfRecipe);
                if (matchingCategory(compare, category1, category2) && notFound(suggestionList, compare))
                    //calls a method that returns a boolean true if the recipe's category 1 and 2 corresponds to the values passed in the
                    // parameters' of the method and then calls a method that return true if the recipe in question is not already in the
                    // suggestion list
                    // if both methods return true the recipe is added to first index the suggestion list
                    suggestionList.addFirst(compare);

            }
    }

    private void addingRecipesOfWeeksToAList( Week week, int placeOfDay, String category1, String category2)
    // method that iterates through all the recipes of a week and adds a recipe to the suggestion list if the recipe's category 1
    // and category 2 correspond to the values passed in the parameters of the method
    {
    for (int day = placeOfDay; day >= 0; day--)
        // loops through all days of the week, goes from the biggest index to the smallest
        // so that the most recent recipes are first added to the SuggestionList to have the
        // oldest ones at the beginning of the suggestionList
        {
            int numberOfRecipesOfDay = week.getDailyRecipe(day).size(); // stores the size of the linked list of a day
            LinkedList<Recipe> ListOfRecipesOfDay = week.getDailyRecipe(day); // stores the linked list of a day
            for(int indexOfRecipe = 0; indexOfRecipe < numberOfRecipesOfDay; indexOfRecipe++)
                // iterate through the linked list of a day containing the recipes of that day
                {
                    Recipe compare = ListOfRecipesOfDay.get(indexOfRecipe); // stores the recipe at the given index
                if (matchingCategory(compare, category1, category2) && notFound(suggestionList, compare))
                // calls method that returns true if compare matches the category 1 and 2 given in the parameters of this method
                // also calls method that returns true if compare cannot be found in the suggestionList yet
                // if both methods are true compare is added to the first index of the suggestionList
                    suggestionList.addFirst(compare);
                }
        }
    }

    private boolean matchingCategory(Recipe r, String category1, String category2)
    // method that returns true if a recipe's category 1 and 2 correspond to the category options passed in the parameters of the
    // method
    {
        boolean match = false;
        if ((category1.equals("all") && category2.equals("all")) || (category1.equals("all") && category2.equals(r.getCategory2())) ||
                (category1.equals(r.getCategory1()) && category2.equals("all")) || (category1.equals(r.getCategory1()) && category2.equals(r.getCategory2())))
            //checks if the recipe belongs to the given options of category 1 and category 2,
            // if in the parameters cat1 or cat2 is equal to "all", all options of the category are accepted
            match = true;
        return match;
    }

    private boolean notFound(LinkedList<Recipe> list, Recipe r)
    // method that returns true if the recipe passed in the parameters is not found in the linked list passed in the parameters
    {
        boolean notFound = true;
        for (int i = 0; i < list.size() && notFound; i++)
        {
            if (list.get(i).getName().equals(r.getName()))
                notFound = false;
        }
        return notFound;
    }



    public void saveData()
    // method that serializes all data by calling the methods in Serialization class
    {
        Serialization.saveWeeks(weeks);
        Serialization.saveRecipesList(pastData, Serialization.pastData);
        Serialization.saveRecipesList(allRecipes,Serialization.allRecipes);
    }




    public CalendarView calendarView;
    public Week[] weeks;
    public LinkedList<Recipe> pastData;
    public LinkedList<Recipe> allRecipes;
    private static String recipesPath;
    public LinkedList<Recipe> suggestionList;
    private Calendar now;
}