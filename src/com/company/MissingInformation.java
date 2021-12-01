package com.company;

import javax.swing.*;
import java.awt.*;

public class MissingInformation extends JFrame
{
    public MissingInformation(Controller controller)
    {
        this.controller = controller;
        JLabel text = new JLabel("Enter all information");
        text.setFont(new Font("Bookman Old Style", Font.PLAIN, 18));

        JPanel panel = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setVgap(47);
        panel.setLayout(layout);
        panel.add(text);
        add(panel);

        Color backgroundColor = new Color(243, 216, 209);
        panel.setBackground(backgroundColor);
        setVisible(true);
        setSize(250, 150);
    }

    private Controller controller;
}
