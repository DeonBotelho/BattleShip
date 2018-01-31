/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipv5;

/**
 * Overview: Displays the rules of the game using dialog boxes (immutable)
 ** @author Christan
 * @author Deon
 * @author Nejen
 * @version 5.0
 */
public class Rules implements GameState {
    
    
    /**
    *Abstraction Function:
    * AF(r) = Rules of the game s, such that
    * s.messages = r.messages
    * 
    * Rep invariant:
    * r.messages!=null;
     */
    
    
    
    private String messages[];
    /**
     * Requires:
     * @param msg
     * Modifies: this
     * Effects : Creates an instance of this class
     */
    Rules(String msg[])
    {
        messages = msg;
    }
    /**
     * Requires: -
     * Modifies: -
     * Effects : Returns text to be displayed on the menu button
     *           containing this instance of Rules.java
     * @return "Rules"
    */
    @Override
    public String getButtonText()
    {
        return "Rules";
    }
    /**
     * Requires: -
     * Modifies: -
     * Effects : Display a dialog box for each String stored in messages[]
    */
    @Override
    public void displayGameState()
    {
        for(String message :messages)
        {
            GameBoard.displayMsg(message);
        }
    }
    /**
     * Requires: -
     * Modifies: -
     * Effects : logs Rules action is completed in standard output
    */
    @Override
    public void removeGameState()
    {
       //nothing to do since dialog box handles its own instance
        System.out.println("Rules displayed");
    }

    /**
     * Requires: -
     * Modifies: -
     * Effects : returns true if rep is secure, false if not
     * @return flag
     */
    public boolean repOk()
    {
        boolean flag =false;
        if(messages!=null)
        {
            flag = true;
        }      
        return flag;
    }

    /**
     * Requires: -
     * Modifies: -
     * Effects : returns String representation of Rules
     * @return s
     */
    @Override
    public String toString()
    {
        String s = "";
        for(String x:messages)
        {
            s+= x +"\n\n";
        }
        return s;      
    }
    
}
