package com.company;
import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.border.TitledBorder;

public class SearchEngine extends JFrame
{
    public SearchEngine(Controller controller)
    {
        this.controller = controller;

        l = new DefaultListModel<>();
        for(String val : getRecipeNames())
            l.addElement(val);
        name = new JList(l);


        l1 = new DefaultListModel<>();
        for(String val1 : getRecipeCat1())
            l1.addElement(val1);
        cat1 = new JList<>(l1);

        l2 = new DefaultListModel<>();
        for(String val2 : getRecipeCat2())
            l2.addElement(val2);
        cat2 = new JList<>(l2);

        resultPanel = new JPanel();
        resultPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        scrollPane = new JScrollPane(resultPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(300, 500));

        JLabel nameText = new JLabel("name");
        JLabel cat1Text = new JLabel("category 1");
        JLabel cat2Text = new JLabel("category 2");

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
        choose = new JComboBox(choices);
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

        add(mainPanel);
        setSize(600, 600);
        setVisible(true);
    }


    private ArrayList<String> getRecipeNames()
    {
        ArrayList RecipeNames = new ArrayList<>();
        for(int i = 0; i < controller.allRecipes.size(); i++)
        {
            String nameOfRecipe = controller.allRecipes.get(i).getName();
            RecipeNames.add(nameOfRecipe);
        }
        return RecipeNames;
    }

    private ArrayList<String> getRecipeCat1()
    {
        ArrayList RecipeCat1 =new ArrayList();
        for(int i = 0; i < controller.allRecipes.size(); i++)
        {
            String nameOfCat1 = controller.allRecipes.get(i).getCategory1();
            RecipeCat1.add(nameOfCat1);
        }
        return RecipeCat1;
    }

    private ArrayList<String> getRecipeCat2()
    {
        ArrayList RecipeCat2 =new ArrayList();
        for(int i = 0; i < controller.allRecipes.size(); i++)
        {
            String nameOfCat2 = controller.allRecipes.get(i).getCategory2();
            RecipeCat2.add(nameOfCat2);
        }
        return RecipeCat2;
    }

    private JList getSearchedList(String typeOfSearch) //return the list according to which the user is trying to find a recipe
    {
        if (typeOfSearch.equals("name"))
            return name;
        else if(typeOfSearch.equals("category 1"))
            return cat1;
        else
            return cat2;
    }

    private DefaultListModel getSearchedListModel(String typeOfSearch) //return the list according to which the user is trying to find a recipe
    {
        if (typeOfSearch.equals("name"))
            return l;
        else if(typeOfSearch.equals("category 1"))
            return l1;
        else
            return l2;
    }


    private void searchFilter(String searchTerm, JList displayOfSearchedList, ArrayList<String> searchedList, DefaultListModel fullSearchedList,
                              JList displayOfSecondList, ArrayList<String> secondList, DefaultListModel fullSecondList,
                              JList displayOfThirdList, ArrayList<String> thirdList, DefaultListModel fullThirdList)
    {
        DefaultListModel filteredItemsSearchedList = new DefaultListModel();
        ArrayList<Integer> indicesOfAddedRecipes = new ArrayList<>();

        if (!searchTerm.equals(""))
        {
            int length = searchTerm.length();
            for (int i = 0; i < searchedList.size(); i++)
            {
                String r = searchedList.get(i);
                if (r.length()> length)
                {
                    String checkIfSame = r.substring(0, length);
                    if (searchTerm.toLowerCase().equals(checkIfSame))
                    {
                        filteredItemsSearchedList.addElement(r);
                        indicesOfAddedRecipes.add(i);
                    }
                }
            }
            displayOfSearchedList.setModel(filteredItemsSearchedList);
            SwitchingOtherListsAtTheSameTimeAsTheSearchedList(indicesOfAddedRecipes, displayOfSecondList, secondList);
            SwitchingOtherListsAtTheSameTimeAsTheSearchedList(indicesOfAddedRecipes, displayOfThirdList, thirdList);
        }
        else
            {
            displayOfSearchedList.setModel(fullSearchedList);
            displayOfSecondList.setModel(fullSecondList);
            displayOfThirdList.setModel(fullThirdList);
            }
    }

   private void SwitchingOtherListsAtTheSameTimeAsTheSearchedList(ArrayList<Integer> indicesOfAddedRecipe,
                                                                  JList DisplayOfList, ArrayList<String> allRecipes)
   {
       DefaultListModel filteredItems = new DefaultListModel();
       for (int x = 0; x < indicesOfAddedRecipe.size(); x++)
       {
           filteredItems.addElement(allRecipes.get(indicesOfAddedRecipe.get(x)));
       }
       DisplayOfList.setModel(filteredItems);
   }

    private void searchTxtKeyReleased(KeyEvent evt)
    {
        if (typeOfSearch.equals("name"))
        {
            searchFilter(search.getText(), name, getRecipeNames(), l, cat1, getRecipeCat1(), l1, cat2,getRecipeCat2(), l2);
        }
        else if (typeOfSearch.equals("category 1"))
        {
            searchFilter(search.getText(), cat1, getRecipeCat1(), l1, name, getRecipeNames(), l, cat2, getRecipeCat2(), l2);
        }
        else
        {
            searchFilter(search.getText(), cat2, getRecipeCat2(), l2, name, getRecipeNames(), l, cat1, getRecipeCat1(), l1);
        }
    }

    private JPanel mainPanel;
    private Controller controller;
    private JScrollPane scrollPane;
    private JPanel resultPanel;
    private JComboBox choose;
    private String typeOfSearch;
    //private String enteredSearch;
    private DefaultListModel l;
    private DefaultListModel l1;
    private DefaultListModel l2;
    private JList name;
    private JList cat1;
    private JList cat2;
    private JTextField search;
}

