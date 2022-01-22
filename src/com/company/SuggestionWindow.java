package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SuggestionWindow extends JFrame
{
    public SuggestionWindow (Controller controller, String suggestedRecipe, Week w, int placeOfDay)
    {
        this.controller = controller;

        JLabel suggestion = new JLabel(suggestedRecipe);
        suggestion.setFont(new Font("Bookman Old Style", Font.PLAIN, 18));

        Color buttonColor = new Color(199,187,188);
        Font buttonFont = new Font ("Bookman Old Style", Font.ITALIC | Font.BOLD, 12);

        JButton accept = new JButton ("Accept");
        accept.setBackground(buttonColor);
        accept.setFont(buttonFont);
        accept.addActionListener((ActionEvent e)->
        {
            controller.addRecipeToCalendar(w, placeOfDay, suggestedRecipe);
            // if the user accepts the suggestion the addRecipeToCalendar method in the controller is called which adds the
            // recipe to the day in question
            controller.calendarView.updateCalendar();
            dispose();
        });

        JButton newSuggestion = new JButton ("New suggestion");
        newSuggestion.setBackground(buttonColor);
        newSuggestion.setFont(buttonFont);
        newSuggestion.addActionListener((ActionEvent e)->
        {
            if(!controller.suggestionList.isEmpty())
                controller.constructSuggestionWindow(controller.suggestionList.removeFirst().getName(), w, placeOfDay);
                // if the user clicks the "new suggestion" button the program will suggest a new recipe that fulfills the same
                // category 1 and 2 criteria that the user has already entered. As such the same window is constructed again
                // suggesting the recipe that is next in the already created suggestion list.
            else
                controller.constructNoSuggestion();
            // If the suggestion list is empty the window NoSuggestion will be constructed
            dispose();
        });

        JButton cancel = new JButton ("Cancel");
        cancel.setBackground(buttonColor);
        cancel.setFont(buttonFont);
        cancel.addActionListener((ActionEvent e)-> dispose());


        GroupLayout layout = new GroupLayout(getContentPane ());
        getContentPane ().setLayout (layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(suggestion)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(accept)
                                .addComponent(cancel)
                                .addComponent(newSuggestion)));
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(suggestion)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(accept)
                                .addComponent(cancel)
                                .addComponent(newSuggestion)));

        Color backgroundColor = new Color(243,216, 209);
        getContentPane().setBackground(backgroundColor);
        setVisible(true);
        pack();
    }
    private Controller controller;
}
