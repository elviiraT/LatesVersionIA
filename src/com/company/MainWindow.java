package com.company;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame
{
    public MainWindow (Controller controller, String title,Week w)
    {
        this.controller = controller;
        // application data: list of lists
        recipeList = new LinkedList<>();

        for (int x = 0; x < 7; x++)
        {
            recipeList.add(w.getDailyRecipe(x).getRecipes());// gets the recipes of the Weeks w DailyRecipes Recipes list for each day
        }
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        // MVC-style table model that will return properties of data shown
        // in table; each list is shown in the column of the table
        tableModel = new AbstractTableModel()
        {
            public int getColumnCount() {
                return recipeList.size();
            }

            public int getRowCount() {
                int max = 0;
                for (List<Recipe> list : recipeList)
                    max = Math.max(max, list.size());
                return max;
            }

            public Object getValueAt(int row, int column)
            {
                List<Recipe> list = recipeList.get(column);
                if (row < list.size())
                    return list.get(row);
                else
                    return null;
            }

            public String getColumnName(int column) {
                return daysOfWeek[column];
            }

        };

        // create table
        table = new JTable(tableModel);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true); // allow column selection only
        JScrollPane scrollPane = new JScrollPane(table);

        // menu bar with one item for adding data to a column
        JMenuBar menuBar = new JMenuBar();
        JMenu columnMenu = new JMenu("Change calendar");
        JMenuItem add = new JMenuItem("Add a recipe");
        JMenuItem suggest = new JMenuItem("Suggest a recipe");
        suggest.addActionListener((ActionEvent e)-> controller.constructSuggestionSetups());
        JMenuItem view = new JMenuItem("View a recipe");
        JMenuItem delete = new JMenuItem("Delete a recipe");
        add.addActionListener((ActionEvent e) -> addData(w));
        delete.addActionListener((ActionEvent e) -> RemoveData(w));
        view.addActionListener((ActionEvent e) -> ViewImage(w));
        columnMenu.add(add);
        columnMenu.add(suggest);
        columnMenu.add(view);
        columnMenu.add(delete);
        menuBar.add(columnMenu);
        setJMenuBar(menuBar);

        JMenu addRecipe = new JMenu("Add a new recipe");
        menuBar.add(addRecipe);
        JMenuItem add2 = new JMenuItem("Add a new recipe");
        add2.addActionListener((ActionEvent e) -> controller.constructAddRecipe());
        addRecipe.add(add2);


        JMenu searchRecipe = new JMenu("Search for a recipe");
        menuBar.add(searchRecipe);
        JMenuItem search = new JMenuItem("Search a recipe");
        search.addActionListener((ActionEvent e) -> controller.constructSearchEngine());
        searchRecipe.add(search);


        JLabel text = new JLabel(title);
        text.setFont(new Font("Calibri", Font.BOLD, 35));

        JButton left = new JButton("<");
        left.addActionListener((ActionEvent e) ->
        {
            if (text.getText() == c)
            {
                controller.constructMainWindow(p, controller.weeks[0]);
                dispose();
            }
            else if (text.getText() == n)
            {
                controller.constructMainWindow(c, controller.weeks[1]);
                dispose();
            }
        });
        left.setBounds(100, 100, 200, 30);
        left.setBackground(Color.BLACK);
        left.setForeground(Color.WHITE);

        JButton right = new JButton(">");
        right.setBounds(100, 100, 200, 30);
        right.addActionListener((ActionEvent e) ->
        {
            if (text.getText() == c)
            {
                controller.constructMainWindow(n, controller.weeks[2]);
                dispose();
            }
            else if (text.getText() == p)
            {
                controller.constructMainWindow(c, controller.weeks[1]);
                dispose();
            }
        });
        right.setBackground(Color.BLACK);
        right.setForeground(Color.WHITE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(left)
                                .addPreferredGap (LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(text)
                                .addPreferredGap (LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(right))
                        .addComponent(scrollPane));
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(left)
                                .addComponent(text)
                                .addComponent(right))
                        .addGap(50, 50, 50)
                        .addComponent(scrollPane));


        setSize(1000, 650);
        setVisible(true);
    }


    private void addData (Week w)
    {
        int column = table.getSelectedColumn (); // returns -1 if none selected
        if (column >= 0)
        {
            controller.constructEnterNameOfRecipe(w, column);// passes the week in question to the constructEnterNameOfRecipe so it knows where to add the recipe
        }
    }

    private void RemoveData (Week w)
    {
            int column = table.getSelectedColumn();
            int row = table.getSelectedRow();
            controller.RemoveRecipeFromCalendar(w,column,row);
    }

    private void ViewImage(Week w)
    {
        int column = table.getSelectedColumn();
        int row = table.getSelectedRow();
        Recipe r = w.getDailyRecipe(column).getRecipe(row);
        controller.constructDisplayImage(controller.SearchRecipeImagePath(r));
        System.out.println(r);
    }


    public void UpdateCalendar()
    {
        tableModel.fireTableDataChanged ();
    }

    private Controller controller;
    private JTable table;
    private AbstractTableModel tableModel;
    List<List<Recipe>> recipeList;// data is list of lists of strings
    private String [] daysOfWeek;
    private String p = "Past Week";
    private String c = "Current Week";
    private String n = "Next Week";
}

