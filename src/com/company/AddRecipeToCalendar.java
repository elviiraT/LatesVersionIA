package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;


public class AddRecipeToCalendar extends JFrame
{
    public AddRecipeToCalendar(Controller controller, Week w, int day)
    {
        this.controller = controller;
        GroupLayout layout = new GroupLayout(getContentPane ());
        getContentPane ().setLayout (layout);

        JLabel text = new JLabel("Enter the name of the recipe");
        text.setFont(new Font ("Bookman Old Style", Font.PLAIN, 18));

        // Creates a DefaultComboBoxModel with names of all the recipes entered in the program
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
                // calls method that sorts the string array in the DefaultComboBoxModel so the recipe names
                // corresponding to the user's search are at the beginning of the list
            }
        });

        recipe.addActionListener((ActionEvent e)-> recipeName = (String)recipe.getSelectedItem());




        JButton add = new JButton("Add");
        Color buttonColor = new Color(199,187,188);
        add.setBackground(buttonColor);
        add.setFont(new Font ("Bookman Old Style", Font.ITALIC | Font.BOLD, 12));
        add.addActionListener((ActionEvent e) ->
        {
            controller.addRecipeToCalendar(w,day,recipeName);
            // calls a method in the controller that adds the recipe to the day of the week the user
            // has selected which are passed in the parameters of this class
            controller.calendarView.updateCalendar();
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
            int numberOfSwaps = 0; // indicates to where a recipe should be swapped
            String temporary;
            for (int i = 0; i < allRecipes.length; i++)
            // iterates through all the recipes
            {
                String recipe = allRecipes[i];
                if (recipe.length() >= length) {
                    String checkIfSame = recipe.substring(0, length).toLowerCase();
                    // creates a substring of the string at index i which has the length of the search term
                    if (search.toLowerCase().equals(checkIfSame))
                        // if the string are equal it swaps the recipe at index i (the one corresponding to the search term)
                        // and the recipe at index numberOfSwaps
                    {
                        temporary = allRecipes[i];
                        allRecipes[i] = allRecipes[numberOfSwaps];
                        allRecipes[numberOfSwaps] = temporary;
                        numberOfSwaps++;
                        // numberOfSwaps is increased by 1 so the next recipe will be swapped to the next index
                    }
                }
            }
            DefaultComboBoxModel sorted = new DefaultComboBoxModel(allRecipes);
            recipe.setModel(sorted); // sets the new DefaultComboBoxModel
            recipe.setSelectedItem(search);
            recipe.showPopup();
        }
        else
            {
            recipe.setModel(defaultModel);//sets the DefaultModel if no search is made
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
