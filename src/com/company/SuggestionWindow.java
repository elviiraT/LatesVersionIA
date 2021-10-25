package com.company;
import javax.swing.*;
import java.awt.*;


public class SuggestionWindow extends JFrame
{
    public SuggestionWindow (Controller controller)
    {
        this.controller = controller;
        JLabel suggestion = new JLabel("*name of the suggested recipe*");
        suggestion.setFont(new Font("Calibri", Font.BOLD, 22));
        JButton accept = new JButton ("Accept");
        JButton cancel = new JButton ("Cancel");
        JButton newSuggestion = new JButton ("New suggestion");

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
