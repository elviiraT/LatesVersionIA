package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;


public class AddRecipeToCalendar extends JFrame
{
    public AddRecipeToCalendar(Controller controller, Week w, int num)
    {
        this.controller = controller;
        GroupLayout layout = new GroupLayout(getContentPane ());
        getContentPane ().setLayout (layout);

        JLabel text = new JLabel("Enter the name of the recipe");
        text.setFont(new Font ("Bookman Old Style", Font.PLAIN, 18));
        //recipe = new JTextField();
        //recipe.setBackground(new Color(240, 228, 215));

        allRecipes = new String [controller.allRecipes.size()];
        for (int x = 0; x < controller.allRecipes.size(); x++)
            allRecipes[x] = controller.allRecipes.get(x).getName();
        defaultModel = new DefaultComboBoxModel(allRecipes);

        recipe = new JComboBox(defaultModel);
        recipe.setEditable(true);
        recipe.setSelectedItem(null);

        JScrollBar pane = new JScrollBar();
        recipe.add(pane);

        JTextField editor = (JTextField) recipe.getEditor().getEditorComponent();

        editor.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent evt)
            {
                search = editor.getText();
                searchTxtKeyReleased(evt);
            }
        });

        recipe.addActionListener((ActionEvent e)-> recipeName = (String)recipe.getSelectedItem());




        JButton add = new JButton("Add");
        Color buttonColor = new Color(199,187,188);
        add.setBackground(buttonColor);
        add.setFont(new Font ("Bookman Old Style", Font.ITALIC | Font.BOLD, 12));
        add.addActionListener((ActionEvent e) ->
        {
            controller.AddRecipeToCalendar(w,num,recipeName);
            controller.calendarView.UpdateCalendar();
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

        Color backgroundColor = new Color(243,216, 209);
        getContentPane().setBackground(backgroundColor);
        setVisible(true);
        pack();
    }

    public void searchTxtKeyReleased(KeyEvent e)
    {
        if (!search.equals(""))
        {
            int length = search.length();
            int numberOfSwaps = 0;
            String temporary;
            for (int i = 0; i < allRecipes.length; i++)
            // iterates through all the recipes
            {
                String r = allRecipes[i];
                if (r.length() >= length) {
                    String checkIfSame = r.substring(0, length).toLowerCase();
                    // creates a substring of the string at index i which has the length of the search term
                    if (search.toLowerCase().equals(checkIfSame)) {
                        temporary = allRecipes[i];
                        allRecipes[i] = allRecipes[numberOfSwaps];
                        allRecipes[numberOfSwaps] = temporary;
                        numberOfSwaps++;
                    }
                }
            }
            DefaultComboBoxModel sorted = new DefaultComboBoxModel(allRecipes);
            recipe.setModel(sorted);
            recipe.setSelectedItem(search);
            recipe.showPopup();
        }
        else
            {
            recipe.setModel(defaultModel);
            recipe.showPopup();
            }
    }

    private String recipeName;
    private Controller controller;
    private JComboBox recipe;
    private DefaultComboBoxModel defaultModel;
    private String search;
    private String[] allRecipes;
}
