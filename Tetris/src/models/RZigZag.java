package models;

import java.awt.*;

/**
 * RightZShape.java:
 * Creates a right-oriented Zig-Zag tetronimo.
 *
 * @author Brenda Garcia
 * @version 2.0 December 13, 2024
 *
 * @see java.awt.Point
 */
public class RZigZag extends Tetronimo
{
    /**
     * Creates the tetronimo and puts it in the default horizontal orientation
     */
    public RZigZag()
    {
        super.r1.setLocation(0, 0);
        super.r2.setLocation(Tetronimo.SIZE, 0);
        super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
        super.r4.setLocation(Tetronimo.SIZE * 2, Tetronimo.SIZE);

        super.add(r1);
        super.add(r2);
        super.add(r3);
        super.add(r4);

        // Set the specific color
        this.setColor(Color.GREEN);
    }

    /**
     * Rotates the Right Zig-Zag tetronimo
     */
    @Override
    public void rotate()
    {
        super.rotate();
        // Reset curRotation to 1 if it exceeds 2 (only two orientations)
        if (super.curRotation > 2)
        {
            super.curRotation = 1;
        }

        Point curLoc = super.getLocation();
        super.setLocation(0, 0);

        switch (super.curRotation)
        {
            case 1: // Default horizontal orientation
                super.r1.setLocation(0, 0);
                super.r2.setLocation(Tetronimo.SIZE, 0);
                super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r4.setLocation(Tetronimo.SIZE * 2, Tetronimo.SIZE);
                break;

            case 2: // Vertical orientation
                super.r1.setLocation(Tetronimo.SIZE, 0);
                super.r2.setLocation(0, Tetronimo.SIZE);
                super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r4.setLocation(0, Tetronimo.SIZE * 2);
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
        return super.curRotation == 1 ? Tetronimo.SIZE * 2 : Tetronimo.SIZE * 3;
    }

    /**
     * Gets the width of the tetronimo based on the orientation
     *
     * @return The width of the tetronimo
     */
    @Override
    public int getWidth()
    {
        return super.curRotation == 1 ? Tetronimo.SIZE * 3 : Tetronimo.SIZE * 2;
    }
}
