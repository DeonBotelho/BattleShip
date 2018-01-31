/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipv5;

import java.awt.Point;

/**
 * Overview: Represents a mutable ship with details such as length,location and name (mutable)
 ** @author Christan
 * @author Deon
 * @author Nejen
 * @version 5.0
 */
public class Ship {

       
    /**
    *Abstraction Function:
    * AF(r) = A ship s ,used as a game piece such that
    * s.shipName = r.shipName
    * s.shipLength = r.shipLength
    * s.shipLocation = r.shipLocation
    * 
    * Rep invariant:
    * shipLength > 0
    * 0 =< shipLocation.x < GameBoard.rows && 0 =< shipLocation.y <GameBoard.cols
     */
    
    
    
    
    
    public static final Orientation RIGHT = new Orientation();
    public static final Orientation LEFT = new Orientation();
    public static final Orientation UP = new Orientation();
    public static final Orientation DOWN = new Orientation();

    private int shipLength;
    private boolean hit[];
    private String shipName;    
    private Point shipLocation[];

    /**
     * Requires:
     * @param name
     * @param shipLength
     * Modifies: this 
     * Effects : Creates an instance of this class
    */
    Ship(String name, int shipLength) {
        this.shipName = name;
        this.shipLength = shipLength;
        this.hit = new boolean[shipLength];
        this.shipLocation = new Point[shipLength];
        for(int i = 0 ; i < shipLength ; i++)
        {
            shipLocation[i] = new Point(-1,-1);             
        }
    }
    /**
     * Requires: 
     * @param p
     * Modifies: this;
     * Effects : Determines is a given location p corresponds to 
     *           one of the ships partial location. Returns true
     *           to represent a hit and false for a miss. 
     * @return  true/false
     */
    public boolean isshipHit(Point p) {
        for (int i = 0; i < shipLength; i++) {
            if (p.equals(shipLocation[i])) {
                hit[i] = true;
                System.out.println("Ship " + this.toString() + "is hit");
                return true;
            }
        }
        return false;
    }

    /**
     * Requires: -
     * Modifies: -
     * Effects : Returns the type name of the ship
     * @return shipName
    */
    public String getShipName() {
        return shipName;
    }
    
    /**
     * Requires: -
     * Modifies: -
     * Effects : Returns a clone of all locations the ship is located at
     * @return shipLocation.clone()
    */
    public Point[] getLocation()
    {
        return shipLocation.clone();
    }

    /**
     * Requires: -
     * Modifies: -
     * Effects : Returns the length of the ship
     * @return shipLength
    */
    public int getShipLength() {
        return shipLength;
    }

    /**
     * Requires: 
     * @param p
     * @param o
     * Modifies: this
     * Effects : Sets the location values of the ship based on
     *          a given point p and the orientation of the ship 
     *          denoted by o. Note: Orientation is a private class 
     *          and so to fill in the parameter o the final static
     *          constants of Ship.java must be used.
     
    */
    public void setLocation(Point p, Orientation o)
    {
        if (o.equals(DOWN)) 
        {
            for (int i = 0; i < shipLength; i++) 
            {
                shipLocation[i] = new Point((p.x + i - 1), (p.y));    
            }
        } else if (o.equals(UP)) {
            for (int i = 0; i < shipLength; i++) 
            {
                shipLocation[i] = new Point((p.x - i + 1), (p.y));
            }
        } else if (o.equals(LEFT)) {
            for (int i = 0; i < shipLength; i++) 
            {              
                shipLocation[i] = new Point((p.x), (p.y - i + 1));
            }
        } else if (o.equals(RIGHT)) {
            for (int i = 0; i < shipLength; i++) {
                shipLocation[i] = new Point((p.x), (p.y + i - 1));
             
            }
        }
        System.out.println(this);
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
        if(shipLength >0 )
        {
            flag[0] = true;
        }
        for(Point p: shipLocation)
        {
            if(p.x>0)
            {flag[1] = true;}
            if(p.y>0)
            {flag[2] = true;}
        }
            
        return (flag[0]&&flag[1]&&flag[2]);
      
    }

    /**
     * Requires: -
     * Modifies: -
     * Effects : Returns the String representation of Ship
    */
    @Override
    public String toString() {
        String s = shipName + " located at";

        for (Point p : shipLocation) {
            s += " (" + (p.x + 1) + "," + (p.y + 1) + ")";
        }

        return s;
    }

    /**
     * Overview: Dummy class made into constants for class Ship.java
     *           to determine the orientation of the ship is being 
     *           placed in the setLocation method.
     */
    public static class Orientation {

        /**
        * Requires: -
        * Modifies: this (instance of Orientation)
        * Effects : Creates an instance of this class.
        */
        private Orientation() {
        }
    }

}
