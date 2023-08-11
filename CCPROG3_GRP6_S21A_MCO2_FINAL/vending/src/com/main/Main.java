/* Header Declarations */
package com.main;
import com.vendingMachine.VendingMachine;
import com.regular.Regular;
import com.special.Special;


public class Main {
    public static void main(String[] args) 
    { 
        Regular CRegular = new Regular();
        Special CSpecial = new Special();
        CRegular.initializeItems();
        CRegular.initializeSlots();
        CSpecial.initializeItems();
        CSpecial.initializeSlots();
        CSpecial.initializeFlavors();
        CSpecial.initializeFlavorSlots();
        
        VendingMachine machine = new VendingMachine(CRegular, CSpecial);
      
    }
}