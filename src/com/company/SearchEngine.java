package com.company;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SearchEngine extends JFrame
{
    public SearchEngine(Controller controller)
    {
        this.controller = controller;

        allRecipesNames = new DefaultListModel<>();
        for(int i = 0; i < controller.allRecipes.size(); i++)
            // loops through all the recipes in the program and adds the name of all recipes to the
            // DefaultListModel allRecipeNames
        {
            String nameOfRecipe = controller.allRecipes.get(i).getName();
            allRecipesNames.addElement(nameOfRecipe);
        }
        name = new JList<String>(allRecipesNames);

        allRecipesCat1 = new DefaultListModel<>();
        for(int i = 0; i < controller.allRecipes.size(); i++)
            // loops through all the recipes in the program and adds the category 1 of all recipes to the
            // DefaultListModel allRecipeCat1
        {
            String nameOfCat1 = controller.allRecipes.get(i).getCategory1();
            allRecipesCat1.addElement(nameOfCat1);
        }
        cat1 = new JList<>(allRecipesCat1);

        allRecipesCat2 = new DefaultListModel<>();
        for(int i = 0; i < controller.allRecipes.size(); i++)
        // loops through all the recipes in the program and adds the category 2 of all recipes to the
        // DefaultListModel allRecipeCat2
        {
            String nameOfCat2 = controller.allRecipes.get(i).getCategory2();
            allRecipesCat2.addElement(nameOfCat2);
        }
        cat2 = new JList<>(allRecipesCat2);


        name.addListSelectionListener((ListSelectionEvent e) ->
        {
            // ListSelectionListener that opens the image file of the selected recipe
            String selectedRecipe = name.getSelectedValue().toString();
            try {
                controller.openImageOfSelectedRecipe2(selectedRecipe);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });



        JPanel resultPanel = new JPanel();
        resultPanel.setBackground(new Color(240, 228, 215));
        resultPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(300, 500));

        Color labelColor = new Color(199,187,188);
        Font labelFont = new Font ("Bookman Old Style", Font.BOLD, 13);
        JLabel nameText = new JLabel("name");

        nameText.setFont(labelFont);
        JLabel cat1Text = new JLabel("category 1");
        cat1Text.setFont(labelFont);
        JLabel cat2Text = new JLabel("category 2");
        cat2Text.setFont(labelFont);

        GroupLayout resultLayout = new GroupLayout(resultPanel);
        resultPanel.setLayout(resultLayout);

        resultLayout.setAutoCreateGaps(true);
        resultLayout.setAutoCreateContainerGaps(true);
        resultLayout.setHorizontalGroup(
                resultLayout.createSequentialGroup()
                        .addGroup(resultLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(nameText)
                                .addComponent(name))
                        .addPreferredGap (LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(resultLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(cat1Text)
                                .addComponent(cat1))
                        .addPreferredGap (LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(resultLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(cat2Text)
                                .addComponent(cat2)));
        resultLayout.setVerticalGroup(
                resultLayout.createSequentialGroup()
                        .addGroup(resultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(nameText)
                                .addComponent(cat1Text)
                                .addComponent(cat2Text))
                        .addGroup(resultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(name)
                                .addComponent(cat1)
                                .addComponent(cat2)));


        mainPanel = new JPanel();
        GroupLayout mainLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainLayout);

        search = new JTextField();
        search.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent evt)
            {
                searchTxtKeyReleased(evt);
            }
        });


        String[] choices = {"name","category 1", "category 2" };
        typeOfSearch = "name";
        JComboBox choose = new JComboBox(choices);
        choose.setBackground(new Color(240, 228, 215));
        choose.addActionListener((ActionEvent e)-> typeOfSearch = (String)choose.getSelectedItem());


        mainLayout.setAutoCreateGaps(true);
        mainLayout.setAutoCreateContainerGaps(true);
        mainLayout.setHorizontalGroup(
                mainLayout.createSequentialGroup()
                        .addComponent(choose)
                        .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(search)
                                .addComponent(scrollPane)));
        mainLayout.setVerticalGroup(
                mainLayout.createSequentialGroup()
                        .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(choose)
                                .addComponent(search))
                        .addComponent(scrollPane));

        mainPanel.setBackground(new Color(243,216, 209));
        add(mainPanel);
        setSize(600, 600);
        setVisible(true);
    }


    private void searchTxtKeyReleased(KeyEvent evt)
    {
        if (typeOfSearch.equals("name"))
            // typeOfSearch stores the type of search that has been choosen by the user through the combobox
            // it will define to which list (name, category 1 or category 2) the users search will match and
            // which of the list will be filtered first
        {
            searchFilter(search.getText(), name, allRecipesNames, cat1, allRecipesCat1, cat2, allRecipesCat2);
        }
        else if (typeOfSearch.equals("category 1"))
        {
            searchFilter(search.getText(), cat1, allRecipesCat1, name, allRecipesNames, cat2, allRecipesCat2);
        }
        else
        {
            searchFilter(search.getText(), cat2, allRecipesCat2, name, allRecipesNames, cat1, allRecipesCat1);
        }
    }


    private void searchFilter(String searchTerm, JList displayOfSearchedList, DefaultListModel<String> fullSearchedList,
                              JList displayOfSecondList, DefaultListModel<String> fullSecondList,
                              JList displayOfThirdList, DefaultListModel<String> fullThirdList)
    // method that first filters all the 3 list at the same time according to the users search term
    {
        DefaultListModel filteredItemsSearchedList = new DefaultListModel();
        // creates a new default list model that will only contain the elements corresponding to the search term
        ArrayList<Integer> indicesOfAddedRecipes = new ArrayList<>();
        // creates an array list containing the indeces of all the recipes added to the filteredItemsSearchedList
        // which will enable to create the filtered items list of the two other lists

        if (!searchTerm.equals(""))
        {
            int length = searchTerm.length();
            for (int i = 0; i < fullSearchedList.size(); i++)
            // iterates through all the recipes
            {
                String r = fullSearchedList.get(i);
                if (r.length() >= length)
                {
                    String checkIfSame = r.substring(0, length).toLowerCase();
                    // creates a substring of the string at index i which has the length of the search term
                    if (searchTerm.toLowerCase().equals(checkIfSame))
                        // if substring and the search term are equal the string is added to the filtered items list
                        // and the index of that string is stored to the index array list
                    {
                        filteredItemsSearchedList.addElement(r);
                        indicesOfAddedRecipes.add(i);
                    }
                }
            }
            DefaultListModel filteredItemsSecondList = SwitchingOtherListsAtTheSameTimeAsTheSearchedList(indicesOfAddedRecipes, fullSecondList);
            DefaultListModel filteredItemsThirdList = SwitchingOtherListsAtTheSameTimeAsTheSearchedList(indicesOfAddedRecipes, fullThirdList);
            // calls method that creates the filtered list for the 2 other lists using the arraylist with the indeces of the elements
            // added to the already filtered list
            if (displayOfSearchedList.equals(cat1))
                groupingRecipesIntoTheOptionsOfACategory(filteredItemsThirdList, displayOfThirdList,
                        filteredItemsSecondList, displayOfSecondList, filteredItemsSearchedList, displayOfSearchedList, 6);
            else if (displayOfSearchedList.equals(cat2))
                groupingRecipesIntoTheOptionsOfACategory(filteredItemsThirdList, displayOfThirdList,
                        filteredItemsSecondList, displayOfSecondList, filteredItemsSearchedList, displayOfSearchedList, 5);
            else
                {
                    displayOfSearchedList.setModel(filteredItemsSearchedList);
                    displayOfSecondList.setModel(filteredItemsSecondList);
                    displayOfThirdList.setModel(filteredItemsThirdList);
                }
        }
        else
            // if the search term is empty the default list models containing all of the recipes will be set
            // to the displayed lists
            {
            displayOfSearchedList.setModel(fullSearchedList);
            displayOfSecondList.setModel(fullSecondList);
            displayOfThirdList.setModel(fullThirdList);
            }
    }

   private DefaultListModel SwitchingOtherListsAtTheSameTimeAsTheSearchedList(ArrayList<Integer> indicesOfAddedRecipe, DefaultListModel<String> allRecipes)
   {
       DefaultListModel filteredItems = new DefaultListModel();
       for (int x = 0; x < indicesOfAddedRecipe.size(); x++)
       {
           filteredItems.addElement(allRecipes.get(indicesOfAddedRecipe.get(x)));
       }
       return filteredItems;
   }



    private void groupingRecipesIntoTheOptionsOfACategory(DefaultListModel<String> ListToSort, JList displayOfSortedList, DefaultListModel secondList,
                                                          JList displayOfSecondList, DefaultListModel thirdList, JList displayOfThirdList, int numberOfOptionInCategory)
    {
        int startOfOption = 0;
        for(int i = 0; i < numberOfOptionInCategory || i < ListToSort.size(); i++)
            // will loop for ever cause i have not registered 6 recipes with different categories
        {
            if(startOfOption < ListToSort.size())
            {
                String option = ListToSort.get(startOfOption);
                int placeToSwitch = startOfOption + 1;
                for (int x = placeToSwitch; x < ListToSort.size(); x++)
                {
                    if (ListToSort.get(x).equals(option))
                    {
                        SwitchingPlaceOfElement(ListToSort, x, placeToSwitch);
                        SwitchingPlaceOfElement(secondList, x, placeToSwitch);
                        SwitchingPlaceOfElement(thirdList, x, placeToSwitch);
                        placeToSwitch++;
                    }
                }
                startOfOption = placeToSwitch;
            }
        }
        displayOfSortedList.setModel(ListToSort);
        displayOfSecondList.setModel(secondList);
        displayOfThirdList.setModel(thirdList);
    }

    private void SwitchingPlaceOfElement(DefaultListModel<String> list, int currentPlace, int newPlace)
    {
        String elementToSwitch = list.get(currentPlace);
        list.removeElementAt(currentPlace);
        list.insertElementAt(elementToSwitch, newPlace);
    }




    private JPanel mainPanel;
    private Controller controller;
    private String typeOfSearch;
    private DefaultListModel allRecipesNames;
    private DefaultListModel allRecipesCat1;
    private DefaultListModel allRecipesCat2;
    private JList name;
    private JList cat1;
    private JList cat2;
    private JTextField search;
}

