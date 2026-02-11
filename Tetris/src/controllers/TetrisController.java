package controllers;

import models.*;
import views.TetrisBoard;
import wheelsunh.users.Rectangle;

import java.awt.*;
import java.util.Random;

/**
 * Tetronimo.java:
 * An abstract class to model the base capaabilities of a tetronimo
 *
 * @author Professor Rossi, Brenda Garcia
 * @version 2.0 December 13, 2024
 *
 * @see java.awt.Color
 */
public class TetrisController
{
    private final TetrisBoard TETRIS_BOARD;

    /**
     * Constructor to take in a tetris board so the controller and the board can communciate
     *
     * @param tetrisBoard A tetris board instance
     */
    public TetrisController( TetrisBoard tetrisBoard )
    {
        this.TETRIS_BOARD = tetrisBoard;
    }

    /**
     * Randomly chooses the next tetronimo and returns it
     *
     * @return The next tetronimo to be played
     */
    public Tetronimo getNextTetromino()
    {
        Random random = new Random();
        int choice = random.nextInt(8);

        Tetronimo tetronimo;
        switch (choice) {
            case 0:
                tetronimo = new Square();
                break;
            case 1:
                tetronimo = new StraightLine();
                break;
            case 2:
                tetronimo = new TShape();
                break;
            case 3:
                tetronimo = new RZigZag();
                break;
            case 4:
                tetronimo = new LZigZag();
                break;
            case 5:
                tetronimo = new LRight();
                break;
            case 6:
                tetronimo = new LLeft();
                break;
            default:
                tetronimo = new Square(); // Fallback case (shouldn't occur with valid random range)
                break;
        }

        // Set initial location of the tetromino off-screen
        tetronimo.setLocation(-Tetronimo.SIZE - 40, 40); // Hidden location

        return tetronimo;
    }

    /**
     * Method to determine if the tetronimo has landed
     *
     * @param tetronimo The tetronimo to evaluate
     * @return True if the tetronimo has landed (on the bottom of the board or another tetronimo), false if it has not
     */
    public boolean tetronimoLanded(Tetronimo tetronimo) {
        boolean[][] occupied = TETRIS_BOARD.getOccupiedCells();
        Rectangle[] blocks = {tetronimo.getR1(), tetronimo.getR2(), tetronimo.getR3(), tetronimo.getR4()};

        for (Rectangle block : blocks) {
            // Calculate board coordinates
            int blockX = (block.getXLocation() - 40) / Tetronimo.SIZE;
            int blockY = block.getYLocation() / Tetronimo.SIZE;

            // Debugging: Print current block coordinates
            System.out.println("Block coordinates: (" + blockX + ", " + blockY + ")");

            // Bottom boundary check
            if (blockY + 1 >= TetrisBoard.HEIGHT) {
                System.out.println("Tetronimo landed: Reached bottom of the board.");
                return true;
            }

            // Occupied cell check
            if (occupied[blockX][blockY + 1]) {
                return true;
            }
        }

        return false; // If no blocks are at the bottom or above an occupied cell
    }

}
