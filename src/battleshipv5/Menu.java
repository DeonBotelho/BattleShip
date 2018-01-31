/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipv5;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
/**
 * Overview : Creates a GUI menu containing a button for each 
 *            specified GameState. (mutable)
 * @author Christan
 * @author Deon
 * @author Nejen
 * @version 5.0
 */
public class Menu extends JFrame
{
    
    /**
    *Abstraction Function:
    * AF(r) = A menu m , such that
    * m.ROWS = r.ROWS
    * m.COLS = r.COLS
    * m.gameState = r.gameState
    * 
    * Rep invariant:
    * r.ROWS>0
    * r.COLS>0
    * r.gameState !=null
     */
    
    private final Color background= Color.BLACK;
    private int ROWS,COLS;
    private JButton menuB[];
    private JPanel panel;
    private ActionListener listen;
    private GameState gameState[],state;
    
    /**
     * Requires:
     * @param menuName
     * Modifies: this 
     * Effects : Creates an instance of this class
    */
    Menu(String menuName)
    {
        super(menuName);
    }
    /**
     * Requires: 
     * @param gamestates     
     * Modifies: this 
     * Effects : Adds the GameStates options this menu will have
    */
    public void AddGameStates(GameState gamestates[])
    {
        this.gameState = gamestates;
        constructMenu();    
    }
    /**
     * Requires: -
     * Modifies: this 
     * Effects : Removes all GameState options this menu has
    */
    public void removeAllStates()
    {
        gameState= null;
    }
    /**
     * Requires:-
     * @param newState
     * Modifies: this 
     * Effects : Removes the first instance of an object with
     *           the same Class type as the newState and replaces
     *           it with newState.
    */
    public void replaceGameState(GameState newState)
    {
        for(int i = 0; i<gameState.length;i++)
        {
            if(gameState[i].getClass().equals(newState.getClass()))
            {
                gameState[i].removeGameState();
                gameState[i] = newState;
            }
        }
        
    }
    /**
     * Requires: -
     * Modifies: this 
     * Effects : initializes and creates Menu GUI
    */
    private void constructMenu()
    {
        this.ROWS = gameState.length;
        this.COLS = 1;
        this.menuB = new JButton[ROWS];
        this.panel = new JPanel (new GridLayout(ROWS,COLS));        
        this.listen = new EventHandeler();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        for(int i = 0 ; i < ROWS ; i++)
        {
            menuB[i] = new JButton(gameState[i].getButtonText());
            menuB[i].addActionListener(listen);
            panel.add(menuB[i]);
        } 
        panel.setBackground(background); 
        add(panel);        
        pack();   
        this.setAlwaysOnTop(true);
    }  
    
    /**
     * Requires:-
     * Modifies: this 
     * Effects : Displays the menu GUI
    */
    public void displayMenu()
    {
        this.setVisible(true);
    }
    /**
     * Requires: -
     * Modifies: this 
     * Effects : Hides the menu GUI
    */
    public void hideMenu()
    {
        this.setVisible(false);
    }
    /**
     * Requires:-
     * Modifies: this 
     * Effects : Displays the corresponding state related to the
     *           Button pressed.
    */
    private void actionSelected(JButton b)
    {
        System.out.println(b.getText());
        int i = 0;
        for(JButton x : menuB)
        {
            if(b.equals(x))
            {                
                state = gameState[i];  
                state.displayGameState();                
                break;
            }
            i++;
        }
    }

    /**
     * Requires: -
     * Modifies: this 
     * Effects : Returns all buttons of the Menu GUI
     * @return menuB
    */
    public JButton[] getButtons()
    {
        return menuB;
    }
    
    /**
     * Overview: Handles all user input on a menu object
     */
    private class EventHandeler implements ActionListener
    {
        /**
        * Requires: 
        * @param e
        * Modifies: this (instance of Menu.java)
        * Effects : Handles user input on a menu object
        */
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JButton x = (JButton)e.getSource();           
            actionSelected(x);           
        }
    }  
    
    
    /**
     * Requires: -
     * Modifies: -
     * Effects : returns true if rep is secure, false if not
     * @return flag[0]&&flag[1]&&flag[2]
     */
    public boolean repOk()
    {
        boolean flag[] = new boolean[3];
        if(ROWS >0 )
        {
            flag[0] = true;
        }
        if(COLS >0 )
        {
            flag[1] = true;
        }
        if(gameState!=null)
        {
            flag[2] = true;
        }
            
        return (flag[0]&&flag[1]&&flag[2]);
      
    }
    /**
     * Requires: -
     * Modifies: -
     * Effects : Returns the String representation of Menu
    */
    @Override
    public String toString()
    {
        String s = this.getTitle();
        for(GameState g : gameState)
        {
            s+=g.getButtonText() +"\n";
        }
        return s;
    }
}