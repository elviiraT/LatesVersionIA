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
        GroupLayout layout = new GroupLayout(getContentPane ());
        getContentPane ().setLayout (layout);

        enterName = new JLabel("Enter the name of the recipe");
        enterName.setFont(new Font ("Calibri", Font.BOLD, 20));

        name = new JTextField(15);
        name.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e) { warn(); }
            public void removeUpdate(DocumentEvent e) { warn(); }
            public void insertUpdate(DocumentEvent e) { warn(); }
            public void warn(){ n = name.getText(); }});


        chooseCategory1 = new JLabel("Choose the 1st category");
        chooseCategory1.setFont(new Font ("Calibri", Font.BOLD, 20));

        ActionListener defineCat1 = new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
            JRadioButton button = (JRadioButton) actionEvent.getSource();
            cat1 = button.getText();
            }
        };

        chicken = new JRadioButton("chicken");
        chicken.addActionListener(defineCat1);
        meat = new JRadioButton("meat");
        meat.addActionListener(defineCat1);
        fish = new JRadioButton("fish");
        fish.addActionListener(defineCat1);
        vegetable = new JRadioButton("vegetable");
        vegetable.addActionListener(defineCat1);
        other1 = new JRadioButton("other");
        other1.addActionListener(defineCat1);
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(chicken);
        bg1.add(meat);
        bg1.add(fish);
        bg1.add(vegetable);
        bg1.add(other1);


        chooseCategory2 = new JLabel("Choose the 2nd category");
        chooseCategory2.setFont(new Font ("Calibri", Font.BOLD, 20));

        ActionListener defineCat2 = new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                JRadioButton button = (JRadioButton) actionEvent.getSource();
                cat2 = button.getText();
            }
        };

        soup = new JRadioButton("soup");
        soup.addActionListener(defineCat2);
        pasta = new JRadioButton("pasta");
        pasta.addActionListener(defineCat2);
        salad = new JRadioButton("salad");
        salad.addActionListener(defineCat2);
        dessert = new JRadioButton("dessert");
        dessert.addActionListener(defineCat2);
        pastries = new JRadioButton("pastries");
        pastries.addActionListener(defineCat2);
        other2 = new JRadioButton("other");
        other2.addActionListener(defineCat2);
        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(soup);
        bg2.add(pasta);
        bg2.add(salad);
        bg2.add(dessert);
        bg2.add(pastries);
        bg2.add(other2);


        image = new JButton("add image");
        image.addActionListener((ActionEvent e)->
        {
               JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
               FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG & GIF Images", "jpg", "gif", "png");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(this);
                if(returnVal == JFileChooser.APPROVE_OPTION)
                {
                    originalImagePath = chooser.getSelectedFile().getPath();
                }
        });



        save = new JButton("Save");
        save.addActionListener((ActionEvent e) ->
                {
                    //Make an algorithm that checks whether there already is Recipe with that name
                    controller.AddRecipeToProgram(n, cat1, cat2, controller.createImageFile(n, originalImagePath));
                    dispose();
                    //calls method from controller that creates a new Recipe object and stores it in AllRecipes
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
    private String n;
    private String cat1;
    private String cat2;
    private String originalImagePath;


    private Controller controller;
    private JLabel enterName;
    private JTextField name;
    private JLabel chooseCategory1;
    private JLabel chooseCategory2;
    private JRadioButton chicken;
    private JRadioButton meat;
    private JRadioButton fish;
    private JRadioButton vegetable;
    private JRadioButton other1;
    private JRadioButton soup;
    private JRadioButton pasta;
    private JRadioButton salad;
    private JRadioButton dessert;
    private JRadioButton pastries;
    private JRadioButton other2;
    private JButton image;
    private JButton save;
}
