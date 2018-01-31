/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipv5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *Overview: Creates a GUI game board for players to interact with (mutable)
 * @author Christan
 * @author Deon
 * @author Nejen
 * @version 5.0
 */
class GameBoard extends JFrame {

    /**
    *Abstraction Function:
    * AF(r) = A game board g , such that
    * g.ROWS = r.ROWS
    * g.COLS = r.COLS
    * g.ships = r.ships
    * 
    * Rep invariant:
    * r.ROWS>0
    * r.COLS>0
    * r.ships !=null
     */
    //Constants   
    private static final ImageIcon IMAGE_UP = new ImageIcon("up.png");
    private static final ImageIcon IMAGE_DOWN = new ImageIcon("down.png");
    private static final ImageIcon IMAGE_LEFT = new ImageIcon("left.png");
    private static final ImageIcon IMAGE_RIGHT = new ImageIcon("right.png");
    //Default
    private static ImageIcon IMAGE_UNKNOWN = new ImageIcon("unknown.png");
    private static ImageIcon IMAGE_MISS = new ImageIcon("miss3.png");
    private static ImageIcon IMAGE_HIT = new ImageIcon("hit3.png");

    //Functionalality
    private Ship ships[];
    private int ROWS, COLS;
    private int shipCounter;
    

    //GUI
    private JPanel panel;
    private JButton tiles[][];
    private JButton generatedTiles[][];
    private JButton right, left, up, down;
    private ActionListener startUpListener;
    private ActionListener gameListener;
    private GameBoard player2;
    private int locationsHit = 0;

    /**
        * Requires: 
        * @param boardname
        * @param startUpListener
        * @param gameListener
        * @param player2
        * Modifies: this 
        * Effects : Creates an instance of this class.
        */
    GameBoard(String boardname, ActionListener startUpListener, ActionListener gameListener, GameBoard player2) {
        super(boardname);
        try {
            ships = new Ship[5];
            ships[0] = new Ship("Destroyer", 2);
            ships[1] = new Ship("Submarine", 3);
            ships[2] = new Ship("Battle Cruiser", 3);
            ships[3] = new Ship("Battleship", 4);
            ships[4] = new Ship("Aircraft Carrier", 5);
            this.ROWS = 10;
            this.COLS = 10;
            shipCounter = ships.length * 2;
            panel = new JPanel(new GridLayout(ROWS, COLS, 5, 5));
            tiles = new JButton[ROWS][COLS];
            generatedTiles = new JButton[ROWS][COLS];
            this.player2 = player2;
            this.startUpListener = startUpListener;
            this.gameListener = gameListener;
            initialize();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException(e);
        }

    }

    /**
    * Requires: -
    * Modifies: this 
    * Effects : Initialize GUI button layout for GameBoard
    */
    private void initialize() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.setBackground(Color.BLACK);

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                tiles[r][c] = new JButton(IMAGE_UNKNOWN);
                tiles[r][c].addActionListener(startUpListener);
                tiles[r][c].setBackground(Color.BLACK);
                //tiles[r][c].setText("("+(r+1)+","+(c+1)+")");
                panel.add(tiles[r][c]);
            }
        }
        add(panel);
        pack();
        setSize(COLS * 50 + COLS * 10, ROWS * 50 + ROWS * 10);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        if (this.player2 != null) {
            this.setLocation(dim.width - (this.getSize().width) * 2, dim.height / 2 - this.getSize().height / 2);
        } else {
            this.setLocation(dim.width - this.getSize().width, dim.height / 2 - this.getSize().height / 2);
        }

    }

    
    /**
    * Requires: 
    * @param s
    * Modifies: -
    * Effects : Display and dialog box with message s
    */
    public static void displayMsg(String s) {
        JOptionPane.showMessageDialog(new JFrame(), s);
    }


    
    /**
    * Requires: 
    * @param show
    * Modifies: this
    * Effects : Sets visibility of all buttons on the GUI layout
    *           and sets the shown image to IMAGE_UNKNOWN=[?]
    */
    public void buttonsVisible(boolean show) {
        for (JButton x[] : tiles) {
            for (JButton y : x) {
                y.setIcon(IMAGE_UNKNOWN);
                y.setVisible(show);
            }
        }
    }

    /**
    * Requires: 
    * Modifies: this
    * Effects : Sets visibility of all buttons on the GUI layout;
    *           displays only those buttons that do not corresponding  
    *           to a stored location of the ships in ships[].
    */
    public void showAllUnselectedButtons() {

        buttonsVisible(true);
        for (Ship s : ships) {
            Point location[] = s.getLocation();
            for (Point p : location) {
                if (p.x >= 0 && p.y >= 0) {
                    tiles[p.x][p.y].setVisible(false);
                }
            }
        }

    }

    
    /**
    * Requires: 
    * @param offset
    * Modifies: this
    * Effects : Generate a random button that will represent a button action
    *           from only the valid button options. Used by Computer AI to 
    *           access methods written mainly in human users in mind.
    * @return b
    */
    public JButton generateButton(int offset) {
        boolean flag = true;
        JButton b = new JButton();
        int row = 0, col = 0;
        while (flag)//Generate a location not picked already
        {
            flag = false;
            row = (int) (Math.random() * (ROWS - offset));
            col = (int) (Math.random() * (COLS - offset));
            b = tiles[row][col];

            //redundant on setup needed for game play
            if (offset == 0) {
                for (int x = 0; x < (ROWS); x++) {
                    for (int y = 0; y < (COLS); y++) {
                        if (b.equals(generatedTiles[x][y])) {
                            flag = true;
                        }
                    }
                }
            } else if (offset > 0) {
                System.out.print("Checking if DOWN positions are Avalible: ");
                for (int x = 0; x < offset+1; x++) {
                    if (generatedTiles[row + x][col] != null) {
                        System.out.println("Invalid@(" + (row + x + 1) + "," + (col + 1) + ")");
                        flag = true;
                        break;
                    } else {
                        System.out.print("(" + (row + x + 1) + "," + (col + 1) + ") ");
                    }

                }
                System.out.println();
                System.out.print("Checking if RIGHT positions are Avalible: ");
                for (int x = 0; x < offset+1; x++) {
                    if (generatedTiles[row][col + x] != null) {
                        System.out.println("Invalid@(" + (row + 1) + "," + (col + 1 + x) + ")");
                        flag = true;
                        break;
                    } else {
                        System.out.print("(" + (row + 1) + "," + (col + 1 + x) + ") ");
                    }

                }
                System.out.println();

            }
        }

        generatedTiles[row][col] = b;
        return b;

    }

    /**
    * Requires: -
    * Modifies: -
    * Effects : Returns to total number of locations able to be 
    *           Hit on this instance of the game board.
    * @return sum
    */
    public int numOfHits() {
        int sum = 0;
        for (Ship s : ships) {
            sum = sum + s.getShipLength();
        }
        return sum;
    }

    /**
    * Requires: 
    * @param b
    * Modifies: this
    * Effects : Sets Button b icon display to IMAGE_HIT and disables
    *           the game action listener.Additionally increments the 
    *           total locations currently hit by 1.
    */
    public void setToHit(JButton b) {
        b.setIcon(IMAGE_HIT);
        b.removeActionListener(gameListener);
        locationsHit++;
    }
    /**
    * Requires: 
    * @param b
    * Modifies: this
    * Effects : Sets Button b icon display to IMAGE_MISS and disables
    *           the game action listener.
    */
    public void setToMiss(JButton b) {
        b.setIcon(IMAGE_MISS);
        b.removeActionListener(gameListener);
    }

   /**
    * Requires: -
    * Modifies: this
    * Effects : Removes the action listener required for setting up the board
    *           and replaces it with the action listener for playing the game.
    *           Additionally redisplays all buttons on the GUI game board that 
    *           may have been set to no visibility during the setup phase.
    */
    public void setToPlay() {
        for (JButton x[] : tiles) {
            for (JButton y : x) {

                y.removeActionListener(startUpListener);
                y.addActionListener(gameListener);

            }
        }
        buttonsVisible(true);

    }

    /**
    * Requires: 
    * @param b
    * Modifies: -
    * Effects : Returns the array location as a Point object of 
    *           the button b if it is found on the GameBoard. other 
    *           wise null is returned.
    * @return new Point(x, y)
    */
    public Point getButtonLocation(JButton b) {
        for (int x = 0; x < ROWS; x++) {
            for (int y = 0; y < COLS; y++) {
                if (b.equals(tiles[x][y])) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    /**
    * Requires: -
    * Modifies: -
    * Effects : Returns the button Right of a previously selected button
    *           During the first action sequence of the setup phase.
    * @return right
    */
    public JButton getRight() {
        return right;
    }

    /**
    * Requires: -
    * Modifies: -
    * Effects : Returns the button left of a previously selected button
    *           During the first action sequence of the setup phase.
    * @return left
    */
    public JButton getLeft() {
        return left;
    }

    /**
    * Requires: -
    * Modifies: -
    * Effects : Returns the button above a previously selected button
    *           During the first action sequence of the setup phase.
    * @return up
    */
    public JButton getUp() {
        return up;
    }

    /**
    * Requires: -
    * Modifies: -
    * Effects : Returns the button below a previously selected button
    *           During the first action sequence of the setup phase.
    * @return down
    */
    public JButton getDown() {
        return down;
    }

    /**
    * Requires: 
    * @param button
    * Modifies: this
    * Effects : First sequence of setup phase. Finds the location of
    *           button and displays all possible valid orientations
    *           from that location as a starting point.
    */
    public void placeShip(JButton button) {
        ActionListener current = button.getActionListeners()[0];
        button.removeActionListener(current);
        Point p = getButtonLocation(button);
        setupDisplay_orientation(p, ships[shipCounter / 2 - 1].getShipLength());

    }

    
    /**
    * Requires: 
    * @param p
    * @param length
    * Modifies: this
    * Effects : Creates a new overlay for user to select the orientation of 
    *           their ship to be placed on the GameBoard.If no valid orientations
    *           exist the button is simply disabled from having an action if 
    *           pressed. Additionally ShipCounter will be incremented to conpensate
    *           for the "wrong" button selection.
    */
    public void setupDisplay_orientation(Point p, int length) {
        boolean flag[] = {false, false, false, false};
        boolean hasoption = false;
        buttonsVisible(false);

        if (p.x > (length - 2)) {
            for (int i = 1; i < length; i++) {
                if (isShipLocated(p.x - i, p.y)) {
                    flag[0] = true;
                    break;
                }
            }
            if (!flag[0]) {
                up = tiles[p.x - 1][p.y];
                tiles[p.x - 1][p.y].setVisible(true);
                tiles[p.x - 1][p.y].setIcon(IMAGE_UP);
                System.out.println("option up");
                hasoption = true;
            }
        }
        if (p.x < ((ROWS - 1) - (length - 2))) {
            for (int i = 1; i < length; i++) {
                if (isShipLocated(p.x + i, p.y)) {
                    flag[1] = true;
                    break;
                }
            }
            if (!flag[1]) {
                down = tiles[p.x + 1][p.y];
                tiles[p.x + 1][p.y].setVisible(true);
                tiles[p.x + 1][p.y].setIcon(IMAGE_DOWN);
                System.out.println("option down");
                hasoption = true;
            }
        }
        if (p.y > (length - 2)) {
            for (int i = 1; i < length; i++) {
                if (isShipLocated(p.x, p.y - i)) {
                    flag[2] = true;
                    break;
                }
            }
            if (!flag[2]) {
                left = tiles[p.x][p.y - 1];
                tiles[p.x][p.y - 1].setVisible(true);
                tiles[p.x][p.y - 1].setIcon(IMAGE_LEFT);
                System.out.println("option left");
                hasoption = true;
            }
        }
        if (p.y < (COLS - 1) - (length - 2)) {
            for (int i = 1; i < length; i++) {
                if (isShipLocated(p.x, p.y + i)) {
                    flag[3] = true;
                    break;
                }
            }
            if (!flag[3]) {
                right = tiles[p.x][p.y + 1];
                tiles[p.x][p.y + 1].setVisible(true);
                tiles[p.x][p.y + 1].setIcon(IMAGE_RIGHT);
                System.out.println("option right");
                hasoption = true;
            }
        }

        if (!hasoption) {
            this.showAllUnselectedButtons();
            shipCounter++;
        }
    }

    /**
    * Requires: 
    * @param x
    * @param y
    * Modifies: -
    * Effects : Validates if a ship is located at a location (x,y)
    * @return true/false 
    */
    public boolean isShipLocated(int x, int y) {
        for (Ship s : ships) {
            for (Point p : s.getLocation()) {
                if (p.x == x && p.y == y) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
    * Requires: -
    * Modifies: -
    * Effects : Returns current shipCounter
    * @param shipCounter
    */
    public int getShipCounter() {
        return shipCounter;
    }

    /**
    * Requires: 
    * @param i
    * Modifies: this
    * Effects : Sets shipCounter to equal value i
    */
    public void setShipCounter(int i) {
        shipCounter = i;
    }

    /**
    * Requires: 
    * @param i
    * Modifies: -
    * Effects : Returns the ship object located at index i
    * @param ships[i]
    */
    public Ship getShip(int i) throws IllegalArgumentException {
        return ships[i];
    }
    
    /**
    * Requires: -
    * Modifies: -
    * Effects : Returns the number of locations currently hit on the game board.
    * @param locationsHit
    */
    public int numLocationsHit() {
        return locationsHit;
    }
    
    /**
    * Requires: -
    * Modifies: this
    * Effects : Resets all generated tiles to have no value. Used after computer
    *           uses a button generator function to setup the board so
    *           that it can generate tiles for all locations in play mode.
    */
    public void resetGeneratedButtons() {
        generatedTiles = new JButton[ROWS][COLS];
    }

    /**
    * Requires: 
    * @param location
    * Modifies: this
    * Effects : set a generated tile to be taken for all given points in location
    */
    public void addGeneratedButtons(Point[] location) {
        for (Point p : location) {

            generatedTiles[p.x][p.y] = tiles[p.x][p.y];

        }
    }
    /**
    * Requires: -
    * Modifies: -
    * Effects : Logs generated tile data to standard output.
    */
    public void logGeneration() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (generatedTiles[i][j] != null) {
                    System.out.print("\t(" + (i + 1) + "," + (j + 1) + ")");
                } else {
                    System.out.print("\t(" + "-" + "-" + "-" + ")");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
    * Requires: 
    * @param IMAGE_UNKNOWN
    * @param IMAGE_HIT
    * @param IMAGE_MISS
    * Modifies: GameBoard.java
    * Effects : Sets the Image icons of the buttons in GameBoard.java
    */
    public static void setImages(ImageIcon IMAGE_UNKNOWN, ImageIcon IMAGE_HIT, ImageIcon IMAGE_MISS)
    {
       GameBoard.IMAGE_UNKNOWN = IMAGE_UNKNOWN;
       GameBoard.IMAGE_HIT = IMAGE_HIT;
       GameBoard.IMAGE_MISS = IMAGE_MISS;
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
        if(ships!=null)
        {
            flag[2] = true;
        }
            
        return (flag[0]&&flag[1]&&flag[2]);
      
    }
 
    
    /**
     * Requires: -
     * Modifies: -
     * Effects : Returns the String representation of GameBoard
    */
    @Override
    public String toString()
    {
        String s = this.getTitle() + "\n";
        
        for(int i =0 ; i < ROWS ; i++)
        {
            for(int j = 0; j < COLS ; j++)
            {
                if(tiles[i][j].getIcon().equals(GameBoard.IMAGE_UNKNOWN))
                {
                    s+="? ";
                }
                else if(tiles[i][j].getIcon().equals(GameBoard.IMAGE_HIT))
                {
                    s+="X ";
                }
                else if(tiles[i][j].getIcon().equals(GameBoard.IMAGE_MISS))
                {
                    s+="O ";
                }
                else
                {
                    s+="   ";
                }
            }
            s+= "\n";
        }
        
        
        return s;
    }
}
