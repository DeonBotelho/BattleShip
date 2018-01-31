/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipv5;

/**
 *
 * @author Christan
 * @author Deon
 * @author Nejen
 * @version 5.0
 */
public interface GameState
{
    /**
     * Requires:-
     * Modifies:-
     * Effects : Returns text to be displayed on the menu button
     *           containing this instance of GameState.
     * @return String
    */
    public String getButtonText();
    /**
     * Requires:-
     * Modifies: this
     * Effects : Display the appropriate GUI(s) for this particular
     *           state.
    */
    public void displayGameState();
    /**
     * Requires:-
     * Modifies: this
     * Effects : Removes any GUI(s) not desired to be displayed once
     *           state is over.
    */
    public void removeGameState();   
}
