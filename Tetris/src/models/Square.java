package models;

import java.awt.*;

/**
 * Square.java:
 * Creates a Square tetronimo.
 *
 * @author Brenda Garcia
 * @version 1.0 December 13, 2024
 *
 * @see java.awt.Point
 */
public class Square extends Tetronimo{

    /**
     * Creates the tetronimo and puts it in the vertical orientation
     */
    public Square(){
        super.r1.setLocation( 0, 0 );
        super.r2.setLocation( Tetronimo.SIZE, 0);
        super.r3.setLocation( 0, Tetronimo.SIZE );
        super.r4.setLocation( Tetronimo.SIZE, Tetronimo.SIZE );

        super.add( r1 );
        super.add( r2 );
        super.add( r3 );
        super.add( r4 );

        // Set the specific color
        this.setColor(Color.YELLOW);
    }

    /**
     * The square does not rotate, as its shape remains the same
     */
    @Override
    public void rotate()
    {
        // A square does not change orientation
    }

    /**
     * Gets the height of the tetromino
     *
     * @return The height of the tetromino
     */
    @Override
    public int getHeight()
    {
        return Tetronimo.SIZE * 2;
    }

    /**
     * Gets the width of the tetromino
     *
     * @return The width of the tetromino
     */
    @Override
    public int getWidth()
    {
        return Tetronimo.SIZE * 2;
    }

}
