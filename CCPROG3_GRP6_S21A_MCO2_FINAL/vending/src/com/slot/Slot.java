/**
 * This class is used for the inventory of items
 * that will be used in the vending machine
 *  @author Miguel
 *  @author Yumul
 */

/* Header Declarations */
package com.slot;
import com.item.Item;

public class Slot 
{
    /* fields and attributes */
    private Item CItem;
    private int nAmount;
    private int nStartInv;
    private int nPrevStartInv;
    private int nEndInv;


   /**
   * Initializes the fields of the class Slot
   * A constructor
   * 
   * @param CItem  a variable of datatype Item
   * @param nAmount the amount an item can contain
   */
    public Slot(Item CItem, int nAmount)
    {
        this.CItem = CItem;
        this.nAmount = nAmount;
        nStartInv = nAmount;
        nPrevStartInv = nAmount;
        nEndInv = 0;
    }

   /**
   * Method to display vending machine item set
   * @return the displayed statement
   */
    public String display()
    {
        return "Starting Inv : " + nPrevStartInv + " | End Inv: " + nEndInv + " | Item: " + CItem.getItemName() + " |  Price: "  + CItem.getPrice() + " | Calories: " + CItem.getCalories();
    }

   /**
   * Decreases the current stock of a chosen item
   * If the stock of the item is decreased
   * 
   */
    public void decreaseAmount()
    {
        nAmount--;
    }

    /* Getter and setter methods */

  /**
   * Allows other classes to see the content of variable CItem
   * @return the item desired
   */
    public Item getItem()
    {
        return CItem;
    }
    
/**
   * Allows other classes to see the content of variable nAmount
   * @return the amount desired
   */
    public int getAmount()
    {
        return nAmount;
    }

/**
   * Allows other classes to change the content of variable CItem
   * @param CItem is the item desired
   * 
   */
    public void setItem(Item CItem)
    {
        this.CItem = CItem;
    }

/**
   * Allows other classes to change the content of variable nAmount
   * @param nAmount is the amount of an item desired
   * 
   */
    public void setAmount(int nAmount)
    {
        this.nAmount = nAmount;
    }

    public void addAmount(int nAmount){
        if(!(this.nAmount >= 10))
            this.nAmount += nAmount;
    }

    public void invEnd(){
        nEndInv = nAmount;
        nPrevStartInv = nStartInv;

    }

    public void invStart(){
        nStartInv = nAmount;
    }

    public int getEndInv(){
        return nEndInv;
    }

    public int getPrevStartInv(){
        return nPrevStartInv;
    }

    public int getStartInv(){
        return nStartInv;
    }


}


