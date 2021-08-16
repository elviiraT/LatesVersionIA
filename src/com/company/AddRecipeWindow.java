package com.company;
import javax.swing.*;
import java.awt.*;

public class AddRecipeWindow extends JFrame
{
    public AddRecipeWindow(Controller controller)
    {
        this.controller = controller;
        panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        add(panel);

        enterName = new JLabel("Enter the name of the recipe");
        enterName.setFont(new Font ("Calibri", Font.BOLD, 20));

        name = new JTextField(15);
        name.setSize(400,10);

        chooseCategory1 = new JLabel("Choose the 1st category");
        chooseCategory1.setFont(new Font ("Calibri", Font.BOLD, 20));


        chicken = new JCheckBox("chicken");
        meat = new JCheckBox("meat");
        fish = new JCheckBox("fish");
        vegetable = new JCheckBox("vegetable");
        other1 = new JCheckBox("other");

        chooseCategory2 = new JLabel("Choose the 2nd category");
        chooseCategory2.setFont(new Font ("Calibri", Font.BOLD, 20));

        soup = new JCheckBox("soup");
        pasta = new JCheckBox("pasta");
        salad = new JCheckBox("salad");
        desert = new JCheckBox("desert");
        pastries = new JCheckBox("pastries");
        other2 = new JCheckBox("other");

        addImage = new JLabel("Add the image");
        addImage.setFont(new Font ("Calibri", Font.BOLD, 20));

        image = new JTextField(15);
        image.setSize(new Dimension(400, 10));

        save = new JButton("Save");
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(enterName)
                        .addComponent(name,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chooseCategory1)
                        .addComponent(chicken)
                        .addGroup(layout.createSequentialGroup())
                        .addComponent(meat)
                        .addComponent(fish)
                        .addComponent(vegetable)
                        .addComponent(other1)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(chooseCategory2)
                                .addComponent(soup))
                        .addComponent(pasta)
                        .addComponent(salad)
                        .addComponent(desert)
                        .addComponent(pastries)
                        .addComponent(other2)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(addImage)
                                .addComponent(image,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(150)
                        .addComponent(save)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addComponent(enterName)
                        .addComponent(name,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                .addComponent(desert)
                                .addComponent(pastries)
                                .addComponent(other2))
                        .addComponent(addImage)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(image,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(150)
                                .addComponent(save))
        );

        setSize(800,500);
        setVisible(true);
    }
    private Controller controller;
    private JPanel panel;
    private JLabel enterName;
    private JTextField name;
    private JLabel chooseCategory1;
    private JLabel chooseCategory2;
    private JCheckBox chicken;
    private JCheckBox meat;
    private JCheckBox fish;
    private JCheckBox vegetable;
    private JCheckBox other1;
    private JCheckBox soup;
    private JCheckBox pasta;
    private JCheckBox salad;
    private JCheckBox desert;
    private JCheckBox pastries;
    private JCheckBox other2;
    private JLabel addImage;
    private JTextField image;
    private JButton save;
}
