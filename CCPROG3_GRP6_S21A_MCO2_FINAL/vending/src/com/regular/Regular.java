/**
 * This class is used for the features and maintenance
 * of the regular vending machine
 * it represents the properties of the vending mahine and how its being used 
 * along with updating the and modifying the items and the other attributes 
 * within the vending machine 
 *  @author Miguel
 *  @author Yumul
 */

/* Header Declarations */
package com.regular;

import com.item.Item;
import com.slot.Slot;

public class Regular {

    /* Field Declarations */
    protected Item[] aItem = new Item[9];
    protected Slot[] aSlot = new Slot[8];
    protected final int[] aMoney = { 1, 5, 10, 20, 50, 100, 500, 1000};
    protected int[] aMoneyCount = { 5, 5, 5, 5, 5, 5, 5, 5};
    protected int[] aDenomInsert = { 0, 0, 0, 0, 0, 0, 0, 0};
    protected int nTotalAmount = 0;
    protected int nTotalCurrent = 0;
    protected int nTotalPrev = 0;
    protected int nChange = 0;
    protected int nItemIndex = 0;
    protected int[] aTempMoneyCount;
    protected final int nStartInv = 10;

    /**
     * Method that displays the item descriptions in the regular vending machine
     */
    public void machineDisplay() {
        System.out.println("Amount" + "\t\t\t" + "Item" + "\t\t\t" + "Price" + "\t\t\t" + "Calories");
        System.out.println("[0]" + aSlot[0].display());
        System.out.println("[1]" + aSlot[1].display());
        System.out.println("[2]" + aSlot[2].display());
        System.out.println("[3]" + aSlot[3].display());
        System.out.println("[4]" + aSlot[4].display());
        System.out.println("[5]" + aSlot[5].display());
        System.out.println("[6]" + aSlot[6].display());
        System.out.println("[7]" + aSlot[7].display());
    }

    /**
     * Method that assigns item in each slot
     */
    public void initializeItems() {
        aItem[0] = new Item("Butterfingers (1 piece)", 80, 250); // updated info alr
        aItem[1] = new Item("Oreos", 45, 53); // updated info alr, nag guess lang sa price lol
        aItem[2] = new Item("Butter Sugar", 50, 140);
        aItem[3] = new Item("Egg Tart", 55, 225);
        aItem[4] = new Item("Macaroon", 90, 97); // updated info alr
        aItem[5] = new Item("Butter Croissant", 70, 231);
        aItem[6] = new Item("Brownies", 70, 112); //updated info alr
        aItem[7] = new Item("Glazed Donut", 50, 269);
    }

    public void newItems()
    {
        aItem[8] = new Item("Bread (1 piece)", 80, 250); // updated info alr
        aItem[9] = new Item("Pasta", 45, 53); // updated info alr, nag guess lang sa price lol
        aItem[10] = new Item("Cake", 50, 140);
        aItem[11] = new Item("Water", 55, 225);
        aItem[12] = new Item("Juice", 90, 97); // updated info alr
        aItem[13] = new Item("Cookie", 70, 231);
        aItem[14] = new Item("Seafood", 70, 112); //updated info alr
        aItem[15] = new Item("Gyoza", 50, 269);
    }

    /**
     * Method to initialize items in a slot and how many items in stock
     */
    public void initializeSlots() {
        aSlot[0] = new Slot(aItem[0], nStartInv);
        aSlot[1] = new Slot(aItem[1], nStartInv);
        aSlot[2] = new Slot(aItem[2], nStartInv);
        aSlot[3] = new Slot(aItem[3], nStartInv);
        aSlot[4] = new Slot(aItem[4], nStartInv);
        aSlot[5] = new Slot(aItem[5], nStartInv);
        aSlot[6] = new Slot(aItem[6], nStartInv);
        aSlot[7] = new Slot(aItem[7], nStartInv);
    }

    /**
     * Method to add stock to the selected denomination
     * @param nDenom is the chosen denomination
     * 
     */
    public void addDenom(int nDenom){
        for(int i = 0; i <= 7; i++){
            if(nDenom == aMoney[i])
                aDenomInsert[i]++;
        }
     }

    /**
     * Method to confirm the price of the selected product
     * @param nMoneyInserted is the total amount of money inserted by the user
     * @param strItemSelected is the item chosen by the user
     * 
     * @return true if the money inserted is greater than chosen product
     * @return false if the money inserted is less than chosen product
     */ 
    public boolean confirmPrice(int nMoneyInserted, String strItemSelected){
        int nItemIndex = 0;
        for(int i = 0; i <= 7; i++){
            if(strItemSelected.equalsIgnoreCase(aSlot[i].getItem().getItemName()))
                nItemIndex = i;
        }
        if(nMoneyInserted >= aSlot[nItemIndex].getItem().getPrice() && strItemSelected.length() != 0){
            return true;
        }
        else{
            return false;
        }
     }
    
    /**
     * Method to check if there is available stock of product
     * @param nMoneyInserted is the total amount of money inserted by the user
     * @param strItemSelected is the item chosen by the user
     * 
     * @return true if there is stock for product
     * @return false if there is no stock for product
     */ 
    public boolean confirmItemAmount(int nMoneyInserted, String strItemSelected){
        int nItemIndex = 0;
        for(int i = 0; i <= 7; i++){
            if(strItemSelected.equalsIgnoreCase(aSlot[i].getItem().getItemName()))
                nItemIndex = i;
        }
        if(aSlot[nItemIndex].getAmount() != 0){
            return true;
        }
        else{
            return false;
        }
     }

    /**
     * Method to check if the machine has denominations for change
     * @param nMoneyInserted is the total amount of money inserted by the user
     * @param strItemSelected is the item chosen by the user
     * 
     * @return true if there is available change
     * @return false if there is no available change
     */ 
    public boolean checkChange(int nMoneyInserted, String strItemSelected){
        int nItemIndex = 0;
        for(int i = 0; i <= 7; i++){
            if(strItemSelected.equalsIgnoreCase(aSlot[i].getItem().getItemName()))
                nItemIndex = i;
        }
        nChange = nMoneyInserted - aSlot[nItemIndex].getItem().getPrice();
        int nTempChange = nChange;
        int[] aTempMoneyCount = aMoneyCount.clone();
        while (nTempChange > 0) {
             if (nTempChange >= 1000 && aTempMoneyCount[7] > 0) {
                    nTempChange -= aMoney[7]; // minus nDenomination index from change
                    aTempMoneyCount[7]--; // minus number of money count
                } else if (nTempChange >= 500 && aTempMoneyCount[6] > 0) {
                    nTempChange -= aMoney[6]; // minus nDenomination index from change
                    aTempMoneyCount[6]--; // minus number of money count
                } else if (nTempChange >= 100 && aTempMoneyCount[5] > 0) {
                    nTempChange -= aMoney[5]; // minus nDenomination index from change
                    aTempMoneyCount[5]--; // minus number of money count
                } else if (nTempChange >= 50 && aTempMoneyCount[4] > 0) {
                    nTempChange -= aMoney[4];
                    aTempMoneyCount[4]--;
                } else if (nTempChange >= 20 && aTempMoneyCount[3] > 0) {
                    nTempChange -= aMoney[3];
                    aTempMoneyCount[3]--;
                } else if (nTempChange >= 10 && aTempMoneyCount[2] > 0) {
                    nTempChange -= aMoney[2];
                    aTempMoneyCount[2]--;
                } else if (nTempChange >= 5 && aTempMoneyCount[1] > 0) {
                    nTempChange -= aMoney[1];
                    aTempMoneyCount[1]--;
                } else if (nTempChange >= 1 && aTempMoneyCount[0] > 0) {
                    nTempChange -= aMoney[0];
                    aTempMoneyCount[0]--;
                } else {
                    nTempChange = -1;
                }
            }
        if(nTempChange == -1){
            nChange = 0;
            return false;
        }
        else{
            aMoneyCount = aTempMoneyCount.clone();
            return true;
        }
     }

    /**
     * Method to dispense the product
     * @param nMoneyInserted is the total amount of money inserted by the user
     * @param strItemSelected is the item chosen by the user
     */ 
    public void dispense(int nMoneyInserted, String strItemSelected){
         int nItemIndex = 0;
        for(int i = 0; i <= 7; i++){
            if(strItemSelected.equalsIgnoreCase(aSlot[i].getItem().getItemName()))
                nItemIndex = i;
            aDenomInsert[i] = 0;
        }
        nTotalAmount += nMoneyInserted;
        aSlot[nItemIndex].decreaseAmount();
        aSlot[nItemIndex].getItem().timesSold();
        nChange = 0;
        nItemIndex = -1;
        System.out.println("Change in Machine");
        for(int i = 0; i<= 7; i++){
            System.out.print(aMoneyCount[i] + " | ");
        }
        System.out.println(" ");
    }

    /**
     * Method to restock all the products
     */ 
    public void restockAllSlots(){
        for(int i = 0; i <= 7; i++){
            aSlot[i].setAmount(nStartInv);
        }
    }

    /**
     * Method to restock a specific product
     */ 
    public void restockOneSlot(int nItemIndex){
            aSlot[nItemIndex].addAmount(1);
    }

    /**
     * Method to modify the price of a product
     * @param nPriceChange the new price of the product
     * @param nItemIndex the selected product
     */ 
    public void changePrice(int nPriceChange, int nItemIndex){
        aSlot[nItemIndex].getItem().setPrice(nPriceChange);
    }

    /**
     * Method to add count to a specific denomination
     * @param nMoneyIndex the chosen denomination
     */ 
    public void addMoneyCount(int nMoneyIndex){
        aMoneyCount[nMoneyIndex]++;
    }

    /**
     * Method to add count to all  denomination
     */ 
    public void addMoneyCountAll(){
        for(int i = 0; i <= 7; i++){
            aMoneyCount[i]++;
        }
    }

    /**
     * Method to collect all cash in machine
     */ 
    public void Collect(){
        nTotalCurrent += nTotalAmount;
        nTotalAmount = 0;
    }

    /**
     * Method to store all details of transactions
     */ 
    public void recordTransactions(){
        nTotalPrev = nTotalCurrent;
        nTotalCurrent = 0;
        for(int i = 0; i <= 7; i++){
            aSlot[i].getStartInv();
        }
    }

/**
   * Allows other classes to see the content of variable Change
   * @return the amount desired
   */
    public int getChange(){
        return nChange;
    }

    /**
   * Allows other classes to see the content of variable Slots[]
   * @return the slots desired
   */
    public Slot[] getSlots(){
        return aSlot;
    }

    /**
   * Allows other classes to see the content of variable aItemNum
   * @return the item desired
   */
    public Item getItem(int aItemNum){
        return aItem[aItemNum];
    }

    /**
   * Allows other classes to see the content of variable nMoneyDenom
   * @return the array of amount desired
   */
    public int[] getMoneyDenom(){
        return aMoney;
    }

    /**
   * Allows other classes to see the content of variable moneyCount
   * @return the array of amount desired
   */
    public int[] getMoneyCount(){
        return aMoneyCount;
    }

    /**
   * Allows other classes to see the content of variable nAmount
   * @param nMoneyDenom the chosen denomination
   * @return the amount desired
   */
    public int getMoneyCountIndex(int nMoneyDenom){
    for(int i = 0; i <= 7; i++){
        if(nMoneyDenom == aMoney[i]){
            return i;
        }
    }
        return -1;
    }

    /**
   * Allows other classes to see the content of variable nAmount
   * @return the amount desired
   */
    public int getTotalPrev(){
        return nTotalPrev;
    }
}



