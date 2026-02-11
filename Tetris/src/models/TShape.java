package models;

import java.awt.*;

/**
 * StraightLine.java:
 * Creates a straight line tetronimo
 *
 * @author Brenda Garcia
 * @version 1.0 December 13, 2024
 *
 * @see java.awt.Point
 */
public class TShape extends Tetronimo
{
    /**
     * Creates the tetronimo and puts it in the vertical orientation
     */
    public TShape()
    {
        super.r1.setLocation( Tetronimo.SIZE, 0 );
        super.r2.setLocation( 0, Tetronimo.SIZE );
        super.r3.setLocation( Tetronimo.SIZE, Tetronimo.SIZE );
        super.r4.setLocation( Tetronimo.SIZE * 2, Tetronimo.SIZE );

        super.add( r1 );
        super.add( r2 );
        super.add( r3 );
        super.add( r4 );

        // Set the specific color
        this.setColor(Color.MAGENTA);
    }

    /**
     * Rotates the tetronimo
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
            case 1: // Default orientation (T-shape upright)
                super.r1.setLocation(Tetronimo.SIZE, 0);
                super.r2.setLocation(0, Tetronimo.SIZE);
                super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r4.setLocation(Tetronimo.SIZE * 2, Tetronimo.SIZE);
                break;

            case 2: // Rotated 90° clockwise
                super.r1.setLocation(Tetronimo.SIZE, 0);
                super.r2.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE * 2);
                super.r4.setLocation(0, Tetronimo.SIZE);
                break;

            case 3: // Rotated 180° (inverted T-shape)
                super.r1.setLocation(Tetronimo.SIZE, Tetronimo.SIZE * 2);
                super.r2.setLocation(0, Tetronimo.SIZE);
                super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r4.setLocation(Tetronimo.SIZE * 2, Tetronimo.SIZE);
                break;

            case 4: // Rotated 270° clockwise
                super.r1.setLocation(Tetronimo.SIZE, Tetronimo.SIZE * 2);
                super.r2.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r3.setLocation(Tetronimo.SIZE, 0);
                super.r4.setLocation(Tetronimo.SIZE * 2, Tetronimo.SIZE);
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
        return Tetronimo.SIZE * 2;
    }

    /**
     * Gets the width of the tetronimo based on the orientation
     *
     * @return The width of the tetronimo
     */
    @Override
    public int getWidth()
    {
        return Tetronimo.SIZE * 3;
    }
}
