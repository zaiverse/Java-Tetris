package models;

import wheelsunh.users.Animator;
import wheelsunh.users.Rectangle;
import wheelsunh.users.ShapeGroup;

import java.awt.*;

/**
 * Tetronimo.java:
 * An abstract class to model the base capaabilities of a tetronimo
 *
 * @author Professor Rossi, Brenda Garcia
 * @version 2.0 December 13, 2024
 *
 * @see java.awt.Color
 */
public abstract class Tetronimo extends ShapeGroup
{
    /**
     * Constant to represent the size of the tetronimo
     */
    public static final int SIZE= 20;

    protected Rectangle r1;
    protected Rectangle r2;
    protected Rectangle r3;
    protected Rectangle r4;

    protected int curRotation = 1;

    /**
     * Generates the four rectangles for the tetronino and puts them on the screen, they are at the default coordinates
     * to start
     */
    public Tetronimo()
    {
        super();
        this.r1 = new Rectangle();
        this.r1.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r1.setFrameColor( Color.BLACK );

        this.r2 = new Rectangle();
        this.r2.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r2.setFrameColor( Color.BLACK );

        this.r3 = new Rectangle();
        this.r3.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r3.setFrameColor( Color.BLACK );

        this.r4 = new Rectangle();
        this.r4.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r4.setFrameColor( Color.BLACK );
    }

    /**
     * Increments the rotation of the tetronimo, other classes need to override this to provide the full functionality
     */
    public void rotate()
    {
        this.curRotation++;
    }

    /**
     * Shifts the tetronimo left one row
     */
    public void shiftLeft()
    {
        super.setLocation( super.getXLocation() - Tetronimo.SIZE, super.getYLocation() );
    }

    /**
     * Shifts the tetronimo right one row
     */
    public void shiftRight()
    {
        super.setLocation( super.getXLocation() + Tetronimo.SIZE, super.getYLocation() );
    }

    /**
     * Getter for r1
     * @return the first rectangle
     */
    public Rectangle getR1() {
        return this.r1;
    }

    /**
     * Getter for r2
     * @return the second rectangle
     */
    public Rectangle getR2() {
        return this.r2;
    }

    /**
     * Getter for r3
     * @return the third rectangle
     */
    public Rectangle getR3() {
        return this.r3;
    }

    /**
     * Getter for r4
     * @return the fourth rectangle
     */
    public Rectangle getR4() {
        return this.r4;
    }

    public int getCurRotation(){return curRotation;}
    public void setCurRotation(int rotate){this.curRotation = rotate;}

    /**
     * Sets the color for all rectangles in the tetronimo.
     *
     * @param color The color to apply to all blocks of this tetronimo.
     */
    public void setColor(Color color) {
        this.r1.setColor(color);
        this.r1.setFrameColor(Color.BLACK); // Ensure the border is always black
        this.r2.setColor(color);
        this.r2.setFrameColor(Color.BLACK);
        this.r3.setColor(color);
        this.r3.setFrameColor(Color.BLACK);
        this.r4.setColor(color);
        this.r4.setFrameColor(Color.BLACK);
    }
}

