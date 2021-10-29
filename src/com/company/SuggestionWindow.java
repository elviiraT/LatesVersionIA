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
        suggestion.setFont(new Font("Calibri", Font.BOLD, 22));

        JButton accept = new JButton ("Accept");
        accept.addActionListener((ActionEvent e)-> {
            controller.AddRecipeToCalendar(w, placeOfDay, suggestedRecipe);
            controller.mainWindow.UpdateCalendar();
            dispose();
        });

        JButton newSuggestion = new JButton ("New suggestion");
        newSuggestion.addActionListener((ActionEvent e)-> {
            controller.constructSuggestionWindow(controller.suggestionList.removeFirst().getName(), w, placeOfDay);
            dispose();
        });

        JButton cancel = new JButton ("Cancel");
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

        setVisible(true);
        pack();
    }
    private Controller controller;
}
