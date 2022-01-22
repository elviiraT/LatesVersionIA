package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SuggestionSetups extends JFrame
{
    public SuggestionSetups (Controller controller, Week w, int placeOfDay)
    {
        this.controller = controller;
        JLabel chooseCategory1 = new JLabel("Choose 1st category");
        chooseCategory1.setFont(new Font("Bookman Old Style", Font.PLAIN, 18));

        JLabel chooseCategory2 = new JLabel("Choose 2nd category");
        chooseCategory2.setFont(new Font ("Bookman Old Style", Font.PLAIN, 18));

        Color ComboBoxColor = new Color(240, 228, 215);
        String[] category1Options = {"chicken", "meat", "fish", "vegetable", "other", "all"};
        JComboBox category1 = new JComboBox(category1Options);
        category1.setBackground(ComboBoxColor);
        selectedCategory1 = "chicken";
        category1.addActionListener((ActionEvent e)-> { selectedCategory1 = (String)category1.getSelectedItem(); });

        String[] category2Options = {"soup", "pasta", "salad", "dessert", "pastries", "other", "all"};
        JComboBox category2 = new JComboBox(category2Options);
        category2.setBackground(ComboBoxColor);
        selectedCategory2 = "soup";
        category2.addActionListener((ActionEvent e)-> { selectedCategory2 = (String)category2.getSelectedItem(); });

        JButton suggest = new JButton("suggest");
        Color buttonColor = new Color(199,187,188);
        suggest.setBackground(buttonColor);
        suggest.setFont(new Font ("Bookman Old Style", Font.ITALIC | Font.BOLD, 12));
        suggest.addActionListener((ActionEvent e)->
        {
            Recipe r = controller.suggestARecipe(selectedCategory1, selectedCategory2, w, placeOfDay);
            // calls a method in the controller class by passing it the chosen categories by the user and
            // the place of the week to which the suggestion should be added and the place of the day to which
            // the recipe should be added
            // This method then creates a list of the recipes that will be suggested
            // then returns and removes the first element of that list
            if (r != null)
                // if the recipe is not null the SuggestionWindow is created and the name of the suggested recipe is passed to the class
                // The week and the place of the day are also passed to it so the recipe is then added to the right place in the calendar
                controller.constructSuggestionWindow(r.getName(), w, placeOfDay);

            else
                // in case the suggestion list is empty and the method returns null the NoSuggestion popup window is constructed
                controller.constructNoSuggestion();
            dispose();
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(chooseCategory1)
                                .addComponent(category1))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(chooseCategory2)
                                .addComponent(category2))
                        .addComponent(suggest));
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(chooseCategory1)
                                .addComponent(category1))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(chooseCategory2)
                                .addComponent(category2))
                        .addComponent(suggest));

        Color backgroundColor = new Color(243,216, 209);
        getContentPane().setBackground(backgroundColor);
        pack();
        setVisible(true);
    }

    private Controller controller;
    private String selectedCategory1;
    private String selectedCategory2;

}

