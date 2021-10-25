package com.company;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

public class EnterNameOfRecipe extends JFrame
{
    public EnterNameOfRecipe(Controller controller, Week w, int num)
    {
        this.controller = controller;
        GroupLayout layout = new GroupLayout(getContentPane ());
        getContentPane ().setLayout (layout);

        JLabel text = new JLabel("Enter the name of the recipe");
        text.setFont(new Font("Calibri", Font.BOLD, 22));
        JTextField recipe = new JTextField();
        recipe.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e) { warn(); }
            public void removeUpdate(DocumentEvent e) { warn(); }
            public void insertUpdate(DocumentEvent e) { warn(); }
            public void warn()
            {
                RecipeName = recipe.getText();
            }});

        JButton add = new JButton("Add");
        add.addActionListener((ActionEvent e) ->
        {
            controller.AddRecipeToCalendar(w,num,RecipeName);
            dispose();
        });



        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(text)
                        .addComponent(recipe)
                        .addComponent(add, GroupLayout.Alignment.TRAILING));
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(text)
                        .addComponent(recipe)
                        .addComponent(add));

        setVisible(true);
        pack();
    }
    private Controller controller;
    private String RecipeName;
    private Week w;
}
