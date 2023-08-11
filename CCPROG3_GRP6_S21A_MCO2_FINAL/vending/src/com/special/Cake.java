/**
 * This class is used for the initialization of the object Cake
 * of the special vending machine
 * it shows the properties the object cake has
 *  @author Miguel
 *  @author Yumul
 */

/* Header Declarations */
package com.special;
import java.util.*;
import com.item.Item;

public class Cake {

    /* fields and attributes */
    private Item CFlavor;
    private ArrayList<Item> aToppings = new ArrayList<>();
    private int nTotalPrice;
    private int nTotalCalories;

    /**
     * Method that allows other classes to set values to specific private variables in Cake class
     * @param CFlavorSelected the chosen flavor by buyer
     * @param nSelectedPrice price of object
     * @param nSelectedCal calories of selected object
     * @param aSelectedToppings arraylist of chosen toppings
     */
    public void prepareCake(Item CFlavorSelected, int nSelectedPrice, int nSelectedCal, ArrayList<Item> aSelectedToppings){
        CFlavor = CFlavorSelected;
        nTotalPrice = nSelectedPrice;
        nTotalCalories = nSelectedCal;
        for(int i = 0; i <= aSelectedToppings.size() - 1;i++){
            aToppings.add(aSelectedToppings.get(i));
        }
    }

    /**
     * returns the arraylist of toppings
     * @return aToppings
     */
    public ArrayList<Item> getToppings(){
        return aToppings;
    }

    /**
     * returns the int total calories
     * @return nTotalCalories
     */
    public int getTotalCalories(){
        return nTotalCalories;
    }

    /**
     * returns the item flavor
     * @return CFlavor
     */
    public Item getFlavor(){
        return CFlavor;
    }

    /**
     * returns the int total price
     * @return nTotalPrice
     */
    public int getTotalPrice(){
        return nTotalPrice;
    }
}
