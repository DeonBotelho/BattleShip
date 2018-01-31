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
 * Overview: Creates,Displays and Handles Player Versus Computer games.(mutable)
 * @author Christan
 * @author Deon
 * @author Nejen
 * @version 5.0
 */
public class PlayerVComputer implements GameState {

    private Menu mainMenu, gameMenu;
    private GameBoard player1, player2;
    private ActionListener startUp, playGame;

    /**
     * Requires:
     * @param mainMenu
     * @param gameMenu
     * Modifies: this (instance of PlayerVComputer)
     * Effects : creates an instance of this class
    */
    PlayerVComputer(Menu mainMenu, Menu gameMenu) {

        this.mainMenu = mainMenu;
        this.gameMenu = gameMenu;
        startUp = new StartUpEvent();
        playGame = new PlayGameEvent();
   
        player2 = new GameBoard("Computer",startUp, null, null);
        player1 = new GameBoard("Player 1",startUp, playGame, player2);

    }
    /**
     * Requires: -
     * Modifies: -
     * Effects : Returns text to be displayed on the menu button
     *           containing this instance of PlayerVComputer.java
     * @return "Player vs Computer"
    */
    @Override
    public String getButtonText() {
        return "Player Vs Computer";
    }

    /**
     * Requires:-
     * Modifies: this (instance of PlayerVComputer)
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
     * Modifies: this (instance of PlayerVComputer)
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
        * Requires:-
        * Modifies: this (instance of PlayerVComputer)
        * Effects : Sets up player2's(the computer's) board.
        */
        private void setUpPlayer2() {

            while (player2.getShipCounter() > 0) {
                Ship s = player2.getShip(player2.getShipCounter() / 2 - 1);
                int offset = player2.getShip(player2.getShipCounter() / 2 - 1).getShipLength() - 1;
                Point p = player2.getButtonLocation(player2.generateButton(offset));
                int i = (int) (Math.random() * 2);
                switch (i) {
                    case 0:
                        s.setLocation(new Point(p.x + 1, p.y), Ship.DOWN);
                        break;
                    case 1:
                        s.setLocation(new Point(p.x, p.y + 1), Ship.RIGHT);
                        break;
                    default:
                        break;
                }
                player2.addGeneratedButtons(s.getLocation());
                player2.setShipCounter(player2.getShipCounter() - 2);
                player2.showAllUnselectedButtons();
                player2.logGeneration();
            }
            player1.setVisible(true);
            player1.setToPlay();
            player2.setToPlay();
            player2.resetGeneratedButtons();
            GameBoard.displayMsg("All ships have been placed!");
            GameBoard.displayMsg("Start Game, Player 1 goes first!");
        }

        /**
        * Requires: e
        * Modifies: this (instance of PlayerVComputer)
        * Effects : Handles player1's input(s) and makes the appropriate changes
        *           to the game board.
        */
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton buttonClicked = (JButton) e.getSource();
            System.out.println("Button (" + (player1.getButtonLocation(buttonClicked).x + 1) + "," + (player1.getButtonLocation(buttonClicked).y + 1) + ") Selected");

            if (player1.getShipCounter() > 0) {
                if (player1.getShipCounter() % 2 == 0) {
                    player1.placeShip(buttonClicked);
                } else if (player1.getShipCounter() % 2 == 1)//5,4,3,3,2
                {
                    Point p = player1.getButtonLocation(buttonClicked);
                    if (buttonClicked.equals(player1.getRight())) {
                        player1.getShip(player1.getShipCounter() / 2).setLocation(p, Ship.RIGHT);
                    } else if (buttonClicked.equals(player1.getLeft())) {
                        player1.getShip(player1.getShipCounter() / 2).setLocation(p, Ship.LEFT);
                    } else if (buttonClicked.equals(player1.getUp())) {
                        player1.getShip(player1.getShipCounter() / 2).setLocation(p, Ship.UP);
                    } else if (buttonClicked.equals(player1.getDown())) {
                        player1.getShip(player1.getShipCounter() / 2).setLocation(p, Ship.DOWN);
                    }
                    player1.showAllUnselectedButtons();
                }
            }
            player1.setShipCounter(player1.getShipCounter() - 1);
            System.out.println("Ship Counter: "+player1.getShipCounter());
            if (player1.getShipCounter() == 0) {
                player1.setVisible(false);
                player2.setVisible(true);
                player2.resetGeneratedButtons();
                setUpPlayer2();
            }
        }
    }

    /**
     * Overview: Action Listener to handle user Events while playing the game.
     */
    private class PlayGameEvent implements ActionListener {

        /**
        * Requires:
        * @param board
        * @param otherPlayer
        * @param buttonClicked
        * Modifies: this (instance of PlayerVComputer)
        * Effects : Uses player's input(s) to makes the appropriate changes
        *           to the game board.
        */
        private void playerMove(GameBoard board, GameBoard otherPlayer, JButton buttonClicked) {
            try {
                Point p = board.getButtonLocation(buttonClicked);
                boolean buttonStatus = otherPlayer.isShipLocated(p.x, p.y);

                if (buttonStatus) {
                    board.setToHit(buttonClicked);
                } else if (!buttonStatus) {
                    board.setToMiss(buttonClicked);
                }
                if (board.numLocationsHit() == board.numOfHits()) {
                    GameBoard.displayMsg("Congradulations " + board.getTitle().toUpperCase() + " Wins!");

                    mainMenu.replaceGameState(new PlayerVComputer(mainMenu, gameMenu));

                }
            } catch (Exception e) {
                throw new IllegalArgumentException("It is" + board.getTitle() + "'s turn!");
            }
        }

        /**
        * Requires: e
        * Modifies: this (instance of PlayerVComputer)
        * Effects : Handles player's input(s) and makes the appropriate changes
        *           to the game board.
        */
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton buttonClicked = (JButton) e.getSource();
            try {
                playerMove(player1, player2, buttonClicked);
                playerMove(player2, player1, player2.generateButton(0));
                player2.logGeneration();
            } catch (Exception E) {
                GameBoard.displayMsg(E.getMessage());
            }
        }
    }

}
