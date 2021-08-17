package com.company;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SearchEngine
{
    public SearchEngine(Controller controller)
    {
        this.controller = controller;
        panel = new JPanel(new FlowLayout());
        add(panel);
        panel.add(recipes);
        String[] columnNames = {"Recipe's name",
                "category 1",
                "category 2"};

        model = new DefaultTableModel(columnNames, 4);
        recipes = new JTable(model);
        recipes.setSize(800, 350);

        setSize(800,500);
        setVisible(true);
    }
    private JPanel panel;
    private JComboBox keywordType;
    private JTextField enterKeyword;
    private JTable recipes;
    private DefaultTableModel model;
    private Controller controller;
}

