package com.company;
import java.awt.event.ActionEvent;

public class Controller
{
    public Controller()
    {
        mainWindow = new MainWindow(this);
        addRecipeWindow = new AddRecipeWindow(this);
        addRecipeWindow = new AddRecipeWindow(this);
    }
    private MainWindow mainWindow;
    private AddRecipeWindow addRecipeWindow;
    private SearchEngine searchEngine;
}