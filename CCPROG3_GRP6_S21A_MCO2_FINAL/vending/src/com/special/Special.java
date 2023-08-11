/**
 * This class is used for the features and maintenance
 * of the special vending machine
 * it represents the properties of the vending mahine and how its being used to create a customized product
 * along with updating the and modifying the items and the other attributes 
 * within the vending machine 
 *  @author Miguel
 *  @author Yumul
 */

/* Header Declarations */
package com.special;

import com.regular.Regular;
import com.item.Item;
import com.slot.Slot;
import java.util.*;


public class Special extends Regular {

    /* fields and attributes */
    private final int nCakeBasePrice = 1950;
    private final int nCakeBaseCal = 424;
    private Slot[] aSlot = new Slot[4];
    private Cake CCake = new Cake(); //variable for cake
    private Item[] CFlavor = new Item[5]; //variable for CFlavor (cannot be sold alone)
    private Slot[] CFlavorSlot = new Slot[5];
    private ArrayList<Item> aToppings = new ArrayList<>();
    private int[] aToppingAmount = {0,0,0,0};
    private int aToppingsPrice = 0;
    private int nToppingsCal = 0;
    private int nSelectedTotal = 0;
    private int nSelectedCalories = 0;
    private int nFlavorIndex = -1;

    /**
     * Method to initialize flavors of cake along with prices and calories per flavor
     */
    public void initializeFlavors()
    {
        CFlavor[0] = new Item("Strawberry",74,10);
        CFlavor[1] = new Item("Chocolate",10,10);
        CFlavor[2] = new Item("Cookies and Cream",10,10);
        CFlavor[3] = new Item("Mango",74,10);
        CFlavor[4] = new Item("Vanilla",59,12);
    }

    /**
     * Method to initialize the amount of stock each flavors have 
     */
    public void initializeFlavorSlots()
    {
        CFlavorSlot[0] = new Slot(CFlavor[0], nStartInv);
        CFlavorSlot[1] = new Slot(CFlavor[1], nStartInv);
        CFlavorSlot[2] = new Slot(CFlavor[2], nStartInv);
        CFlavorSlot[3] = new Slot(CFlavor[3], nStartInv);
        CFlavorSlot[4] = new Slot(CFlavor[4], nStartInv);
    }

    /**
     * Method to initialize items and slots
     */
    public void initializeSlots() {
        aSlot[0] = new Slot(aItem[0], nStartInv);
        aSlot[1] = new Slot(aItem[1], nStartInv);
        aSlot[2] = new Slot(aItem[4], nStartInv);
        aSlot[3] = new Slot(aItem[6], nStartInv);
    }

    /**
     * Method to add flavor to the cake
     * @param strFlavorSelected flavor chosen by user
     */
    public void addFlavor(String strFlavorSelected){
        for(int i = 0; i <= 4; i++){
            if(strFlavorSelected.equalsIgnoreCase(CFlavorSlot[i].getItem().getItemName()))
                nFlavorIndex = i;
        }
        nSelectedTotal =  (nCakeBasePrice + CFlavorSlot[nFlavorIndex].getItem().getPrice() + aToppingsPrice);
        nSelectedCalories = (nCakeBaseCal + CFlavorSlot[nFlavorIndex].getItem().getCalories() + nToppingsCal);
    } 

    /**
     * Method to add topping to the cake
     * @param nItemIndex topping chosen by user
     */
    public void addTopping(int nItemIndex){
        if(nFlavorIndex != -1){
            aToppings.add(aSlot[nItemIndex].getItem());
            aToppingAmount[nItemIndex]++;
            aToppingsPrice += aSlot[nItemIndex].getItem().getPrice();
            nToppingsCal += aSlot[nItemIndex].getItem().getCalories();
            nSelectedTotal =  (nCakeBasePrice + CFlavorSlot[nFlavorIndex].getItem().getPrice() + aToppingsPrice);
            nSelectedCalories = (nCakeBaseCal + CFlavorSlot[nFlavorIndex].getItem().getCalories() + nToppingsCal);
        }
    }

    /**
     * Method to reset the special vending machine
     */
    public void resetSpcl(){
        aToppings = new ArrayList<>();
        for(int i = 0; i <=3; i++){
            aToppingAmount[i] = 0;
        }
        aToppingsPrice = 0;
        nToppingsCal = 0;
        nSelectedTotal = 0;
        nSelectedCalories = 0;
        nFlavorIndex = -1;
    }

        /**
     * Method to confirm price of item
     * @param nMoneyInserted total money inserted by user
     */
    public boolean confirmPrice(int nMoneyInserted){
        if(nMoneyInserted >= nSelectedTotal){
            return true;
        }
        else{
            return false;
        }
    }

        /**
     * Method to confirm item stock
     * @param nMoneyInserted total money inserted by user
     */
    public boolean confirmItemAmount(int nMoneyInserted){
        for(int i = 0; i <= 3; i++){
            if(aToppingAmount[i] > aSlot[i].getAmount() && !(aSlot[i].getAmount() == 0)){
                return false;
            }
        }
        if(CFlavorSlot[nFlavorIndex].getAmount() <= 0){
            return false;
        }

        return true;
    }

    /**
     * Method to check change needed and availability
     * @param nMoneyInserted total money inserted by user
     */
    public boolean checkChange(int nMoneyInserted){

        nChange = nMoneyInserted - CCake.getTotalPrice();
        int nTempChange = nChange;
        int[] aTempMoneyCount = aMoneyCount.clone();
        while (nTempChange > 0) {
             if (nTempChange >= 1000 && aTempMoneyCount[7] > 0) {
                    nTempChange -= aMoney[7]; // minus denomination index from change
                    aTempMoneyCount[7]--; // minus number of money count
                } else if (nTempChange >= 500 && aTempMoneyCount[6] > 0) {
                    nTempChange -= aMoney[6]; // minus denomination index from change
                    aTempMoneyCount[6]--; // minus number of money count
                } else if (nTempChange >= 100 && aTempMoneyCount[5] > 0) {
                    nTempChange -= aMoney[5]; // minus denomination index from change
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
     * Method to dispense of chosen item
     * @param nMoneyInserted total money inserted by user
     */
    public Cake dispense(int nMoneyInserted){
        CCake = new Cake();
        nTotalAmount += nMoneyInserted;
        for(int i = 0; i < aToppingAmount.length;i++){
            for(int j = 0; j < aToppingAmount[i]; j++){
                aSlot[i].decreaseAmount();
                aSlot[i].getItem().timesSold();
                aToppingAmount[i] = aToppingAmount[i] - 1;
            }
        }
        nChange = 0;
        System.out.println("Change in Machine");
        for(int i = 0; i<= 7; i++){
            System.out.print(aMoneyCount[i] + " | ");
        }
        System.out.println(" ");

        return CCake;
    }

    /**
     * Method to retsock all slots of products
     */
    public void restockAllSlots(){
        for(int i = 0; i <= 7; i++){
            aSlot[i].setAmount(nStartInv);
            CFlavorSlot[i].setAmount(nStartInv);
        }
    }

    /**
     * Method to get the number of slots a favor has
     */
    public Slot[] getFlavorSlots(){
        return CFlavorSlot;
    }

       /**
     * Method to get the available flavors
     */
    public Item[] getFlavors(){
        return CFlavor;
    }

       /**
     * Method to get the number of toppnigs
     */
    public int[] getToppingAmount(){
        return aToppingAmount;
    }

       /**
     * Method to get the total price of all selected products
     */
    public int getSelectedTotal(){
        return nSelectedTotal;
    }

       /**
     * Method to get the total calories of all selected products
     */
    public int getSelectedCalories(){
        return nSelectedCalories;
    }

    /**
     * Method to get the slots of products
     */
    public Slot[] getSlots(){
        return aSlot;
    }
}
