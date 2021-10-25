package com.company;
import javax.swing.*;
import java.awt.*;

public class SuggestionSetups extends JFrame
{
    public SuggestionSetups (Controller controller)
    {
        this.controller = controller;
        String[] category1Options = {"chicken", "meat", "fish", "vegetable", "other", "all"};
        category1 = new JComboBox(category1Options);
        String[] category2Options = {"soup", "pasta", "salad", "dessert", "pastries", "other", "all"};
        category2 = new JComboBox(category2Options);
        JLabel chooseCategory1 = new JLabel("Choose 1st category");
        JLabel chooseCategory2 = new JLabel("Choose 2nd category");
        JButton suggest = new JButton("suggest");

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
    private JComboBox category1;
    private JComboBox category2;
}

