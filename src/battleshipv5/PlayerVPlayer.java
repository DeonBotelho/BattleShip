/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipv5;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Overview: Creates,Displays and Handles Player Versus Player games. (mutable)
 * @author Christan
 * @author Deon
 * @author Nejen
 * @version 5.0
 */
public class PlayerVPlayer implements GameState {

    private Menu mainMenu, gameMenu;
    private GameBoard player1, player2, board, otherPlayer;
    
    private ActionListener startUp, playGame;

    
    /**
     * Requires:
     * @param mainMenu
     * @param gameMenu
     * Modifies: this (instance of PlayerVPlayer)
     * Effects : creates an instance of this class
    */
    PlayerVPlayer(Menu mainMenu, Menu gameMenu) {
        
        this.mainMenu = mainMenu;
        this.gameMenu = gameMenu;
        startUp = new StartUpEvent();
        playGame = new PlayGameEvent();
        

        player2 = new GameBoard("Player 2", startUp, playGame, null);
        player1 = new GameBoard("Player 1",startUp, playGame, player2);
        board = player1;
        otherPlayer = player2;
    }
    /**
     * Requires:-
     * Modifies:-
     * Effects : Returns text to be displayed on the menu button
     *           containing this instance of PlayerVPlayer.java
     * @return "Player Vs Player"
    */
    @Override
    public String getButtonText() {
        return "Player Vs Player";
    }

    /**
     * Requires:-
     * Modifies: this (instance of PlayerVPlayer)
     * Effects : Hide main menu and display the game menu instead
     *           Additionally display both player's game boards.
    */
    @Override
    public void displayGameState() {
       
        player1.buttonsVisible(true);
        player2.buttonsVisible(true);
        player1.setVisible(true);
        mainMenu.hideMenu();
        gameMenu.displayMenu();

    }

    /**
     * Requires: -
     * Modifies: this (instance of PlayerVPlayer)
     * Effects : Hide game menu and display the main menu instead
     *           Additionally display both player's game boards are disabled.
    */
    @Override
    public void removeGameState() {
        gameMenu.hideMenu();
        mainMenu.displayMenu();
        player1.setVisible(false);
        player2.setVisible(false);
        
    }
    /**
     * Overview: Action listener to handle user Events for setting up 
     *           the game boards.
     */
    private class StartUpEvent implements ActionListener {

        /**
        * Requires: e
        * Modifies: this (instance of PlayerVPlayer)
        * Effects : Handles player's input(s) and makes the appropriate changes
        *           to the game board.
        */
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton x = (JButton) e.getSource();
            System.out.println("Button (" + (board.getButtonLocation(x).x + 1) + "," + (board.getButtonLocation(x).y + 1) + ") Selected");

            if (board.getShipCounter() > 0) {
                if (board.getShipCounter() % 2 == 0) {
                    board.placeShip(x);
                } else if (board.getShipCounter() % 2 == 1)//5,4,3,3,2
                {
                    Point p = board.getButtonLocation(x);
                    if (x.equals(board.getRight())) {
                        board.getShip(board.getShipCounter() / 2).setLocation(p, Ship.RIGHT);
                    } else if (x.equals(board.getLeft())) {
                        board.getShip(board.getShipCounter() / 2).setLocation(p, Ship.LEFT);
                    } else if (x.equals(board.getUp())) {
                        board.getShip(board.getShipCounter() / 2).setLocation(p, Ship.UP);
                    } else if (x.equals(board.getDown())) {
                        board.getShip(board.getShipCounter() / 2).setLocation(p, Ship.DOWN);
                    }
                    board.showAllUnselectedButtons();
                }
                board.setShipCounter(board.getShipCounter() - 1);
                System.out.println("Ship Counter: "+board.getShipCounter());
            }

            if (board.getShipCounter() == 0) {
                if (board == player1) {
                    board = player2;
                    player1.setVisible(false);
                    player2.setVisible(true);
                } else {
                    player1.setVisible(true);
                    player1.setToPlay();
                    player2.setToPlay();
                    GameBoard.displayMsg("All ships have been placed!");
                    GameBoard.displayMsg("Start Game, Player 1 goes first!");
                    board = player1;
                    otherPlayer = player2;
                }
            }
        }
    }
    /**
     * Overview: Action Listener to handle users Events while playing the game.
     */
    private class PlayGameEvent implements ActionListener {
        
        /**
        * Requires: e
        * Modifies: this (instance of PlayerVPlayer)
        * Effects : Handles player's input(s) and makes the appropriate changes
        *           to the game board.
        */
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton buttonClicked = (JButton) e.getSource();
            try {
                Point p = board.getButtonLocation(buttonClicked);
                boolean buttonStatus = otherPlayer.isShipLocated(p.x, p.y);

                if (buttonStatus) {
                    board.setToHit(buttonClicked);

                } else if (!buttonStatus) {
                    board.setToMiss(buttonClicked);
                }
                if (board.numLocationsHit() == board.numOfHits()) {
                    GameBoard.displayMsg("CONGRADULATIONS!!! " + board.getTitle().toUpperCase() + " WINS!");
                    board = null;
                    otherPlayer = null;
                    
                    mainMenu.replaceGameState(new PlayerVPlayer(mainMenu,gameMenu));
                }
                if (board == player1) {
                    player1.setVisible(true);
                    player2.setVisible(true);
                    board = player2;
                    otherPlayer = player1;
                } else if (board == player2) {
                    player1.setVisible(true);
                    player2.setVisible(true);
                    board = player1;
                    otherPlayer = player2;
                } 
            } catch (Exception es) {
                GameBoard.displayMsg("It is " + board.getTitle() + "'s turn!");

            }

        }
    }

}
