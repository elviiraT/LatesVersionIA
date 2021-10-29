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
        JLabel chooseCategory2 = new JLabel("Choose 2nd category");

        String[] category1Options = {"chicken", "meat", "fish", "vegetable", "other", "all"};
        JComboBox category1 = new JComboBox(category1Options);
        selectedCategory1 = "chicken";
        category1.addActionListener((ActionEvent e)-> { selectedCategory1 = (String)category1.getSelectedItem(); });

        String[] category2Options = {"soup", "pasta", "salad", "dessert", "pastries", "other", "all"};
        JComboBox category2 = new JComboBox(category2Options);
        selectedCategory2 = "soup";
        category1.addActionListener((ActionEvent e)-> { selectedCategory2 = (String)category2.getSelectedItem(); });

        JButton suggest = new JButton("suggest");
        suggest.addActionListener((ActionEvent e)->
        {
            Recipe r = controller.SuggestARecipe(selectedCategory1, selectedCategory2, w, placeOfDay);
            if (r != null)
                controller.constructSuggestionWindow(r.getName(), w, placeOfDay);
            else
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

        pack();
        setVisible(true);
    }

    private Controller controller;
    private String selectedCategory1;
    private String selectedCategory2;

}

