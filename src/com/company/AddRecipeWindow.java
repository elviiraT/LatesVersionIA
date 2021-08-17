package com.company;
import javax.swing.*;
import java.awt.*;

public class AddRecipeWindow extends JFrame
{
    public AddRecipeWindow(Controller controller)
    {
        this.controller = controller;
        GroupLayout layout = new GroupLayout(getContentPane ());
        getContentPane ().setLayout (layout);

        enterName = new JLabel("Enter the name of the recipe");
        enterName.setFont(new Font ("Calibri", Font.BOLD, 20));

        name = new JTextField(15);

        chooseCategory1 = new JLabel("Choose the 1st category");
        chooseCategory1.setFont(new Font ("Calibri", Font.BOLD, 20));


        chicken = new JRadioButton("chicken");
        meat = new JRadioButton("meat");
        fish = new JRadioButton("fish");
        vegetable = new JRadioButton("vegetable");
        other1 = new JRadioButton("other");
        ButtonGroup bg1 =new ButtonGroup();
        bg1.add(chicken);
        bg1.add(meat);
        bg1.add(fish);
        bg1.add(vegetable);
        bg1.add(other1);

        chooseCategory2 = new JLabel("Choose the 2nd category");
        chooseCategory2.setFont(new Font ("Calibri", Font.BOLD, 20));

        soup = new JRadioButton("soup");
        pasta = new JRadioButton("pasta");
        salad = new JRadioButton("salad");
        dessert = new JRadioButton("dessert");
        pastries = new JRadioButton("pastries");
        other2 = new JRadioButton("other");
        ButtonGroup bg2 =new ButtonGroup();
        bg2.add(soup);
        bg2.add(pasta);
        bg2.add(salad);
        bg2.add(dessert);
        bg2.add(pastries);
        bg2.add(other2);

        addImage = new JLabel("Add the image");
        addImage.setFont(new Font ("Calibri", Font.BOLD, 20));

        image = new JTextField(15);
        image.setSize(new Dimension(400, 10));

        save = new JButton("Save");

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
                        .addComponent(addImage)
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
                        .addComponent(addImage)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(image)
                                .addComponent(save)));

        setSize(800,500);
        pack();
        setVisible(true);
    }
    private Controller controller;
    private JPanel panel;
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
    private JLabel addImage;
    private JTextField image;
    private JButton save;
}
