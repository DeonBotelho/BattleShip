/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipv5;

import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Overview: Changes the static image icons used in GameBoard.java(mutable)
 * @author Christan
 * @author Deon
 * @author Nejen
 * @version 5.0
 */
public class ColorModes implements GameState {

    
    /**
    *Abstraction Function:
    * AF(r) = Color pallet with selected image icons,c
    * c.IMAGE_UNKNONW = r.IMAGE_UNKNOWN
    * c.IMAGE_MISS =r.IMAGE_MISS
    * c.IMAGE_HIT = r.IMAGE_HIT
    * 
    * 
    * Rep invariant:
    * r.IMAGE_UNKNOWN!=null;
    * r.IMAGE_HIT!=null;
    * r.IMAGE_MISS!=null;
     */
    
    private static int modeCount = 1;
    private int currentMode;
    private ImageIcon IMAGE_UNKNOWN = new ImageIcon("unknown.png");
    private ImageIcon IMAGE_MISS = new ImageIcon("miss3.png");
    private ImageIcon IMAGE_HIT = new ImageIcon("hit3.png");
    private Menu settingsMenu;
    private Menu temp;    
    private ReturnToMenu menu;
    private GameState g[];
    private JButton b[];

    /**
     * Requires:
     * @param settingsMenu
     * @param temp
     * @param unknown
     * @param hit
     * @param miss 
     * Modifies: this
     * Effects : Creates an instance of this class
     */
    ColorModes(Menu settingsMenu, Menu temp, ImageIcon unknown, ImageIcon hit, ImageIcon miss) {

        this.settingsMenu = settingsMenu;
        this.temp = new Menu("Color Scheme");
        this.IMAGE_UNKNOWN = unknown;
        this.IMAGE_HIT = hit;
        this.IMAGE_MISS = miss;
        this.currentMode = modeCount++;
        g = new GameState[3];
        g[0] = new EmptyButtons(this.temp);
        g[1] = new EmptyButtons(this.temp);
        g[2] = new EmptyButtons(this.temp);
    }

    /**
     * Requires:-
     * Modifies:-
     * Effects : Returns text to be displayed on the menu button
     *           containing this instance of GameState
     * @return "Mode" + currentMode
     */
    @Override
    public String getButtonText() {
        return "Mode " + currentMode;
    }

    /**
     * Requires:-
     * Modifies: this , GameBoard.java
     * Effects : Display a temp menu to show new Icons used by GameBoard.java
     *           after modifying the static icons of GameBoard.java   
    */
    @Override
    public void displayGameState() {

        
        temp.removeAllStates();
        temp.AddGameStates(g);
        temp.setSize(100, 300);
        temp.setLocation(new Point(00, 250));
        b = temp.getButtons();
        b[0].setIcon(this.IMAGE_UNKNOWN);
        b[1].setIcon(this.IMAGE_HIT);
        b[2].setIcon(this.IMAGE_MISS);
        temp.displayMenu();
        GameBoard.displayMsg("Color Scheme set!");
        temp.hideMenu();

        GameBoard.setImages(IMAGE_UNKNOWN, IMAGE_HIT, IMAGE_MISS);
    }

    /**
     * Requires:-
     * Modifies: this 
     * Effects : Removes settings Menu and the temp menu.
    */
    @Override
    public void removeGameState() {
        settingsMenu.hideMenu();
        temp.hideMenu();
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
        if(IMAGE_UNKNOWN!=null )
        {
            flag[0] = true;
        }
        if(IMAGE_HIT !=null )
        {
            flag[1] = true;
        }
        if(IMAGE_MISS !=null)
        {
            flag[2] = true;
        }
            
        return (flag[0]&&flag[1]&&flag[2]);
      
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
        String s = temp.getTitle() +"\n";
              s+="[?]: "+ IMAGE_UNKNOWN.toString() +"\n";
              s+="[x]: "+ IMAGE_HIT.toString() +"\n";
              s+="[o]: "+ IMAGE_MISS.toString() +"\n";
        
        return s;      
    }
}
