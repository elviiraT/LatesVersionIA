package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

public class AddRecipe extends JFrame
{
    public AddRecipe(Controller controller)
    {
        this.controller = controller;
        Color backgroundColor = new Color(243,216, 209);
        getContentPane().setBackground(backgroundColor);
        GroupLayout layout = new GroupLayout(getContentPane ());
        getContentPane ().setLayout (layout);

        JLabel enterName = new JLabel("Enter the name of the recipe");
        enterName.setFont(new Font("Bookman Old Style", Font.PLAIN, 18));

        JTextField name = new JTextField(15);
        name.setBackground(new Color(240, 228, 215));
        name.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { warn(); }
            public void removeUpdate(DocumentEvent e) { warn(); }
            public void insertUpdate(DocumentEvent e) { warn(); }
            public void warn(){ enteredName = name.getText(); }});


        JLabel chooseCategory1 = new JLabel("Choose the 1st category");
        chooseCategory1.setFont(new Font("Bookman Old Style", Font.PLAIN, 18));

        ActionListener defineCat1 = new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
            JRadioButton button = (JRadioButton) actionEvent.getSource();
            cat1 = button.getText();
            }
        };

        JRadioButton chicken = new JRadioButton("chicken");
        chicken.setBackground(backgroundColor);
        chicken.addActionListener(defineCat1);
        JRadioButton meat = new JRadioButton("meat");
        meat.setBackground(backgroundColor);
        meat.addActionListener(defineCat1);
        JRadioButton fish = new JRadioButton("fish");
        fish.setBackground(backgroundColor);
        fish.addActionListener(defineCat1);
        JRadioButton vegetable = new JRadioButton("vegetable");
        vegetable.setBackground(backgroundColor);
        vegetable.addActionListener(defineCat1);
        JRadioButton other1 = new JRadioButton("other");
        other1.setBackground(backgroundColor);
        other1.addActionListener(defineCat1);
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(chicken);
        bg1.add(meat);
        bg1.add(fish);
        bg1.add(vegetable);
        bg1.add(other1);


        JLabel chooseCategory2 = new JLabel("Choose the 2nd category");
        chooseCategory2.setFont(new Font("Bookman Old Style", Font.PLAIN, 18));

        ActionListener defineCat2 = new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                JRadioButton button = (JRadioButton) actionEvent.getSource();
                cat2 = button.getText();
            }
        };

        JRadioButton soup = new JRadioButton("soup");
        soup.setBackground(backgroundColor);
        soup.addActionListener(defineCat2);
        JRadioButton pasta = new JRadioButton("pasta");
        pasta.setBackground(backgroundColor);
        pasta.addActionListener(defineCat2);
        JRadioButton salad = new JRadioButton("salad");
        salad.setBackground(backgroundColor);
        salad.addActionListener(defineCat2);
        JRadioButton dessert = new JRadioButton("dessert");
        dessert.setBackground(backgroundColor);
        dessert.addActionListener(defineCat2);
        JRadioButton pastries = new JRadioButton("pastries");
        pastries.setBackground(backgroundColor);
        pastries.addActionListener(defineCat2);
        JRadioButton other2 = new JRadioButton("other");
        other2.setBackground(backgroundColor);
        other2.addActionListener(defineCat2);
        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(soup);
        bg2.add(pasta);
        bg2.add(salad);
        bg2.add(dessert);
        bg2.add(pastries);
        bg2.add(other2);

        Color buttonColor = new Color(199,187,188);
        Font buttonFont = new Font ("Bookman Old Style", Font.ITALIC | Font.BOLD, 12);

        JButton image = new JButton("add image");
        image.setBackground(buttonColor);
        image.setFont(buttonFont);
        image.addActionListener((ActionEvent e)->
        {
               JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
               FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG, GIF Images,MS Word file(.docx) & Pdf file(.pdf)", "jpg", "gif", "png", "docx", "pdf");
               chooser.setFileFilter(filter);
               int returnVal = chooser.showOpenDialog(this);
               if(returnVal == JFileChooser.APPROVE_OPTION)
               {
                   originalImagePath = chooser.getSelectedFile().getPath();
               }
        });



        JButton save = new JButton("Save");
        save.setBackground(buttonColor);
        save.setFont(buttonFont);
        save.addActionListener((ActionEvent e) ->
                {
                    if (enteredName!=null && cat1 !=null && cat2 !=null && originalImagePath !=null)
                    {
                        boolean exist = controller.CheckIfRecipeNameExists(enteredName);
                        // method in controller class is called which returns true if the
                        if (exist) {
                            int num = 1;
                            while (exist) {
                                recipeName = enteredName + num;
                                if (!controller.CheckIfRecipeNameExists(recipeName))
                                    exist = false;
                                else
                                    num++;
                            }
                        } else
                            recipeName = enteredName;
                        controller.AddRecipeToProgram(recipeName, cat1, cat2, controller.createImageFile(recipeName, originalImagePath));
                        dispose();
                        // calls method from controller that creates a new Recipe object and stores it in AllRecipes
                    }
                    else
                        controller.constructMissingInformation();
                });

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(enterName)
                        .addComponent(name)
                        .addComponent(chooseCategory1)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(chicken)
                                .addComponent(meat)
                                .addComponent(fish)
                                .addComponent(vegetable)
                                .addComponent(other1))
                        .addComponent(chooseCategory2)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(soup)
                                .addComponent(pasta)
                                .addComponent(salad)
                                .addComponent(dessert)
                                .addComponent(pastries)
                                .addComponent(other2))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(image)
                                .addComponent(save)));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(enterName)
                        .addComponent(name)
                        .addComponent(chooseCategory1)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(chicken)
                                .addComponent(meat)
                                .addComponent(fish)
                                .addComponent(vegetable)
                                .addComponent(other1))
                        .addComponent(chooseCategory2)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(soup)
                                .addComponent(pasta)
                                .addComponent(salad)
                                .addComponent(dessert)
                                .addComponent(pastries)
                                .addComponent(other2))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(image)
                                .addComponent(save)));





        setSize(800,500);
        pack();
        setVisible(true);
    }
    private String recipeName;
    private String enteredName;
    private String cat1;
    private String cat2;
    private String originalImagePath;
    private Controller controller;
}
