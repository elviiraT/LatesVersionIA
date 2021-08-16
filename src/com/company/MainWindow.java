package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import javax.swing.table.TableCellRenderer;

public class MainWindow extends JFrame
{
    public MainWindow(Controller controller)
    {
        this.controller = controller;
        main = new JPanel();
        main.setLayout (new BorderLayout());
        add(main);
        upperB = new JPanel ();
        upperB.setLayout (new FlowLayout());
        main.add(upperB, BorderLayout.NORTH);
        leftB = new JPanel ();
        leftB.setLayout (new FlowLayout());
        main.add(leftB, BorderLayout.WEST);
        rightB = new JPanel ();
        main.add(rightB, BorderLayout.EAST);
        center = new JPanel();
        center.setLayout(new FlowLayout());
        main.add(center, BorderLayout.CENTER);
        table = new JPanel();
        table.setLayout(new FlowLayout());
        main.add(table, BorderLayout.SOUTH);


        b1 = new JButton("Add new recipe");
        b1.setBounds(100, 100, 200, 30);
        upperB.add(b1);
        b1.setBackground(Color.GREEN);
        b2 = new JButton("Search for a recipe");
        b2.setBounds(100, 100, 200, 30);
        b2.setBackground(Color.CYAN);
        upperB.add(b2);

        text = new JLabel("Current Week");
        text.setFont(new Font ("Calibri", Font.BOLD, 35));

        center.add(text);

        b3 = new JButton("<");
        b3.setBounds(100, 100, 200, 30);
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.WHITE);
        leftB.add(b3);

        b4 = new JButton(">");
        b4.setBounds(100, 100, 200, 30);
        b4.setBackground(Color.BLACK);
        b4.setForeground(Color.WHITE);
        rightB.add(b4);

        String[] columnNames = {"    Monday",
                "   Tuesday",
                " Wednesday",
                "   Thursday",
                "     Friday", "   Saturday", "    Sunday"};

        model = new DefaultTableModel(columnNames, 4);
        calendar = new JTable(model);
        calendar.setSize(800, 350);
        calendar.setRowHeight(0, 50);
        calendar.setRowHeight(1, 50);
        calendar.setRowHeight(2, 200);
        calendar.setRowHeight(3, 50);
        button = new JButton ("Add");
        scrollpane = new ScrollPane();
        scrollpane.setSize(50,40);
        calendar.setValueAt(scrollpane, 1,0);

        calendar.setEnabled(false);
        for (int x = 0; x < 7; x++)
        {
            calendar.setValueAt(columnNames[x], 0, x);
        }
        table.add(calendar);

        setSize(800,500);
        setVisible(true);
    }
    private JButton b1;
    private JButton b2;
    private JPanel main;
    private JPanel upperB;
    private JPanel leftB;
    private JPanel rightB;
    private JPanel table;
    private JPanel center;
    private JLabel text;
    private JButton b3;
    private JButton b4;
    private JButton button;
    private JTable calendar;
    private TableCellRenderer buttonRenderer;
    private Controller controller;
    private DefaultTableModel model;
    private ScrollPane scrollpane;
}

