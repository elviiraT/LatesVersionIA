package com.company;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;

public class CalendarView extends JFrame
{
    public CalendarView(Controller controller, String title, Week w)
    {
        this.controller = controller;


        recipeList = new LinkedList<>();
        for (int x = 0; x < 7; x++)
        {
            recipeList.add(w.getDailyRecipe(x));// gets the recipes of the Weeks w DailyRecipes Recipes list for each day
        }
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        tableModel = new AbstractTableModel()
        {
            public int getColumnCount() {return recipeList.size();}
            public int getRowCount() {
                int max = 1;
                // The table always has at least one row, if the dailyRecipe does not
                // contain recipes the text "Click to select" is added to the first row
                for (List<Recipe> list : recipeList)
                    //iteraates through each dailyRecipe list and sets the largest size as max
                    max = Math.max(max, list.size());
                return max;
            }

            public Object getValueAt(int row, int column)
            {
                List<Recipe> list = recipeList.get(column);
                if (list.isEmpty()&& row == 0)
                    return "Click to select";
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
        Color tableColor = new Color(240, 228, 215);
        table.setBackground(tableColor);
        table.setFillsViewportHeight(true);
        Color tableHeaderColor = new Color(199,187,188);
        table.getTableHeader().setBackground(tableHeaderColor);
        table.getTableHeader().setFont(new Font ("Bookman Old Style", Font.ITALIC | Font.BOLD, 15));
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        JScrollPane scrollPane = new JScrollPane(table);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(tableColor);

        JMenu calendar = new JMenu("Change calendar");
        calendar.setFont(new Font ("Bookman Old Style", Font.ITALIC, 15));

        JMenuItem add = new JMenuItem("Add a recipe");
        add.setFont(new Font ("Bookman Old Style", Font.ITALIC, 15));
        add.setBackground(tableColor);
        add.addActionListener((ActionEvent e) -> addData(w));

        JMenuItem suggest = new JMenuItem("Suggest a recipe");
        suggest.setFont(new Font ("Bookman Old Style", Font.ITALIC, 15));
        suggest.setBackground(tableColor);
        suggest.addActionListener((ActionEvent e)-> suggestARecipe(w));

        JMenuItem view = new JMenuItem("View a recipe");
        view.setFont(new Font ("Bookman Old Style", Font.ITALIC, 15));
        view.setBackground(tableColor);
        view.addActionListener((ActionEvent e) -> viewImage(w));

        JMenuItem delete = new JMenuItem("Delete a recipe");
        delete.setFont(new Font ("Bookman Old Style", Font.ITALIC, 15));
        delete.setBackground(tableColor);
        delete.addActionListener((ActionEvent e) -> removeData(w));

        calendar.add(add);
        calendar.add(suggest);
        calendar.add(view);
        calendar.add(delete);
        menuBar.add(calendar);
        setJMenuBar(menuBar);

        JMenu addRecipe = new JMenu("Add a new recipe");
        addRecipe.setFont(new Font ("Bookman Old Style", Font.ITALIC, 15));
        addRecipe.setBackground(tableColor);
        menuBar.add(addRecipe);

        JMenuItem add2 = new JMenuItem("Add a new recipe");
        add2.setFont(new Font ("Bookman Old Style", Font.ITALIC, 15));
        add2.setBackground(tableColor);
        add2.addActionListener((ActionEvent e) -> controller.constructAddRecipe());
        addRecipe.add(add2);


        JMenu searchRecipe = new JMenu("Search for a recipe");
        searchRecipe.setFont(new Font ("Bookman Old Style", Font.ITALIC, 15));
        searchRecipe.setBackground(tableColor);
        menuBar.add(searchRecipe);

        JMenuItem search = new JMenuItem("Search a recipe");
        search.setBackground(tableColor);
        search.setFont(new Font ("Bookman Old Style", Font.ITALIC, 15));
        search.addActionListener((ActionEvent e) -> controller.constructSearchEngine());
        searchRecipe.add(search);


        JLabel text = new JLabel(title);
        text.setFont(new Font("Castellar",  Font.PLAIN, 35));
        JButton left = new JButton("<");
        left.addActionListener((ActionEvent e) ->
        {
            // changes the calendar view the preceding week
            // checks whether the user is in the current or next calendar view so it constructs
            // the right preceding calendar view
            if (text.getText().equals(current))
            {
                controller.constructCalendarView(past, controller.weeks[0]);
                dispose();
            }
            else if (text.getText().equals(next))
            {
                controller.constructCalendarView(current, controller.weeks[1]);
                dispose();
            }
        });
        left.setBounds(100, 100, 200, 30);
        left.setBackground(tableHeaderColor);


        JButton right = new JButton(">");
        right.setBounds(100, 100, 200, 30);
        right.addActionListener((ActionEvent e) ->
        {
            // changes the calendar view the following week
            // checks whether the user is in the past or current calendar view so it constructs
            // the right following calendar view
            if (text.getText().equals(current))
            {
                controller.constructCalendarView(next, controller.weeks[2]);
                dispose();
            }
            else if (text.getText().equals(past))
            {
                controller.constructCalendarView(current, controller.weeks[1]);
                dispose();
            }
        });
        right.setBackground(tableHeaderColor);


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
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(left)
                                .addComponent(text)
                                .addComponent(right))
                        .addGap(30, 30, 30)
                        .addComponent(scrollPane));


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                //saves the data when the calendarView window is closed
                controller.saveData();
            }});


        Color backgroundColor = new Color(243,216, 209);
        getContentPane().setBackground(backgroundColor);
        setSize(1000, 650);
        setVisible(true);

    }


    private void addData (Week w)
    {
        int column = table.getSelectedColumn ();
        // gets the index of the selected column because it corresponds to the place of the day in the week
        // returns -1 if none selected
        if (column >= 0)
        {
            controller.constructAddRecipeToCalendar(w, column);
            // passes the week and the place of the day in question to the constructAddRecipeToCalendar so it
            // knows where to add the recipe that will be entered by the user
        }
    }

    private void removeData (Week w)
    {
        int column = table.getSelectedColumn();
        // gets the selected column of the table since it corresponds to the place of
        // the linked list of the day in the week's array
        int row = table.getSelectedRow();
        // gets the selected row since it corresponds to the index of the selected recipe in the
        // linked list of that day
        controller.removeRecipeFromCalendar(w,column,row);
        // calls a method in controller that removes the selected recipe
        // week, row and column are passed in the parameters of this method to situate the place of the
        // recipe in question
        updateCalendar();
    }

    private void viewImage(Week w)
    // opens image file by calling a method in the Controller class
    {
        int column = table.getSelectedColumn();
        int row = table.getSelectedRow();
        Recipe r = w.getDailyRecipe(column).get(row);
        try {
            controller.openImageOfSelectedRecipe(r.getName());
            // calls method in the Controller class which opens the image file of the selected recipe
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void suggestARecipe(Week w)
    // method that constructs the SuggestionSetups
    {
        int placeOfDay = table.getSelectedColumn();
        // the selected column identifies the place of the day in the week
        if (placeOfDay > -1)
            controller.constructSuggestionSetups(w, placeOfDay);
            // when the SuggestionSetups window is created the week and the place of the day are passed to it the parameters
            // of the method so that when the suggestion list is creates the past recipes can be identified
    }


    public void updateCalendar()
    {
        tableModel.fireTableDataChanged ();
    }

    private Controller controller;
    private JTable table;
    private AbstractTableModel tableModel;
    private List<List<Recipe>> recipeList;// data is list of lists of strings
    private final String past = "Past Week";
    private final String current = "Current Week";
    private final String next = "Next Week";
}

