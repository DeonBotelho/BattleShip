/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipv5;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Overview: No effect state, used as a dummy button.(mutable)
 * @author Christan
 * @author Deon
 * @author Nejen
 * @version 5.0
 */
public class EmptyButtons implements GameState
{
    
    private Menu calledFrom;
    
    /**
     * Requires: 
     * @param calledFrom
     * Modifies: this
     * Effects : creates an instance of this class
     */
    EmptyButtons(Menu calledFrom)
    {
        this.calledFrom = calledFrom;  
        
    }
    
    /**
     * Requires: -
     * Modifies: -
     * Effects : Returns empty text to be displayed on the menu that
     *           uses this class.
     * @return ""
    */
    @Override
    public String getButtonText() 
    {
        return "";        
    }

    /**
     * Requires: -
     * Modifies: -
     * Effects : Does nothing
    */
    @Override
    public void displayGameState() 
    {
       //Do nothing  
    }
    /**
     * Requires: -
     * Modifies: this
     * Effects : Hides the menu that calls this state
     */
    @Override
    public void removeGameState() 
    {
         calledFrom.hideMenu();
    }
    
}
