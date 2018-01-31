/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipv5;

/**
 * Overview : Closes the caller Menu and opens another Menu in its place (mutable)
 * @author Christan
 * @author Deon
 * @author Nejen
 * @version 5.0
 */
public class ReturnToMenu implements GameState {

    Menu returnTo, calledFrom;

     /**
     * Requires:
     * @param calledFrom
     * @param returnTo 
     * Modifies: this
     * Effects : Creates an instance of this class
     */
   
    ReturnToMenu(Menu calledFrom, Menu returnTo) {
        this.calledFrom = calledFrom;
        this.returnTo = returnTo;
    }
    
    /**
     * Requires:-
     * Modifies:-
     * Effects : Returns text to be displayed on the menu button
     *           containing this instance of ReturnToMenu.java
     * @return returnTo.getTitle() //Title of menu to be displayed
    */
    @Override
    public String getButtonText() {
        return returnTo.getTitle();
    }

    /**
     * Requires:-
     * Modifies: this
     * Effects : Hides the caller menu and displays the desired menu to be
     *           displayed. If returnTo menu is the Main menu called from
     *           Game menu, game instances will be closed and reset as well.
    */
    @Override
    public void displayGameState() {
        if(returnTo.getTitle().equalsIgnoreCase("Main menu")&& calledFrom.getTitle().equalsIgnoreCase("Game Menu"))
        {
        returnTo.replaceGameState(new PlayerVPlayer(returnTo,calledFrom));
        returnTo.replaceGameState(new PlayerVComputer(returnTo,calledFrom));
        }
        calledFrom.hideMenu();
        returnTo.displayMenu();
    }

    /**
     * Requires:-
     * Modifies: this 
     * Effects : Displays caller menu and hides the returnTo menu
    */
    @Override
    public void removeGameState() {
        returnTo.hideMenu();
        calledFrom.displayMenu();
    }

}
