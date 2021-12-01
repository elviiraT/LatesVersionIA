package com.company;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
        text.setFont(new Font ("Bookman Old Style", Font.PLAIN, 18));
        recipe = new JTextField();
        recipe.setBackground(new Color(240, 228, 215));

        popup = new JPopupMenu();
        JMenu subMenu = new JMenu("m");
        subMenu.add("m1");
        subMenu.add("m2");

// 3. Finally, add the sub-menu and item to the popup
        popup.add(subMenu);
        popup.add("n");

        allRecipes = new LinkedList();
        for (int x = 0; x < controller.allRecipes.size(); x++)
            allRecipes.add(controller.allRecipes.get(x).getName());
        model = new DefaultListModel<>();
        searchedRecipes = new JList();


        recipe.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent evt)
            {
                recipeName = recipe.getText();
                searchTxtKeyReleased(evt);
            }
        });
       //         getDocument().addDocumentListener(new DocumentListener(){
       //     public void changedUpdate(DocumentEvent e) { warn(); }
       //     public void removeUpdate(DocumentEvent e) { warn(); }
       //     public void insertUpdate(DocumentEvent e) { warn(); }
       //     public void warn()
       //     {
       //         RecipeName = recipe.getText();
       //     }});


        JButton add = new JButton("Add");
        Color buttonColor = new Color(199,187,188);
        add.setBackground(buttonColor);
        add.setFont(new Font ("Bookman Old Style", Font.ITALIC | Font.BOLD, 12));
        add.addActionListener((ActionEvent e) ->
        {
            controller.AddRecipeToCalendar(w,num,recipeName);
            controller.mainWindow.UpdateCalendar();
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

        if (!recipeName.equals(""))
        {
            int length = recipeName.length();
            for (int i = 0; i < allRecipes.size(); i++)
            // iterates through all the recipes
            {
                String r = allRecipes.get(i);
                if (r.length() >= length) {
                    String checkIfSame = r.substring(0, length).toLowerCase();
                    // creates a substring of the string at index i which has the length of the search term
                    if (recipeName.toLowerCase().equals(checkIfSame))
                    // if substring and the search term are equal the string is added to the filtered items list
                    // and the index of that string is stored to the index array list
                    {
                        model.addElement(r);
                    }
                }
            }
            searchedRecipes.setModel(model);

            recipe.add(popup);
            recipe.setComponentPopupMenu(popup);
            //recipe.setInheritsPopupMenu(true);

        }
    }

    private Controller controller;
    private LinkedList <String>  allRecipes;
    private DefaultListModel<String> model;
    private JList searchedRecipes;
    private JPopupMenu popup;
    private JTextField recipe;
    private String recipeName;
}
