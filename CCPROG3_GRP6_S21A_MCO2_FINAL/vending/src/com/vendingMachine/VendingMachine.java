/**
 * This class is used for the GUI and logic
 * that will be used in the vending machine
 *  @author Miguel
 *  @author Yumul
 */

/* Header Declarations */
package com.vendingMachine;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import com.regular.Regular;
import com.special.Special;
import com.special.Cake;

public class VendingMachine {

    /* fields and attributes */
    private JFrame mainFrame;//the frame
    private JButton[] startButtons = new JButton[3];//starting buttons 
    private JPanel startScreen = new JPanel();//screen panel for the staring screen
    private JPanel mainScreen = new JPanel(); //screen panel for the VM screen - features, maintenence
    private JPanel vmFeatureScreen = new JPanel();// screen panel for the VM features
    private JPanel vmMaintenanceScreen = new JPanel();// screen panel for the VM maintenance
    private JPanel mtcRefillItems = new JPanel();
    private JPanel mtcRefillDenom = new JPanel();
    private JButton[] itemButtons = new JButton[8];//item and slot buttons
    private JButton[] denomButtons = new JButton[8];//money buttons
    private JButton dispenseButton = new JButton("Dispense");//button for dispensing
    private JButton cancelButton = new JButton("Cancel");//cancel and reset everything
    private JButton returnBtn = new JButton("Exit");//return to previous page 
    private JLabel dispenseMsg = new JLabel("Waiting...");//print dispensing 
    private JLabel moneyAmount = new JLabel();//text of amount of mney inserted
    private JLabel moneyNeed = new JLabel();//text of amount of money needed for change
    private JLabel itemPicked = new JLabel();//text of item picked 
    private JLabel flavorPicked = new JLabel();
    private JLabel[] regVendingInfo = new JLabel[8];//info of items in reg - amount, price, calories
    private JButton[] mtcItemButtons = new JButton[8];//buttons for the slots
    private JButton[] mtcDenomButtons = new JButton[8];//buttons for the denominations
    private JButton[] spclFlavorButtons = new JButton[5];
    private JLabel[] spclVendingInfo = new JLabel[5];
    private JLabel[] mtcDenomInfo = new JLabel[8];
    private JTextField textBox = new JTextField();
    private JLabel spclTotalPrice = new JLabel();
    private JLabel spclTotalCal = new JLabel();

    private String strTempText;//needed for getting set integer into actionlistener
    private int nMoneyInserted = 0; //inserted money
    private String strItemSelected = new String();//selected item
    private String strFlavorSelected = new String();
    private int nItemIndex = -1;
    private int nCurrentVendingMachine = 0; //current vending machine your in, 1 for regular, 2 for special
    private boolean bRegFeatVisited = false;
    private boolean bRegMtncVisited = false;
    private boolean bSpclFeatVisited = false;
    private boolean bSpclMtncVisited = false;
    private boolean bMtcRefillItemsVisited = false;
    private boolean bMtcRefillDenomVisited = false;
    private boolean bSpclMtcRefillItemsVisited = false;
    private boolean bHasStocked = false;

 /**
     *constuctor of VM 
     * @param CRegular for creating the regular vending machine
     * @param CSpecial for creating the special vending machine
     * 
     */ 
    public VendingMachine(Regular CRegular, Special CSpecial)
    {
        this.mainFrame = new JFrame("Virtual Vending Machine");

        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //the X button on frame
        this.mainFrame.setLayout(new FlowLayout());//flow layout
        this.mainFrame.setSize(500, 500); //size of frame

        start(CRegular, CSpecial); //calls the start 

        this.mainFrame.setVisible(true); //Always last sa constructor
    }

    /**
     * Method to display and access starting screen
     * @param CRegular to access Regular VM menu
     * @param CSpecial to access Special VM menu
     * 
     */ 
    public void start(Regular CRegular, Special CSpecial){
        startScreen.setSize(300, 300); //size of screen
        startScreen.setLayout(new BoxLayout(startScreen, BoxLayout.PAGE_AXIS));//sets the layout into a boxlayout
        JLabel startGraphic = new JLabel("<html><h3><bold>Welcome to the Vending Machine! Available Options Are:</bold></h3><hr></html>"); //starting screen title
        startButtons[0] = new JButton("Regular");
        startButtons[1] = new JButton("Special"); //the buttons
        startButtons[2] = new JButton("Exit");
        startButtons[0].addActionListener(new ActionListener() {   //action - if button 0, it goes to regularMenu
            @Override
            public void actionPerformed(ActionEvent e){
                    regularMenu(CRegular); 
            }
        });
        startButtons[1].addActionListener(new ActionListener() {   //action - if button 1, it goes to specialMenu
            @Override
            public void actionPerformed(ActionEvent e){
                specialMenu(CSpecial);
            }
        });
        startButtons[2].addActionListener(new ActionListener() {    //action - if button 2, it closes
            @Override
            public void actionPerformed(ActionEvent e){
                mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
        startScreen.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGraphic.setAlignmentX(Component.CENTER_ALIGNMENT); //center alignment of the title text
        startButtons[0].setAlignmentX(Component.CENTER_ALIGNMENT);//center alignment of the buttons
        startButtons[1].setAlignmentX(Component.CENTER_ALIGNMENT);
        startButtons[2].setAlignmentX(Component.CENTER_ALIGNMENT);
        startScreen.add(startGraphic);  //adds the text and buttons to the screen
        startScreen.add(startButtons[0]);
        startScreen.add(startButtons[1]);
        startScreen.add(startButtons[2]);
        mainFrame.add(startScreen); //adds the screen in the frame
        mainFrame.setPreferredSize(new Dimension(500,500));
        mainFrame.pack(); //call so that new panel appears, do this everytime panel changes
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);     
    }


   /**
     * Method to create, display and access the regular menu
     * @param CRegular to access the features and maintenance in Regular 
     * 
     */ 
    public void regularMenu(Regular CRegular){
        nCurrentVendingMachine = 1;
        mainFrame.remove(startScreen);  //removes the previous panel so that it takes the new panel, do this everytime panel changes
        mainScreen.setSize(300, 300); //sets size of the regular screen 
        mainScreen.setLayout(new BoxLayout(mainScreen, BoxLayout.PAGE_AXIS));
        JButton[] regularButton = new JButton[3];
        JLabel titleGraphic = new JLabel("<html><h3><bold>Regular Vending Machine Options:</bold></h3><hr></html>");
        regularButton[0] = new JButton("Features");
        regularButton[1] = new JButton("Maintenance");
        regularButton[2] = new JButton("Exit");
        regularButton[0].addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e){
                if(bRegFeatVisited){
                    bRegFeatVisited();    //calls method if the regualr features has already been visited so that the screen doesnt duplicate
                }else{
                    regularFeatures(CRegular); //calls and creates the regularFeatures
                }
            }
        });
        regularButton[1].addActionListener(new ActionListener() { //same rin here in maintenance
            @Override
            public void actionPerformed(ActionEvent e){
                if(bRegMtncVisited){
                    bRegMtncVisited();
                }else{
                    regularMaintenance(CRegular);
                }
            }
        });
        regularButton[2].addActionListener(new ActionListener() { //closes 
            @Override
            public void actionPerformed(ActionEvent e){
                mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
        mainScreen.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleGraphic.setAlignmentX(Component.CENTER_ALIGNMENT);
        regularButton[0].setAlignmentX(Component.CENTER_ALIGNMENT);
        regularButton[1].setAlignmentX(Component.CENTER_ALIGNMENT);
        regularButton[2].setAlignmentX(Component.CENTER_ALIGNMENT);
        mainScreen.add(titleGraphic);
        mainScreen.add(regularButton[0]);
        mainScreen.add(regularButton[1]);
        mainScreen.add(regularButton[2]);
        mainFrame.add(mainScreen);
        mainFrame.setPreferredSize(new Dimension(500,500));
        mainFrame.pack(); //call so that new panel appears, do this everytime panel changes
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);      
    }


    /**
     * Method to create, display and use the regular features
     * @param CRegular to use the Regular VM features 
     * 
     */ 
    public void regularFeatures(Regular CRegular){
        nMoneyInserted = 0;
        bRegFeatVisited = true;  //so method will only be visited once
        mainFrame.remove(mainScreen);   //removes previous screen
        vmFeatureScreen.setSize(800, 800);
        vmFeatureScreen.setLayout(new BoxLayout(vmFeatureScreen, BoxLayout.PAGE_AXIS));
        JLabel spclFeatMsg = new JLabel("<html><h3>Regular Vending Machine</h3></html>");
        JPanel spclFeatMainScreen = new JPanel();        //creates the panel for the whole screen
        spclFeatMainScreen.setLayout(new FlowLayout()); //makes it a flow layout
        JPanel spclVendingScreen = new JPanel();         //creates the panel for the vending machine
        spclVendingScreen.setLayout(new GridLayout(4, 2, 10, 10)); //makes it a grid layout
        JPanel[] spclVendingPanels = new JPanel[8];       //creates an array for the panels for each item slots
        for(int i = 0; i <= 7; i++){
            itemButtons[i] = new JButton(CRegular.getSlots()[i].getItem().getItemName());//buttons for each i = to the item name of the ith number
            regVendingInfo[i] = new JLabel("<html>" + "Amount: " + CRegular.getSlots()[i].getAmount()  + "<br>" + "Price: " + CRegular.getItem(i).getPrice() + "<br>" + "Calories: " +CRegular.getItem(i).getCalories() + "</html>"); //text label under the button for the amount,price, and calories
            itemButtons[i].addActionListener(new ActionListener() { //action of the button
                @Override
                public void actionPerformed(ActionEvent e){
                    strItemSelected = ((JButton) e.getSource()).getText(); //strItemSelected gets the text from the current button that has been clicked
                    itemPicked.setText("Selected Item: " + strItemSelected); //will replace the text in the text screen of the machine
                }
            });
            spclVendingPanels[i] = new JPanel();//creates the panels for the item slots
            spclVendingPanels[i].setLayout(new BoxLayout(spclVendingPanels[i], BoxLayout.PAGE_AXIS));
            itemButtons[i].setPreferredSize(new Dimension(200, 150));
            spclVendingPanels[i].add(itemButtons[i]);      //adds the buttons into the panels
            spclVendingPanels[i].add(regVendingInfo[i]); //adds the regVendingInfo into the panels
        }

        JButton swap = new JButton("Swap");
        swap.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e){
                for(int i = 8; i <= 15; i++){
                    itemButtons[i] = new JButton(CRegular.getSlots()[i].getItem().getItemName());//buttons for each i = to the item name of the ith number
                    regVendingInfo[i] = new JLabel("<html>" + "Amount: " + CRegular.getSlots()[i].getAmount()  + "<br>" + "Price: " + CRegular.getItem(i).getPrice() + "<br>" + "Calories: " +CRegular.getItem(i).getCalories() + "</html>");
                    spclVendingPanels[i].add(regVendingInfo[i]); //adds the regVendingInfo into the panels
                }
            }
        });

        JPanel regSideScreen = new JPanel();    //creates a panel beside the VM
        regSideScreen.setLayout(new BoxLayout(regSideScreen, BoxLayout.PAGE_AXIS));
        JPanel textScreen = new JPanel();       //creates a panel for the text screen
        textScreen.setLayout(new BoxLayout(textScreen, BoxLayout.PAGE_AXIS));
        itemPicked.setText("Insert Money and Select an Item."); //current set of texts in text screen
        moneyAmount.setText("Current Inserted Money: " + nMoneyInserted); //current set of texts in text screen
        JPanel denomScreen = new JPanel();            //creates a panel for the denominations  
        denomScreen.setLayout(new GridLayout(2, 4, 5, 5)); //layout of the denomination panels
        for(int i = 0; i <= 7; i++){
            denomButtons[i] = new JButton(Integer.toString(CRegular.getMoneyDenom()[i]));//buttons for each i = to each denomination ,, also integer to string because integer cannot be displayed on the button 
            denomButtons[i].addActionListener(new ActionListener() { //action of the buttons - inserting of money in the VM
                @Override
                public void actionPerformed(ActionEvent e){
                    strTempText = (((JButton) e.getSource()).getText()); //gets the text on the button that has been clicked
                    CRegular.addDenom(Integer.parseInt(strTempText)); //adds the inserted money into addDenom - addDenom is from regular and is a storage for the inserted money,, also string to integer so that it can be added together
                    nMoneyInserted = nMoneyInserted + Integer.parseInt(strTempText); //computation for the inserted money and money to be inserted
                    moneyAmount.setText("Current Inserted Money: " + nMoneyInserted); //displays the inserted money
                }
            });
        }
        JPanel optionScreen = new JPanel(); //creates a panel for the options - dispense, cancel, exit
        optionScreen.setLayout(new BoxLayout(optionScreen, BoxLayout.PAGE_AXIS));
        
        for(int i = 0; i <= 7; i++){ //Adds the vending machine panel into the whole screen panel
            spclVendingScreen.add(spclVendingPanels[i]);
            spclVendingPanels[i].setPreferredSize(new Dimension(200,150));
            
        }
        for(int i = 0; i <= 7; i++){  //adds the denomination buttons into the denomination panel
            denomScreen.add(denomButtons[i]);
        }

        //adding the buttons and displays into their respective screen panels
        buttonFuncs(CRegular);
        swap.setPreferredSize(new Dimension(100, 100));
        swap.add(swap);
        optionScreen.add(dispenseButton); //for example: the dispense button is added into the option panel
        optionScreen.add(cancelButton);
        optionScreen.add(returnBtn);
        dispenseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        dispenseButton.setPreferredSize(new Dimension(20, 20));
        cancelButton.setPreferredSize(new Dimension(20, 20));
        returnBtn.setPreferredSize(new Dimension(20, 20));
        optionScreen.add(dispenseMsg);
        textScreen.add(itemPicked);
        textScreen.add(moneyAmount);
        textScreen.add(moneyNeed);
        dispenseMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        itemPicked.setAlignmentX(Component.CENTER_ALIGNMENT);
        moneyAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
        moneyNeed.setAlignmentX(Component.CENTER_ALIGNMENT);
        regSideScreen.add(textScreen);
        regSideScreen.add(denomScreen);
        regSideScreen.add(optionScreen);
        spclFeatMainScreen.add(spclVendingScreen);
        spclFeatMainScreen.add(regSideScreen);
        vmFeatureScreen.add(spclFeatMsg, Component.CENTER_ALIGNMENT);
        vmFeatureScreen.add(spclFeatMainScreen);
        mainFrame.add(vmFeatureScreen);
        mainFrame.setPreferredSize(new Dimension(3000,3000));
        mainFrame.pack(); //call so that new panel appears, do this everytime panel changes
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);     
    }


    /**
     * Method to use buttons in Regular VM feature
     * containg dispense, cancel, exit
     * @param CRegular to access Regular features
     * 
     */ 
    public void buttonFuncs(Regular CRegular){
        if(nCurrentVendingMachine == 1){ // if the current VM is in regular
            returnBtn.addActionListener(new ActionListener() { //action for the exit button
                @Override
                public void actionPerformed(ActionEvent e){
                    backToReg();        //the action calls the backToReg method - the panel returns to the regular VM menu without duplicating it
                }
            });
            cancelButton.addActionListener(new ActionListener() { //action for the cancel button
                @Override
                public void actionPerformed(ActionEvent e){
                    nMoneyInserted = 0; //resets the money inserted to 0
                    moneyAmount.setText("Current Inserted Money: " + nMoneyInserted); 
                    strItemSelected = "";  //resets the item selected to "none"
                    itemPicked.setText("Insert Money and Select an Item."); //and displays the text to this
                    dispenseMsg.setText("Waiting...");
                }
            });
            dispenseButton.addActionListener(new ActionListener() { //action for the dispense button
                @Override
                public void actionPerformed(ActionEvent e){
                    boolean a = CRegular.confirmPrice(nMoneyInserted, strItemSelected); //calls method from regular - checks if inserted money is sufficient for the item price
                    boolean b = CRegular.confirmItemAmount(nMoneyInserted, strItemSelected); //calls method from regular - checks if the selected item is not out of stock 
                    boolean c = CRegular.checkChange(nMoneyInserted, strItemSelected);//calls method from regular - checks if the machine still has change to give
                    if(a && b && c){ //if all a, b, c are true then the item dispenses and change is given and resets the displayed text into its original text
                        dispenseMsg.setText("<html>" + strItemSelected + " Dispensed! <br> Change Given: " + CRegular.getChange() + "</html>");
                        CRegular.dispense(nMoneyInserted, strItemSelected);
                        updateItemText(CRegular);   //updates the item display - updates the amount of the item
                        itemPicked.setText("Insert Money and Select an Item.");
                        nMoneyInserted = 0;
                        moneyAmount.setText("Current Inserted Money: " + nMoneyInserted);
                        strItemSelected = "";
                        ActionListener task = new ActionListener(){
                            public void actionPerformed(ActionEvent e){
                                dispenseMsg.setText("Waiting...");
                            }
                        };
                        Timer timer = new Timer(3000, task); 
                        timer.setRepeats(false);
                        timer.start();
                    }
                    else{
                        if(!a)
                            dispenseMsg.setText("<html>Transaction not Complete.<br> Please insert more money or select another item.</html>");
                        else if(!b)
                            dispenseMsg.setText("<html>Transaction not Complete.<br> Item slot is empty, please select another item.</html>");
                        else if(!c)
                            dispenseMsg.setText("<html>Transaction not Complete.<br> Change is not enough. Please call Maintenance.</html>");
                        

                    }
                }
            });
        }
    }

    /**
     * Method that updates the displayed text of an item
     * 
     */ 
    private void updateItemText(Regular CRegular){
        for(int i = 0; i <= 7;i++){
            if(strItemSelected.equalsIgnoreCase(CRegular.getSlots()[i].getItem().getItemName()))
                nItemIndex = i;
        }
        regVendingInfo[nItemIndex].setText("<html>" + "Amount: " + CRegular.getSlots()[nItemIndex].getAmount()  + "<br>" + "Price: " + CRegular.getItem(nItemIndex).getPrice() + "<br>" + "Calories: " +CRegular.getItem(nItemIndex).getCalories() + "</html>");
    }

    /**
     * Method that calls back the regularMenu
     * 
     */ 
    public void backToReg(){
        nMoneyInserted = 0;
        mainFrame.remove(vmFeatureScreen);
        mainFrame.remove(vmMaintenanceScreen);
        mainFrame.add(mainScreen);

        mainFrame.pack();
    }

    /**
     * Method that calls back the regularFeatures
     * 
     */ 
    public void bRegFeatVisited(){
        nMoneyInserted = 0;
        moneyAmount.setText("Current Inserted Money: " + nMoneyInserted);
        itemPicked.setText("Insert Money and Select an Item.");
        dispenseMsg.setText("Waiting...");
        mainFrame.remove(mainScreen);
        mainFrame.add(vmFeatureScreen);
        mainFrame.pack();
    }


//REGULAR MAINTENANCE
    /**
     * Method to create, display and use the regular maintenance
     * @param CRegular to use the Regular VM maintenance 
     * 
     */ 
    public void regularMaintenance(Regular CRegular){
        bRegMtncVisited = true;
        mainFrame.remove(mainScreen);
        vmMaintenanceScreen.setLayout(new BoxLayout(vmMaintenanceScreen, BoxLayout.PAGE_AXIS));
        JLabel mtcOptions = new JLabel("Maintenance Options");
        JButton[] mtcButtons = new JButton[5];
        mtcButtons[0] = new JButton("Item Stocking and Price Change");
        mtcButtons[1] = new JButton("Restock Denominations");
        mtcButtons[2] = new JButton("Payment Collection");
        mtcButtons[3] = new JButton("Transaction Summary");
        mtcButtons[4] = new JButton("Exit");
        JLabel mtcInfo = new JLabel();
        mtcButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bMtcRefillItemsVisited){
                    bMtcRefillItemsVisited();
                }
                else{
                    mtcRefillItems(CRegular);
                }
            }
        });
        mtcButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bMtcRefillDenomVisited){
                    bMtcRefillDenomVisited();
                }
                else{
                    mtcRefillDenom(CRegular);
                }
            }
        });
        mtcButtons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CRegular.Collect();
                mtcInfo.setText("Money Successfully Collected! Check Summary of Transactions for Details.");
            }
        });
        mtcButtons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mtcInfo.setText("<html>" +
                                "Summary of Transactions: <br>" + 
                                "Total Sales Before Restocking: " + Integer.toString(CRegular.getTotalPrev()) + "<br>" +
                                CRegular.getSlots()[0].display() + "<br>" +
                                CRegular.getSlots()[1].display() + "<br>" +
                                CRegular.getSlots()[2].display() + "<br>" +
                                CRegular.getSlots()[3].display() + "<br>" +
                                CRegular.getSlots()[4].display() + "<br>" +
                                CRegular.getSlots()[5].display() + "<br>" +
                                CRegular.getSlots()[6].display() + "<br>" +
                                CRegular.getSlots()[7].display() + "<br>" + "</html>"
                                );
            }
        });
        mtcButtons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                backToReg();
            }
        });

        vmMaintenanceScreen.add(mtcOptions);
        mtcOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
        mtcButtons[0].setAlignmentX(Component.CENTER_ALIGNMENT);
        mtcButtons[1].setAlignmentX(Component.CENTER_ALIGNMENT);
        mtcButtons[2].setAlignmentX(Component.CENTER_ALIGNMENT);
        mtcButtons[3].setAlignmentX(Component.CENTER_ALIGNMENT);
        mtcButtons[4].setAlignmentX(Component.CENTER_ALIGNMENT);
        vmMaintenanceScreen.add(mtcButtons[0]);
        vmMaintenanceScreen.add(mtcButtons[1]);
        vmMaintenanceScreen.add(mtcButtons[2]);
        vmMaintenanceScreen.add(mtcButtons[3]);
        vmMaintenanceScreen.add(mtcButtons[4]);
        vmMaintenanceScreen.add(mtcInfo);
        mainFrame.add(vmMaintenanceScreen);
        mainFrame.setPreferredSize(new Dimension(500,500));
        mainFrame.pack(); //call so that new panel appears, do this everytime panel changes
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);   
    }

    /**
     * Method for the stocking/Price set of items
     * containg the restocking and setting of price
     * @param CRegular to access the methods in Regular 
     * 
     */ 
    public void mtcRefillItems(Regular CRegular){
        bMtcRefillItemsVisited = true;
        mainFrame.remove(vmMaintenanceScreen);
        dispenseMsg.setText("Waiting...");
        mtcRefillItems.setLayout(new BoxLayout(mtcRefillItems, BoxLayout.PAGE_AXIS));
        JLabel mtcRefillItemsMsg = new JLabel("Restock/Change Price");
        JPanel mtcRefillmain = new JPanel();
        mtcRefillmain.setLayout(new FlowLayout());
        JPanel mtcItems = new JPanel();
        mtcItems.setLayout(new GridLayout(4, 2, 10, 10));
        JPanel[] mtcPanel = new JPanel[8];
        for(int i = 0; i <= 7; i++){
            mtcItemButtons[i] = new JButton(CRegular.getSlots()[i].getItem().getItemName());
            regVendingInfo[i] = new JLabel("<html>" + "Amount: " + CRegular.getSlots()[i].getAmount()  + "<br>" + "Price: " + CRegular.getItem(i).getPrice() + "<br>" + "Calories: " +CRegular.getItem(i).getCalories() + "</html>");
            mtcItemButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    strItemSelected = ((JButton) e.getSource()).getText();
                    itemPicked.setText("Selected Item: " + strItemSelected);
                }
            });
            mtcPanel[i] = new JPanel();
            mtcPanel[i].setLayout(new BoxLayout(mtcPanel[i], BoxLayout.PAGE_AXIS));
            mtcItemButtons[i].setPreferredSize(new Dimension(200, 150));
            mtcPanel[i].add(mtcItemButtons[i]);
            mtcPanel[i].add(regVendingInfo[i]);
        }
        JPanel mtcSideScreen = new JPanel();
        mtcSideScreen.setLayout(new BoxLayout(mtcSideScreen, BoxLayout.PAGE_AXIS));
        itemPicked.setText("Select Item to Restock/Edit");
        JButton itemRestockAll = new JButton("Restock All(10)");
        itemRestockAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(!bHasStocked){
                    for(int i = 0; i <= 7; i++){
                        CRegular.getSlots()[i].invEnd();
                    }
                    bHasStocked = true;
                }
                CRegular.restockAllSlots();
                for(int i = 0; i <= 7; i++){
                    regVendingInfo[i].setText("<html>" + "Amount: " + CRegular.getSlots()[i].getAmount()  + "<br>" + "Price: " + CRegular.getItem(i).getPrice() + "<br>" + "Calories: " +CRegular.getItem(i).getCalories() + "</html>");
                }
            }
        });
        JButton itemRestockOne = new JButton("Restock(1)");
        itemRestockOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(!bHasStocked){
                    for(int i = 0; i <= 7; i++){
                        CRegular.getSlots()[i].invEnd();
                    }
                    bHasStocked = true;
                }
                updateItemText(CRegular);
                CRegular.restockOneSlot(nItemIndex);
                for(int i = 0; i <= 7; i++){
                    regVendingInfo[i].setText("<html>" + "Amount: " + CRegular.getSlots()[i].getAmount()  + "<br>" + "Price: " + CRegular.getItem(i).getPrice() + "<br>" + "Calories: " +CRegular.getItem(i).getCalories() + "</html>");
                }
            }
        });
        JButton changePriceBtn = new JButton("Change Price");
        changePriceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    updateItemText(CRegular);
                    int priceChange = Integer.parseInt(textBox.getText());
                    CRegular.changePrice(priceChange, priceChange);
                    dispenseMsg.setText("Price Successfully Changed.");
                    for(int i = 0; i <= 7; i++){
                        regVendingInfo[i].setText("<html>" + "Amount: " + CRegular.getSlots()[i].getAmount()  + "<br>" + "Price: " + CRegular.getItem(i).getPrice() + "<br>" + "Calories: " +CRegular.getItem(i).getCalories() + "</html>");
                    }
                }catch(Exception err){
                    dispenseMsg.setText("Invalid Change Price Input.");
                }
            }
        });
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                CRegular.recordTransactions();
                backToMtc();
            }
        });
        for(int i = 0; i <= 7; i++){
            mtcItems.add(mtcPanel[i]);
        }
        mtcRefillItems.add(mtcRefillItemsMsg);
        mtcRefillItems.add(mtcRefillmain);
        mtcRefillmain.add(mtcItems);
        mtcRefillmain.add(mtcSideScreen);
        mtcSideScreen.add(itemPicked);
        mtcSideScreen.add(itemRestockAll);
        mtcSideScreen.add(itemRestockOne);
        mtcSideScreen.add(textBox);
        mtcSideScreen.add(changePriceBtn);
        mtcSideScreen.add(exitBtn);
        mainFrame.add(mtcRefillItems);
        mainFrame.setPreferredSize(new Dimension(500,500));
        mainFrame.pack(); //call so that new panel appears, do this everytime panel changes
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);     
    }

    /**
     * Method for the stocking denominations/change
     * 
     * @param CRegular to access the methods in Regular 
     * 
     */ 
    public void mtcRefillDenom(Regular CRegular){
        bMtcRefillDenomVisited = true;
        mainFrame.remove(vmMaintenanceScreen);
        mtcRefillDenom.setLayout(new BoxLayout(mtcRefillDenom, BoxLayout.PAGE_AXIS));
        JLabel mtcRefillDenomMsg = new JLabel("Refill Denominations");
        JPanel mtcPanel = new JPanel();
        mtcPanel.setLayout(new FlowLayout());
        JPanel denomScreen = new JPanel();
        denomScreen.setLayout(new GridLayout(2, 4, 5, 5));
        JPanel[] denomPanel = new JPanel[8];
        for(int i = 0; i <= 7; i++){
            mtcDenomButtons[i] = new JButton(Integer.toString(CRegular.getMoneyDenom()[i]));
            mtcDenomInfo[i] = new JLabel("Amount: " + Integer.toString(CRegular.getMoneyCount()[i]));
            mtcDenomButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    strTempText = (((JButton) e.getSource()).getText());
                    int denomIndex = CRegular.getMoneyCountIndex(Integer.parseInt(strTempText));
                    CRegular.addMoneyCount(denomIndex);
                    mtcDenomInfo[denomIndex].setText("Amount: " + Integer.toString(CRegular.getMoneyCount()[denomIndex]));
                }
            });
            denomPanel[i] = new JPanel();
            denomPanel[i].setLayout(new BoxLayout(denomPanel[i], BoxLayout.PAGE_AXIS));
            denomPanel[i].add(mtcDenomButtons[i]);
            denomPanel[i].add(mtcDenomInfo[i]);
        }
        JButton addDenomAll = new JButton("Refill All");
        addDenomAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                CRegular.addMoneyCountAll();
                for(int i = 0; i <= 7; i++){
                     mtcDenomInfo[i].setText("Amount: " + Integer.toString(CRegular.getMoneyCount()[i]));
                }
            }
        });
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                backToMtc();
            }
        });

        
        for(int i = 0; i<= 7;i++){
            denomScreen.add(denomPanel[i]);
        }
        mtcRefillDenom.add(mtcRefillDenomMsg);
        mtcRefillDenom.add(mtcPanel);
        mtcPanel.add(denomScreen);
        mtcPanel.add(addDenomAll);
        mtcPanel.add(exitBtn);
        mainFrame.add(mtcRefillDenom);
        mainFrame.setPreferredSize(new Dimension(500,500));
        mainFrame.pack(); //call so that new panel appears, do this everytime panel changes
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);   
    }

   /**
     * Method that calls back the Regular Maintenance menu
     * 
     */ 
    public void bRegMtncVisited(){
        mainFrame.remove(mainScreen);
        mainFrame.add(vmMaintenanceScreen);
        mainFrame.pack();
    }
    
    /**
     * Method that calls back the stocking/price set method
     * 
     */ 
    public void bMtcRefillItemsVisited(){
        mainFrame.remove(vmMaintenanceScreen);
        mainFrame.add(mtcRefillItems);
        mainFrame.pack();
    }

    /**
     * Method that calls back the stocking denominations/change
     * 
     */ 
    public void bMtcRefillDenomVisited(){
        mainFrame.remove(vmMaintenanceScreen);
        mainFrame.add(mtcRefillDenom);
        mainFrame.pack();
    }

    /**
     * Method that calls back the Maintenance menu
     * 
     */ 
    public void backToMtc(){
        mainFrame.remove(mtcRefillItems);
        mainFrame.remove(mtcRefillDenom);
        mainFrame.add(vmMaintenanceScreen);
        mainFrame.pack();

    }
    
    
    /*------------------------------------------------------------------------ */
    /* SPECIAL VENDING MACHINE */

    /**
     * Method for the Special VM menu
     * 
     * @param CSpecial to access the Special VM  
     * 
     */ 
    public void specialMenu(Special CSpecial){
        nCurrentVendingMachine = 2;
        mainFrame.remove(startScreen);  //removes the previous panel so that it takes the new panel, do this everytime panel changes
        mainScreen.setSize(300, 300); //sets size of the regular screen 
        mainScreen.setLayout(new BoxLayout(mainScreen, BoxLayout.PAGE_AXIS));
        JButton[] specialButton = new JButton[3];
        JLabel titleGraphic = new JLabel("<html><h3><bold>Special Vending Machine Options:</bold></h3><hr></html>");
        specialButton[0] = new JButton("Features");
        specialButton[1] = new JButton("Maintenance");
        specialButton[2] = new JButton("Exit");
        specialButton[0].addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e){
                if(bSpclFeatVisited){
                    bSpclFeatVisited();    
                }
                else{
                    specialFeatures(CSpecial); 
                }
            }
        });
        specialButton[1].addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e){
                if(bSpclMtncVisited){
                    bSpclMtncVisited();
                }else{
                    specialMaintenance(CSpecial);
                }
            }
        });
        specialButton[2].addActionListener(new ActionListener() { //closes 
            @Override
            public void actionPerformed(ActionEvent e){
                mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
        mainScreen.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleGraphic.setAlignmentX(Component.CENTER_ALIGNMENT);
        specialButton[0].setAlignmentX(Component.CENTER_ALIGNMENT);
        specialButton[1].setAlignmentX(Component.CENTER_ALIGNMENT);
        specialButton[2].setAlignmentX(Component.CENTER_ALIGNMENT);
        mainScreen.add(titleGraphic);
        mainScreen.add(specialButton[0]);
        mainScreen.add(specialButton[1]);
        mainScreen.add(specialButton[2]);
        mainFrame.add(mainScreen);
        mainFrame.setPreferredSize(new Dimension(500,500));
        mainFrame.pack(); //call so that new panel appears, do this everytime panel changes
        mainFrame.setVisible(true);
    }

    /**
     * Method for the Special VM featues
     * 
     * @param CSpecial to access the methods of Special 
     * 
     */ 
    public void specialFeatures(Special CSpecial){
        nMoneyInserted = 0;
        bSpclFeatVisited = true;
        mainFrame.remove(mainScreen);
        vmFeatureScreen.setSize(800, 800);
        vmFeatureScreen.setLayout(new BoxLayout(vmFeatureScreen, BoxLayout.PAGE_AXIS));
         JLabel spclFeatMsg = new JLabel("<html><h3>Special Vending Machine</h3></html>");
         JPanel spclFeatMainScreen = new JPanel();        
        spclFeatMainScreen.setLayout(new FlowLayout()); 
        JPanel spclFlavorScreen = new JPanel();         
        spclFlavorScreen.setLayout(new BoxLayout(spclFlavorScreen, BoxLayout.PAGE_AXIS));
        JPanel[] spclVendingPanel = new JPanel[5];
        for(int i = 0; i <= 4; i++){
            spclFlavorButtons[i] = new JButton(CSpecial.getFlavorSlots()[i].getItem().getItemName());
            spclVendingInfo[i] = new JLabel("<html>" + "Amount: " + CSpecial.getFlavorSlots()[i].getAmount()  + "<br>" + "Price: " + CSpecial.getFlavors()[i].getPrice() + "<br>" + "Calories: " +CSpecial.getFlavors()[i].getCalories() + "</html>");
            spclFlavorButtons[i].addActionListener(new ActionListener() { 
                @Override
                public void actionPerformed(ActionEvent e){
                    strFlavorSelected = ((JButton) e.getSource()).getText(); 
                    flavorPicked.setText("Selected Flavor: " + strFlavorSelected);
                    CSpecial.addFlavor(strFlavorSelected);
                    spclTotalPrice.setText("Total Price: " + CSpecial.getSelectedTotal());
                    spclTotalCal.setText("Total Calories: " + CSpecial.getSelectedCalories());
                }
            });
            spclVendingPanel[i] = new JPanel();
            spclVendingPanel[i].setLayout(new BoxLayout(spclVendingPanel[i], BoxLayout.PAGE_AXIS));
            spclVendingPanel[i].add(spclFlavorButtons[i]);     
            spclVendingPanel[i].add(spclVendingInfo[i]);
        }
        JPanel spclVendingScreen = new JPanel();         
        spclVendingScreen.setLayout(new GridLayout(2, 2, 10, 10)); 
        JPanel[] spclVendingPanels = new JPanel[4];
        for(int i = 0; i <= 3; i++){
            itemButtons[i] = new JButton(CSpecial.getSlots()[i].getItem().getItemName());
            regVendingInfo[i] = new JLabel("<html>" + "Amount: " + CSpecial.getSlots()[i].getAmount()  + "<br>" + "Price: " + CSpecial.getItem(i).getPrice() + "<br>" + "Calories: " +CSpecial.getItem(i).getCalories() + "<br>Amount Taken: "+ CSpecial.getToppingAmount()[i] +"</html>");
            itemButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    strItemSelected = ((JButton) e.getSource()).getText();
                    getItemIndexSpcl(CSpecial);
                    CSpecial.addTopping(nItemIndex);
                    regVendingInfo[nItemIndex].setText("<html>" + "Amount: " + CSpecial.getSlots()[nItemIndex].getAmount()  + "<br>" + "Price: " + CSpecial.getItem(nItemIndex).getPrice() + "<br>" + "Calories: " +CSpecial.getItem(nItemIndex).getCalories() + "<br>Amount Taken: "+ CSpecial.getToppingAmount()[nItemIndex] +"</html>");
                    spclTotalPrice.setText("Total Price: " + CSpecial.getSelectedTotal());
                    spclTotalCal.setText("Total Calories: " + CSpecial.getSelectedCalories());
                }
            });
            spclVendingPanels[i] = new JPanel();
            spclVendingPanels[i].setLayout(new BoxLayout(spclVendingPanels[i], BoxLayout.PAGE_AXIS));
            itemButtons[i].setPreferredSize(new Dimension(200, 150));
            spclVendingPanels[i].add(itemButtons[i]);
            spclVendingPanels[i].add(regVendingInfo[i]);
        }
        JPanel spclSideScreen = new JPanel();    //creates a panel beside the VM
        spclSideScreen.setLayout(new BoxLayout(spclSideScreen, BoxLayout.PAGE_AXIS));
        JPanel textScreen = new JPanel();       
        textScreen.setLayout(new BoxLayout(textScreen, BoxLayout.PAGE_AXIS));
        JLabel cakeLabel = new JLabel("<html>Base Cake Price: 1950 <br> Base Cake Calories: 424</html>");
        flavorPicked.setText("Select Flavor, Toppings, then Insert Money."); 
        moneyAmount.setText("Current Inserted Money: " + nMoneyInserted); 
        spclTotalPrice.setText("Total Price: " + CSpecial.getSelectedTotal());
        spclTotalCal.setText("Total Calories: " + CSpecial.getSelectedCalories());
        JPanel denomScreen = new JPanel();            
        denomScreen.setLayout(new GridLayout(2, 4, 5, 5)); 
        for(int i = 0; i <= 7; i++){
            denomButtons[i] = new JButton(Integer.toString(CSpecial.getMoneyDenom()[i]));
            denomButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    strTempText = (((JButton) e.getSource()).getText());
                    CSpecial.addDenom(Integer.parseInt(strTempText));
                    nMoneyInserted = nMoneyInserted + Integer.parseInt(strTempText);
                    moneyAmount.setText("Current Inserted Money: " + nMoneyInserted);
                }
            });
        }
        JPanel optionScreen = new JPanel(); //creates a panel for the options - dispense, cancel, exit
        optionScreen.setLayout(new BoxLayout(optionScreen, BoxLayout.PAGE_AXIS));


        for(int i = 0; i <= 4; i++){
            spclFlavorScreen.add(spclVendingPanel[i]);
        }
        for(int i = 0; i <= 3; i++){ //Adds the vending machine panel into the whole screen panel
            spclVendingScreen.add(spclVendingPanels[i]);
            spclVendingPanels[i].setPreferredSize(new Dimension(200,150));
            
        }
        for(int i = 0; i <= 7; i++){  //adds the denomination buttons into the denomination panel
            denomScreen.add(denomButtons[i]);
        }
        spclButtonFuncs(CSpecial);
        optionScreen.add(dispenseButton);
        optionScreen.add(cancelButton);
        optionScreen.add(returnBtn);
        dispenseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        dispenseButton.setPreferredSize(new Dimension(20, 20));
        cancelButton.setPreferredSize(new Dimension(20, 20));
        returnBtn.setPreferredSize(new Dimension(20, 20));
        mainFrame.add(vmFeatureScreen);
        optionScreen.add(dispenseMsg);
        textScreen.add(cakeLabel);
        textScreen.add(flavorPicked);
        textScreen.add(spclTotalPrice);
        textScreen.add(spclTotalCal);
        textScreen.add(moneyAmount);
        dispenseMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        flavorPicked.setAlignmentX(Component.CENTER_ALIGNMENT);
        moneyAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
        spclTotalPrice.setAlignmentX(Component.CENTER_ALIGNMENT);
        spclTotalCal.setAlignmentX(Component.CENTER_ALIGNMENT);
        spclSideScreen.add(textScreen);
        spclSideScreen.add(denomScreen);
        spclSideScreen.add(optionScreen);
        spclFeatMainScreen.add(spclFlavorScreen);
        spclFeatMainScreen.add(spclVendingScreen);
        spclFeatMainScreen.add(spclSideScreen);
        vmFeatureScreen.add(spclFeatMsg, Component.CENTER_ALIGNMENT);
        vmFeatureScreen.add(spclFeatMainScreen);
        mainFrame.add(vmFeatureScreen);
        mainFrame.setPreferredSize(new Dimension(3000,3000));
        mainFrame.pack(); //call so that new panel appears, do this everytime panel changes
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);    
    }

    /**
     * Method to use buttons in Regular VM feature
     * containg dispense, cancel, exit
     * @param CSpecial to access Special features
     * 
     */ 
    public void spclButtonFuncs(Special CSpecial){
        if(nCurrentVendingMachine == 2){ // if the current VM is in regular
            returnBtn.addActionListener(new ActionListener() { //action for the exit button
                @Override
                public void actionPerformed(ActionEvent e){
                    backToSpcl();        //the action calls the backToReg method - the panel returns to the regular VM menu without duplicating it
                }
            });
            cancelButton.addActionListener(new ActionListener() { //action for the cancel button
                @Override
                public void actionPerformed(ActionEvent e){
                    nMoneyInserted = 0; //resets the money inserted to 0
                    moneyAmount.setText("Current Inserted Money: " + nMoneyInserted); 
                    strFlavorSelected = "";  //resets the item selected to "none"
                    flavorPicked.setText("Select Flavor, Toppings, then Insert Money.");
                    CSpecial.resetSpcl(); 
                    moneyAmount.setText("Current Inserted Money: " + nMoneyInserted); 
                    spclTotalPrice.setText("Total Price: " + CSpecial.getSelectedTotal());
                    spclTotalCal.setText("Total Calories: " + CSpecial.getSelectedCalories());
                    for(int i = 0; i <= 3; i++){
                        regVendingInfo[i].setText("<html>" + "Amount: " + CSpecial.getSlots()[i].getAmount()  + "<br>" + "Price: " + CSpecial.getItem(i).getPrice() + "<br>" + "Calories: " +CSpecial.getItem(i).getCalories() + "<br>Amount Taken: "+ CSpecial.getToppingAmount()[i] +"</html>");
                    }
                }
            });
            dispenseButton.addActionListener(new ActionListener() { //action for the dispense button
                @Override
                public void actionPerformed(ActionEvent e){
                    boolean a = CSpecial.confirmPrice(nMoneyInserted); //calls method from regular - checks if inserted money is sufficient for the item price
                    boolean b = CSpecial.confirmItemAmount(nMoneyInserted); //calls method from regular - checks if the selected item is not out of stock 
                    boolean c = CSpecial.checkChange(nMoneyInserted);//calls method from regular - checks if the machine still has change to give
                    if(a && b && c){ //if all a, b, c are true then the item dispenses and change is given and resets the displayed text into its original text
                        Cake dispensedCake = CSpecial.dispense(nMoneyInserted);
                        cakePreparationMsg(CSpecial, dispensedCake);
                        nMoneyInserted = 0;
                        flavorPicked.setText("Select Flavor, Toppings, then Insert Money.");
                        moneyAmount.setText("Current Inserted Money: " + nMoneyInserted); 
                        spclTotalPrice.setText("Total Price: " + CSpecial.getSelectedTotal());
                        spclTotalCal.setText("Total Calories: " + CSpecial.getSelectedCalories());
                        for(int i = 0; i <= 3; i++){
                            regVendingInfo[i].setText("<html>" + "Amount: " + CSpecial.getSlots()[i].getAmount()  + "<br>" + "Price: " + CSpecial.getItem(i).getPrice() + "<br>" + "Calories: " +CSpecial.getItem(i).getCalories() + "<br>Amount Taken: "+ CSpecial.getToppingAmount()[i] +"</html>");
                        }
                    }
                    else{
                        if(!a)
                            dispenseMsg.setText("<html>Transaction not Complete.<br> Please insert more money or select another item.</html>");
                        else if(!b)
                            dispenseMsg.setText("<html>Transaction not Complete.<br> Item slot is empty, please select another item.</html>");
                        else if(!c)
                            dispenseMsg.setText("<html>Transaction not Complete.<br> Change is not enough. Please call Maintenance.</html>");
                        

                    }
                }
            });
        }
    }

    /**
     * Method to display text for dispensing preparation
     * 
     * @param CSpecial to access the methods of Special 
     * @param CSpecial to access the methods of Cake 
     */ 
    public void cakePreparationMsg(Special cSpecial, Cake cCake){
        int nMilliseconds = 0;
        
                    ActionListener base1 = new ActionListener(){
                            public void actionPerformed(ActionEvent e){
                                dispenseMsg.setText("Waiting...");
                            }
                        };
                        nMilliseconds += 2000;
                        Timer timer1 = new Timer(nMilliseconds, base1); 
                        timer1.setRepeats(false);
                        timer1.start();
         
                    ActionListener base2 = new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            dispenseMsg.setText("mixing flour, baking powder, salt, and baking soda.");
                        }
                    };
                    nMilliseconds += 2000;
                    Timer timer2 = new Timer(nMilliseconds, base2); 
                    timer2.setRepeats(false);
                    timer2.start();


                    ActionListener base3 = new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            dispenseMsg.setText("churning the butter, adding sugar, beating eggs and combining the mixture.");
                        }
                    };
                    nMilliseconds += 2000;
                    Timer timer3 = new Timer(nMilliseconds, base3); 
                    timer3.setRepeats(false);
                    timer3.start();
 
           
                    ActionListener base4 = new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            dispenseMsg.setText("adding the flavor and few drops of food coloring.");
                        }
                    };
                    nMilliseconds += 2000;
                    Timer timer4 = new Timer(nMilliseconds, base4); 
                    timer4.setRepeats(false);
                    timer4.start();
           
                    ActionListener base5 = new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            dispenseMsg.setText("dividing batter.");
                        }
                    };
                    nMilliseconds += 2000;
                    Timer timer5 = new Timer(nMilliseconds, base5); 
                    timer5.setRepeats(false);
                    timer5.start();
           
                    ActionListener base6 = new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            dispenseMsg.setText("baking for 30 minutes.");
                        }
                    };
                    nMilliseconds += 2000;
                    Timer timer6 = new Timer( nMilliseconds, base6); 
                    timer6.setRepeats(false);
                    timer6.start();
           
                    ActionListener base7 = new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            dispenseMsg.setText("decorating cake and adding toppings.");
                        }
                    };
                    nMilliseconds += 2000;
                    Timer timer7 = new Timer(nMilliseconds, base7); 
                    timer7.setRepeats(false);
                    timer7.start();
           
                    ActionListener base8 = new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            dispenseMsg.setText("cake complete!");
                        }
                    };
                    nMilliseconds += 2000;
                    Timer timer8 = new Timer(nMilliseconds, base8); 
                    timer8.setRepeats(false);
                    timer8.start();
            
        }
    

    /**
     * Method that calls back the Specieal menu
     * 
     */ 
    public void backToSpcl(){
        nMoneyInserted = 0;
        mainFrame.remove(vmFeatureScreen);
        mainFrame.remove(vmMaintenanceScreen);
        mainFrame.add(mainScreen);
        mainFrame.pack();
    }

    /**
     * Method that calls back the Special feature
     * 
     */ 
    public void bSpclFeatVisited(){
        nMoneyInserted = 0;
        dispenseMsg.setText("Waiting...");
        mainFrame.remove(mainScreen);
        mainFrame.add(vmFeatureScreen);
        mainFrame.pack();
    }

    /**
     * Method that gets Item index of an item
     * 
     */ 
    private void getItemIndexSpcl(Special CSpecial){
        for(int i = 0; i <= 3; i++){
            if(strItemSelected.equalsIgnoreCase(CSpecial.getSlots()[i].getItem().getItemName()))
                nItemIndex = i;
        }
    }
    

    /**
     * Method for the Special VM Maintenance menu
     * 
     * @param CSpecial to access the methods of Special 
     * 
     */ 
    public void specialMaintenance(Special CSpecial){
       bSpclMtncVisited = true;
        mainFrame.remove(mainScreen);
        vmMaintenanceScreen.setLayout(new BoxLayout(vmMaintenanceScreen, BoxLayout.PAGE_AXIS));
        JLabel mtcOptions = new JLabel("Maintenance Options");
        JButton[] mtcButtons = new JButton[5];
        mtcButtons[0] = new JButton("Edit Items");
        mtcButtons[1] = new JButton("Restock Denominations");
        mtcButtons[2] = new JButton("Payment Collection");
        mtcButtons[3] = new JButton("Transaction Summary");
        mtcButtons[4] = new JButton("Exit");
        JLabel mtcInfo = new JLabel();
        mtcButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bSpclMtcRefillItemsVisited){
                    bSpclMtcRefillItemsVisited();
                }
                else{
                    spclmtcRefillItems(CSpecial);
                }
            }
        });
        mtcButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bMtcRefillDenomVisited){
                    bMtcRefillDenomVisited();
                }
                else{
                    mtcRefillDenom(CSpecial);
                }
            }
        });
        mtcButtons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CSpecial.Collect();
                mtcInfo.setText("Money Successfully Collected! Check Summary of Transactions for Details.");
            }
        });
        mtcButtons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mtcInfo.setText("<html>" +
                                "Summary of Transactions: <br>" + 
                                CSpecial.getSlots()[0].display() + "<br>" +
                                CSpecial.getSlots()[1].display() + "<br>" +
                                CSpecial.getSlots()[2].display() + "<br>" +
                                CSpecial.getSlots()[3].display() + "<br>" +
                                CSpecial.getFlavorSlots()[0].display() + "<br>" +
                                CSpecial.getFlavorSlots()[1].display() + "<br>" +
                                CSpecial.getFlavorSlots()[2].display() + "<br>" +
                                CSpecial.getFlavorSlots()[3].display() + "<br>" + "</html>"
                                );
            }
        });
        mtcButtons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToSpcl();
            }
        });
        vmMaintenanceScreen.add(mtcOptions);
        for(int i = 0; i <= 4; i++){
            vmMaintenanceScreen.add(mtcButtons[i]);
        }
        vmMaintenanceScreen.add(mtcInfo);
        mainFrame.add(vmMaintenanceScreen);
        mainFrame.pack();
    }


    /**
     * Method for the stocking/price setting  in Special VM Maintenance 
     * 
     * @param CSpecial to access the methods of Special 
     * 
     */ 
    public void spclmtcRefillItems(Special CSpecial){
        bSpclMtcRefillItemsVisited = true;
        mainFrame.remove(vmMaintenanceScreen);
        dispenseMsg.setText("Waiting...");
        mtcRefillItems.setLayout(new BoxLayout(mtcRefillItems, BoxLayout.PAGE_AXIS));
        JLabel mtcRefillItemsMsg = new JLabel("Restock/Change Price");
        JPanel mtcRefillmain = new JPanel();
        mtcRefillmain.setLayout(new FlowLayout());
        JPanel mtcItems = new JPanel();
        mtcItems.setLayout(new GridLayout(4, 2, 10, 10));
        JPanel[] mtcPanel = new JPanel[8];
        for(int i = 0; i <= 8; i++){
            if(i >= 0 && i <= 3){
                mtcItemButtons[i] = new JButton(CSpecial.getSlots()[i].getItem().getItemName());
                regVendingInfo[i] = new JLabel("<html>" + "Amount: " + CSpecial.getSlots()[i].getAmount()  + "<br>" + "Price: " + CSpecial.getSlots()[i].getItem().getPrice() + "<br>" + "Calories: " +CSpecial.getSlots()[i].getItem().getCalories() + "</html>");
                mtcItemButtons[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        strItemSelected = ((JButton) e.getSource()).getText();
                        itemPicked.setText("Selected Item: " + strItemSelected);
                    }
                });
                mtcPanel[i] = new JPanel();
                mtcPanel[i].setLayout(new BoxLayout(mtcPanel[i], BoxLayout.PAGE_AXIS));
                mtcItemButtons[i].setPreferredSize(new Dimension(200, 150));
                mtcPanel[i].add(mtcItemButtons[i]);
                mtcPanel[i].add(regVendingInfo[i]);
            }
        }
        for(int i = 4; i <= 7; i++){
            mtcItemButtons[i] = new JButton(CSpecial.getFlavorSlots()[i].getItem().getItemName());
            regVendingInfo[i] = new JLabel("<html>" + "Amount: " + CSpecial.getFlavorSlots()[i].getAmount()  + "<br>" + "Price: " + CSpecial.getFlavors()[i].getPrice() + "<br>" + "Calories: " +CSpecial.getFlavors()[i].getCalories() + "</html>");
            mtcItemButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    strItemSelected = ((JButton) e.getSource()).getText();
                    itemPicked.setText("Selected Item: " + strItemSelected);
                }
            });
            mtcPanel[i] = new JPanel();
            mtcPanel[i].setLayout(new BoxLayout(mtcPanel[i], BoxLayout.PAGE_AXIS));
            mtcItemButtons[i].setPreferredSize(new Dimension(200, 150));
            mtcPanel[i].add(mtcItemButtons[i-3]);
            mtcPanel[i].add(regVendingInfo[i-3]);
        }
        JPanel mtcSideScreen = new JPanel();
        mtcSideScreen.setLayout(new BoxLayout(mtcSideScreen, BoxLayout.PAGE_AXIS));
        itemPicked.setText("Select Item to Restock/Edit");
        JButton itemRestockAll = new JButton("Restock All(10)");
        itemRestockAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(!bHasStocked){
                    for(int i = 0; i <= 3; i++){
                        CSpecial.getSlots()[i].invEnd();
                        CSpecial.getFlavorSlots()[i].invEnd();
                    }
                    bHasStocked = true;
                }
                CSpecial.restockAllSlots();
                for(int i = 0; i <= 3; i++){
                    regVendingInfo[i].setText("<html>" + "Amount: " + CSpecial.getSlots()[i].getAmount()  + "<br>" + "Price: " + CSpecial.getItem(i).getPrice() + "<br>" + "Calories: " +CSpecial.getItem(i).getCalories() + "</html>");
                    regVendingInfo[i+3].setText("<html>" + "Amount: " + CSpecial.getFlavorSlots()[i].getAmount()  + "<br>" + "Price: " + CSpecial.getFlavors()[i].getPrice() + "<br>" + "Calories: " +CSpecial.getFlavors()[i].getCalories() + "</html>");
                }
            }
        });
        JButton itemRestockOne = new JButton("Restock(1)");
        itemRestockOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(!bHasStocked){
                    for(int i = 0; i <= 7; i++){
                        CSpecial.getSlots()[i].invEnd();
                        CSpecial.getFlavorSlots()[i].invEnd();
                    }
                    bHasStocked = true;
                }
                getItemIndexSpcl(CSpecial);
                CSpecial.restockOneSlot(nItemIndex);
                for(int i = 0; i <= 8; i++){
                    if(i >= 0 && i <= 3){
                        regVendingInfo[i].setText("<html>" + "Amount: " + CSpecial.getSlots()[i].getAmount()  + "<br>" + "Price: " + CSpecial.getItem(i).getPrice() + "<br>" + "Calories: " +CSpecial.getItem(i).getCalories() + "</html>");
                    }else{
                        regVendingInfo[i].setText("<html>" + "Amount: " + CSpecial.getFlavorSlots()[i].getAmount()  + "<br>" + "Price: " + CSpecial.getFlavors()[i].getPrice() + "<br>" + "Calories: " +CSpecial.getFlavors()[i].getCalories() + "</html>");
                    }
                }
            }
        });
        JButton changePriceBtn = new JButton("Change Price");
        changePriceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    getItemIndexSpcl(CSpecial);
                    int priceChange = Integer.parseInt(textBox.getText());
                    CSpecial.changePrice(priceChange, priceChange);
                    dispenseMsg.setText("Price Successfully Changed.");
                    for(int i = 0; i <= 7; i++){
                        if(i >= 0 && i <= 3){
                        regVendingInfo[i].setText("<html>" + "Amount: " + CSpecial.getSlots()[i].getAmount()  + "<br>" + "Price: " + CSpecial.getItem(i).getPrice() + "<br>" + "Calories: " +CSpecial.getItem(i).getCalories() + "</html>");
                        }else{
                            regVendingInfo[i].setText("<html>" + "Amount: " + CSpecial.getFlavorSlots()[i].getAmount()  + "<br>" + "Price: " + CSpecial.getFlavors()[i].getPrice() + "<br>" + "Calories: " +CSpecial.getFlavors()[i].getCalories() + "</html>");
                        }
                    }
                }catch(Exception err){
                    dispenseMsg.setText("Invalid Change Price Input.");
                }
            }
        });
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                CSpecial.recordTransactions();
                backToSpclMtc();
            }
        });
        for(int i = 0; i <= 7; i++){
            mtcItems.add(mtcPanel[i]);
        }
        mtcRefillItems.add(mtcRefillItemsMsg);
        mtcRefillItems.add(mtcRefillmain);
        mtcRefillmain.add(mtcItems);
        mtcRefillmain.add(mtcSideScreen);
        mtcSideScreen.add(itemPicked);
        mtcSideScreen.add(itemRestockAll);
        mtcSideScreen.add(itemRestockOne);
        mtcSideScreen.add(textBox);
        mtcSideScreen.add(changePriceBtn);
        mtcSideScreen.add(exitBtn);
        mainFrame.add(mtcRefillItems);
        mainFrame.setPreferredSize(new Dimension(500,500));
        mainFrame.pack(); //call so that new panel appears, do this everytime panel changes
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);     
    }
    
     /**
     * Method that calls back the stocking/price setting method
     * 
     */ 
    public void bSpclMtcRefillItemsVisited(){
        mainFrame.remove(vmMaintenanceScreen);
        mainFrame.add(mtcRefillItems);
        mainFrame.pack();
    }

     /**
     * Method that calls back the Special Maintenance menu
     * 
     */ 
     public void bSpclMtncVisited(){
        mainFrame.remove(mainScreen);
        mainFrame.add(vmMaintenanceScreen);
        mainFrame.pack();
    }

    /**
     * Method that calls back the Special Maintenance
     * 
     */ 
    public void backToSpclMtc(){
        mainFrame.remove(mtcRefillItems);
        mainFrame.remove(mtcRefillDenom);
        mainFrame.add(vmMaintenanceScreen);
        mainFrame.pack();

    }   
}


