/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipv5;

/**
 * Overview: Terminates program, closes all instances.(immutable)
 * @author Christan
 * @author Deon
 * @author Nejen
 * @version 5.0
 */
public class Exit implements GameState {

    /**
     * Requires:-
     * Modifies:-
     * Effects : Returns text to be displayed on the menu button
     *           containing this instance of Exit.
     * @return "Exit Game"
    */
    @Override
    public String getButtonText() {
         return "Exit Game";
    }

    /**
     * Requires: -
     * Modifies: -
     * Effects : Terminates program. Closes all instances 
    */
    @Override
    public void displayGameState() {
        System.exit(0);
    }

    /**
     * Requires:-
     * Modifies:-
     * Effects : displays main menu.
    */
    @Override
    public void removeGameState() {
        BattleShip.main(null);
    }
    
}
