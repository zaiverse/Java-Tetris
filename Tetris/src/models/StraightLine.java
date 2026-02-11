package models;

import java.awt.*;

/**
 * StraightLine.java:
 * Creates a straight line tetronimo
 *
 * @author Professor Rossi, Brenda Garcia
 * @version 2.0 December 13, 2024
 *
 * @see java.awt.Point
 */
public class StraightLine extends Tetronimo
{
    /**
     * Creates the tetronimo and puts it in the vertical orientation
     */
    public StraightLine()
    {
        super.r1.setLocation( 0, 0 );
        super.r2.setLocation( 0, Tetronimo.SIZE );
        super.r3.setLocation( 0, Tetronimo.SIZE * 2 );
        super.r4.setLocation( 0, Tetronimo.SIZE * 3 );

        super.add( r1 );
        super.add( r2 );
        super.add( r3 );
        super.add( r4 );

        // Set the specific color
        this.setColor(Color.CYAN);
    }

    /**
     * Rotates the tetronimo
     */
    /**
     * Rotates the tetronimo to handle full 360-degree rotation (90°, 180°, 270°, 360°)
     */
    @Override
    public void rotate()
    {
        super.rotate();
        // Reset curRotation to 1 if it exceeds 4
        if (super.curRotation > 4)
        {
            super.curRotation = 1;
        }

        Point curLoc = super.getLocation();
        super.setLocation(0, 0);

        switch (super.curRotation)
        {
            case 1: // Vertical (default orientation)
                super.r1.setLocation(0, 0);
                super.r2.setLocation(0, Tetronimo.SIZE);
                super.r3.setLocation(0, Tetronimo.SIZE * 2);
                super.r4.setLocation(0, Tetronimo.SIZE * 3);
                break;

            case 2: // Horizontal (rotated 90° clockwise)
                super.r1.setLocation(0, 0);
                super.r2.setLocation(Tetronimo.SIZE, 0);
                super.r3.setLocation(Tetronimo.SIZE * 2, 0);
                super.r4.setLocation(Tetronimo.SIZE * 3, 0);
                break;

            case 3: // Vertical (rotated 180° from default)
                super.r1.setLocation(0, 0);
                super.r2.setLocation(0, -Tetronimo.SIZE);
                super.r3.setLocation(0, -Tetronimo.SIZE * 2);
                super.r4.setLocation(0, -Tetronimo.SIZE * 3);
                break;

            case 4: // Horizontal (rotated 270° clockwise)
                super.r1.setLocation(0, 0);
                super.r2.setLocation(-Tetronimo.SIZE, 0);
                super.r3.setLocation(-Tetronimo.SIZE * 2, 0);
                super.r4.setLocation(-Tetronimo.SIZE * 3, 0);
                break;
        }

        super.setLocation(curLoc);
    }

    /**
     * Gets the height of the tetronimo based on the orientation
     *
     * @return The height of the tetronimo
     */
    @Override
    public int getHeight()
    {
        if( this.curRotation % 2 == 0 )
        {
            return Tetronimo.SIZE;
        }
        else
        {
            return Tetronimo.SIZE * 4;
        }
    }

    /**
     * Gets the width of the tetronimo based on the orientation
     *
     * @return The width of the tetronimo
     */
    @Override
    public int getWidth()
    {
        if( this.curRotation % 2 == 0 )
        {
            return Tetronimo.SIZE * 4;
        }
        else
        {
            return Tetronimo.SIZE;
        }
    }
}
