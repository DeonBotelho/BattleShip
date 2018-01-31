/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipv5;

import javax.swing.ImageIcon;

/**
 * Overview: (immutable)
 * Creates the main,game,temp and settings menu's that will be used throughout.
 * The menu's GUI visibility by default are disabled at creation; BattleShip
 * will display only the main menu for user(s) to select from the following options:
 * Player Vs. Player
 * Player Vs. Computer
 * Rules
 * Settings
 * Exit          
 * 
 * @author Christan
 * @author Deon
 * @author Nejen
 * @version 5.0
 */
public class BattleShip {    
    //Instance Variables
    //Menu's Avalible to be displayed
    private static Menu gameMenu,mainMenu,settings,temp;
    //Rules of the game to displayed 
    private static String ruleMsgs[];
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        /*Get Look and feel depending on type of computer*/
        BattleShip.setNimbus();
        //Menus that will be avalaible
        mainMenu = new Menu("Main Menu");
        gameMenu = new Menu("Game Menu");
        settings = new Menu("Settings");
        temp = new Menu("Color Scheme");
        
        //Rules of the game
        ruleMsgs = BattleShip.getGameRules();
        
        
        //Main and Game menu GameStates                       
        PlayerVPlayer pvp = new PlayerVPlayer(mainMenu,gameMenu);
        PlayerVComputer pvc = new PlayerVComputer(mainMenu,gameMenu);
        Rules rules = new Rules(ruleMsgs);
        ReturnToMenu returnTo = new ReturnToMenu(gameMenu,mainMenu);
        ReturnToMenu setColor = new ReturnToMenu(mainMenu,settings);
        Exit exit = new Exit();

        //Settings menu GameStates
        ColorModes mode1 = new ColorModes(settings,temp,new ImageIcon("unknown.png"),new ImageIcon("hit.png"),new ImageIcon("miss.png"));
        ColorModes mode2 = new ColorModes(settings,temp,new ImageIcon("unknown.png"),new ImageIcon("hit2.png"),new ImageIcon("miss2.png"));
        ColorModes mode3 = new ColorModes(settings,temp,new ImageIcon("unknown.png"),new ImageIcon("hit3.png"),new ImageIcon("miss3.png"));
        ColorModes mode4 = new ColorModes(settings,temp,new ImageIcon("unknown3.png"),new ImageIcon("hit.png"),new ImageIcon("miss.png"));
        ColorModes mode5 = new ColorModes(settings,temp,new ImageIcon("unknown3.png"),new ImageIcon("hit2.png"),new ImageIcon("miss2.png"));
        ColorModes mode6 = new ColorModes(settings,temp,new ImageIcon("unknown3.png"),new ImageIcon("hit3.png"),new ImageIcon("miss3.png"));
        
        ReturnToMenu returnToo = new ReturnToMenu(settings,mainMenu);
        
        //GameStates to be added into Main Menu
        GameState gameState1[] = {pvp, pvc, rules, setColor,exit};
        //GameStates to be added into Game Menu
        GameState gameState2[] = {rules, returnTo, exit};
        //GameStates to be added into settings Menu
        GameState gameState3[] = {mode1,mode2,mode3,mode4,mode5,mode6,returnToo};
             
        //Creating each Menu's GUI
        mainMenu.AddGameStates(gameState1);
        gameMenu.AddGameStates(gameState2);
        settings.AddGameStates(gameState3);

        //Display the main menu
        mainMenu.displayMenu();
    }
    /**
     * Requires: -
     * Modifies: java.swing.UIManager
     * Effects : Sets the Nimbus look and feel of the GUI layout     
     */
    private static void setNimbus()
    {
         /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //
        
    }
    
    /**
     * Requires: -
     * Modifies: -
     * Effects : Returns an array of Strings containing the 
     *          rules of the game.
     */
    private static String[] getGameRules()
    {
        String msgs[] = new String[3];
        msgs[0] = "The object of Battleship is to try and sink all of the         \n "
                + "other player's before they sink all of your ships. All of the   \n"
                + " other player's ships are somewhere on his/her board.           \n"
                + "You try and hit them by pressing the button of the coordinates  \n"
                + "you wish to strike on the board.  The other player also tries to\n"
                + " hit your ships by lauching using their board.  Neither you nor \n"
                + " the other player can see the other's ships so you must try     \n"
                + "to guess where they are.  Each player will have 1 board in the  \n"
                + "game. Once the set up phase is over you can launch attacks from \n"
                + "you board. The status of an attack will be displayed on each    \n"
                + "board recording the player's guesses."; 
        msgs[1] = "Phase 1: Setting up the board\n\n"
                + "Each Player will take turns setting up their own boards. During \n"
                + "this time the opposing player should look away to avoid cheating!\n"
                + "Ships may be placed one at a time by selecting a starting point\n"
                + "to place down the ship. After this you will be prompted to select\n"
                + "a direction to orientate your ship";
        msgs[2] = "Phase 2: Playing the game\n\n"
                + "Each player will alternate turns choosing a location in which to\n"
                + "strike the enemy by selecting a location on their own boards.For\n"
                + "example. Player 1 will select a location on their own board to \n"
                + "attack Player 2. The status of the attack will be displayed on \n"
                + "the attackers board. ";
        
        
        
        return msgs;
    }
    
        
}
