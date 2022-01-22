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
                controller.openImageOfSelectedRecipe(selectedRecipe);
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


        JPanel mainPanel = new JPanel();
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
            // typeOfSearch stores the type of search that has been chosen by the user through the combobox
            // it will define to which list (name, category 1 or category 2) the user's search will be matched and
            // according to which list the items will be filtered
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
    // method that first filters the list which is searched according to the user's search term
    // then it calls a method which creates a filtered list for the 2 other lists
    // finally if the user searches one of the category lists a method which groups the lists according to the
    // options of the other list is called
    {
        DefaultListModel filteredItemsSearchedList = new DefaultListModel();
        // creates a new default list model that will only contain the elements corresponding to the search term
        ArrayList<Integer> indicesOfAddedRecipes = new ArrayList<>();
        // creates an array list containing the indices of all the recipes added to the filteredItemsSearchedList
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
            DefaultListModel filteredItemsSecondList = switchingListsConcurrently(indicesOfAddedRecipes, fullSecondList);
            DefaultListModel filteredItemsThirdList = switchingListsConcurrently(indicesOfAddedRecipes, fullThirdList);
            // calls method that creates the filtered list for the 2 other lists using the arraylist with the indices of the elements
            // added to the already filtered list
            if (displayOfSearchedList.equals(cat1))
                groupingRecipesIntoTheOptionsOfACategory(filteredItemsThirdList, displayOfThirdList,
                        filteredItemsSecondList, displayOfSecondList, filteredItemsSearchedList, displayOfSearchedList, 6);
            // If searched list is category1 a method that groups the recipes according to category2 is called
                // and then sets the filtered lists to the displayed lists is called
            else if (displayOfSearchedList.equals(cat2))
                groupingRecipesIntoTheOptionsOfACategory(filteredItemsThirdList, displayOfThirdList,
                        filteredItemsSecondList, displayOfSecondList, filteredItemsSearchedList, displayOfSearchedList, 5);
            // If searched list is category2 a method that groups the recipes according to category1
                // and then sets the filtered lists to the displayed lists is called
            else
            // If the searched list is name the filtered lists will be set to the displayed lists
                {
                    displayOfSearchedList.setModel(filteredItemsSearchedList);
                    displayOfSecondList.setModel(filteredItemsSecondList);
                    displayOfThirdList.setModel(filteredItemsThirdList);
                }
        }
        else
            // if the search term is empty the default list models containing all recipes will be set
            // to the displayed lists
            {
            displayOfSearchedList.setModel(fullSearchedList);
            displayOfSecondList.setModel(fullSecondList);
            displayOfThirdList.setModel(fullThirdList);
            }
    }


   private DefaultListModel switchingListsConcurrently(ArrayList<Integer> indicesOfAddedRecipe, DefaultListModel<String> allRecipes)
   {
       DefaultListModel filteredItems = new DefaultListModel(); // creates the new filtered list
       for (int x = 0; x < indicesOfAddedRecipe.size(); x++)
       // loops through the array with the indices of the items that have to be added to the list and adds them
       {
           filteredItems.addElement(allRecipes.get(indicesOfAddedRecipe.get(x)));
       }
       return filteredItems;
   }



    private void groupingRecipesIntoTheOptionsOfACategory(DefaultListModel <String> ListToSort, JList displayOfSortedList, DefaultListModel secondList,
                                                          JList displayOfSecondList, DefaultListModel thirdList, JList displayOfThirdList, int numberOfOptionsInCategory)
            // Groups the recipes according to the options in ListToSort which may contain the options for category 1 or 2
    {
        int startOfOption = 0; // defines were a new option starts
        for(int i = 0; i < numberOfOptionsInCategory || i < ListToSort.size(); i++)
            // loop while there are options (5 for category 1 and 6 for category 2) or until the there are no more elements in ListToSort
        {
            if(startOfOption < ListToSort.size()) //checks that there are still elements to sort in ListToSort
            {
                String option = ListToSort.get(startOfOption); //defines the option which will start at startOfOption
                int placeToSwitch = startOfOption + 1;
                for (int x = placeToSwitch; x < ListToSort.size(); x++)
                {
                    if (ListToSort.get(x).equals(option))
                        // compares element at index x to the option
                        // if it is equal the element will be switched to PlaceToSwitch and 1 will be added to
                        // PlaceToSwitch so the next element is switched to the next index
                        // the same switching process will be done to all the lists (name, cat1 and cat2)
                    {
                        switchingPlaceOfElement(ListToSort, x, placeToSwitch);
                        switchingPlaceOfElement(secondList, x, placeToSwitch);
                        switchingPlaceOfElement(thirdList, x, placeToSwitch);
                        placeToSwitch++;
                    }
                }
                startOfOption = placeToSwitch;
                // the start of a new option is at placeToSwitch once there are no more elements to be switched
            }
        }
        displayOfSortedList.setModel(ListToSort);
        displayOfSecondList.setModel(secondList);
        displayOfThirdList.setModel(thirdList);
    }

    private void switchingPlaceOfElement(DefaultListModel<String> list, int currentPlace, int newPlace)
    // switches the place of elements at index currentPlace and newPlace in list
    {
        String elementToSwitch = list.get(currentPlace);
        list.removeElementAt(currentPlace);
        list.insertElementAt(elementToSwitch, newPlace);
    }




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

