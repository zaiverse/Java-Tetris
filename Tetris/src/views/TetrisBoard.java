package views;

import controllers.TetrisController;
import models.Tetronimo;
import wheelsunh.users.*;
import wheelsunh.users.Frame;
import wheelsunh.users.Rectangle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * TetrisBoard.java:
 * Class to model the tetris board
 *
 * @author Professor Rossi, Brenda Garcia
 * @version 2.0 December 13, 2024
 *
 * @see java.awt.Color
 * @see java.awt.event.KeyListener
 * @see java.awt.event.KeyEvent
 */
public class TetrisBoard implements KeyListener
{
    /**
     * Constant to represent the width of the board
     */
    public static final int WIDTH = 10;

    /**
     * Constant to represnet the height of the board
     */
    public static final int HEIGHT = 24;

    private final TetrisController CONTROLLER;
    private Tetronimo tetronimo;
    private Rectangle[][] playingField;
    private boolean[][] occupiedCells;

    private Rectangle[] previewArea;
    private int score;
    private TextBox scoreDisplay; // Display object for the score
    /**
     * Constructor to initialize the board
     *
     * @param frame The wheelsunh frame (so we can add this class as a key listener for the frame)
     */
    public TetrisBoard( Frame frame )
    {
        frame.addKeyListener( this );
        this.CONTROLLER = new TetrisController( this );

        this.buildBoard();
        this.buildPreviewArea(); // Initialize the preview area
        this.initializeScoreDisplay(); // Initialize the score display

        this.run();
    }

    /**
     * Builds the playing field for tetris
     */
    private void buildBoard() {
        this.playingField = new Rectangle[WIDTH][HEIGHT];
        this.occupiedCells = new boolean[WIDTH][HEIGHT]; // Initialize occupied cells

        for (int col = 0; col < TetrisBoard.WIDTH; col++) {
            for (int row = 0; row < TetrisBoard.HEIGHT; row++) {
                this.playingField[col][row] = new Rectangle();
                this.playingField[col][row].setLocation(col * Tetronimo.SIZE + 40, row * Tetronimo.SIZE);
                this.playingField[col][row].setSize(Tetronimo.SIZE, Tetronimo.SIZE);
                this.playingField[col][row].setColor(Color.WHITE); // Initial background color
                this.playingField[col][row].setFrameColor(Color.BLACK); // Add grid lines
                this.occupiedCells[col][row] = false; // Initialize as unoccupied
            }
        }
    }

    /**
     * Starts gameplay and is responsible for keeping the game going
     */
    public void run() {
        boolean gameOver = false;

        Tetronimo nextTetronimo = this.CONTROLLER.getNextTetromino();

        while (!gameOver) {
            // Get the next tetronimo
            this.tetronimo = nextTetronimo; // Set the current tetromino
            this.tetronimo.setLocation(40 + (TetrisBoard.WIDTH / 2) * Tetronimo.SIZE, 0); // Move to starting position

            // Prepare next tetronimo
            nextTetronimo = this.CONTROLLER.getNextTetromino();
            this.renderPreviewTetromino(nextTetronimo); // Display the next tetromino in the preview area

            System.out.println("New tetronimo created.");

            // Debug: Log initial tetromino block positions
            Rectangle[] blocks = {this.tetronimo.getR1(), this.tetronimo.getR2(), this.tetronimo.getR3(), this.tetronimo.getR4()};
            for (Rectangle block : blocks) {
                System.out.println("Tetronimo block at (" + block.getXLocation() + ", " + block.getYLocation() + ")");
            }

            // Move the tetronimo down until it lands
            while (!this.CONTROLLER.tetronimoLanded(this.tetronimo)) {
                System.out.println("Tetronimo is falling.");
                this.tetronimo.setLocation(this.tetronimo.getXLocation(), this.tetronimo.getYLocation() + Tetronimo.SIZE);

                // Debug: Log tetronimo position after moving
                for (Rectangle block : blocks) {
                    System.out.println("Block moved to (" + block.getXLocation() + ", " + block.getYLocation() + ")");
                }

                Utilities.sleep(500);
            }

            System.out.println("Tetronimo has landed.");

            // Mark tetromino cells as occupied in the playing field
            for (Rectangle block : blocks) {
                int blockX = (block.getXLocation() - 40) / Tetronimo.SIZE;
                int blockY = block.getYLocation() / Tetronimo.SIZE;
                System.out.println("Block at (" + blockX + ", " + blockY + ") is now occupied.");
                playingField[blockX][blockY] = block;
                occupiedCells[blockX][blockY] = true;

                if (blockY == 0) {
                    gameOver = true;
                    System.out.println("Game Over! Block reached the top row.");
                }
            }

            // Clear full rows
            clearFullRows(this.tetronimo);

            // Prepare for the next tetromino
            this.tetronimo = null;
        }
    }

    /**
     * Retrieves the array representing which cells on the board are occupied.
     *
     * @return A 2D boolean array where {@code true} indicates an occupied cell and {@code false} indicates an empty cell.
     */
    public boolean[][] getOccupiedCells(){return this.occupiedCells;}

    /**
     * This method is not used in this program
     *
     * @param e The key event
     */
    @Override
    public void keyTyped( KeyEvent e )
    {
        //not in use
    }

    /**
     * Handles the key events by the user (INCOMPLETE)
     *
     * @param e The key event
     */
    @Override
    public void keyPressed( KeyEvent e )
    {
        int key = e.getKeyCode();

        if( this.tetronimo == null )
        {
            return;
        }

        switch( key )
        {
            case 38: // Up arrow for rotation
                // Backup the current positions of the blocks
                Rectangle[] blocks = {this.tetronimo.getR1(), this.tetronimo.getR2(), this.tetronimo.getR3(), this.tetronimo.getR4()};
                int[][] currentPositions = new int[4][2];
                for (int i = 0; i < blocks.length; i++) {
                    currentPositions[i][0] = blocks[i].getXLocation();
                    currentPositions[i][1] = blocks[i].getYLocation();
                }

                // Simulate the rotation
                this.tetronimo.rotate();

                // Check the new positions of the blocks after rotation
                boolean withinBounds = true;
                for (Rectangle block : blocks) {
                    int x = block.getXLocation();
                    int y = block.getYLocation();

                    // Check left, right, top, and bottom boundaries
                    if (x < 40 || x >= 40 + WIDTH * Tetronimo.SIZE || y < 0 || y >= HEIGHT * Tetronimo.SIZE) {
                        withinBounds = false;
                        break;
                    }
                }

                if (!withinBounds) {
                    System.out.println("Rotation blocked: Out of bounds.");
                    // Revert to previous positions
                    for (int i = 0; i < blocks.length; i++) {
                        blocks[i].setLocation(currentPositions[i][0], currentPositions[i][1]);
                    }
                    this.tetronimo.setCurRotation(this.tetronimo.getCurRotation() - 1); // Revert rotation state
                    if (this.tetronimo.getCurRotation() < 1) {
                        this.tetronimo.setCurRotation(4); // Handle wraparound
                    }
                }
                break;


            case 37: // Shift left
                boolean canShiftLeft = true;
                for (Rectangle block : new Rectangle[]{this.tetronimo.getR1(), this.tetronimo.getR2(), this.tetronimo.getR3(), this.tetronimo.getR4()}) {
                    if (block.getXLocation() - Tetronimo.SIZE < 40) {
                        canShiftLeft = false;
                        break;
                    }
                }
                if (canShiftLeft) {
                    this.tetronimo.shiftLeft();
                }
                break;

            case 39: // Shift right
                boolean canShiftRight = true;
                for (Rectangle block : new Rectangle[]{this.tetronimo.getR1(), this.tetronimo.getR2(), this.tetronimo.getR3(), this.tetronimo.getR4()}) {
                    if (block.getXLocation() + Tetronimo.SIZE >= 40 + TetrisBoard.WIDTH * Tetronimo.SIZE) {
                        canShiftRight = false;
                        break;
                    }
                }
                if (canShiftRight) {
                    this.tetronimo.shiftRight();
                }
                break;
        }

    }

    /**
     * This method is not used in this program
     *
     * @param e The key event
     */
    @Override
    public void keyReleased( KeyEvent e )
    {
        //not in use
    }

    /**
     * Clears fully occupied rows and shifts rows above them down.
     *
     * @param tetronimo The current tetronimo, used for score updating.
     */
    private void clearFullRows(Tetronimo tetronimo) {
        int rowsCleared = 0;

        for (int row = TetrisBoard.HEIGHT - 1; row >= 0; row--) {
            boolean isFullRow = true;

            // Check if the row is full
            for (int col = 0; col < TetrisBoard.WIDTH; col++) {
                if (!occupiedCells[col][row]) {
                    isFullRow = false;
                    break;
                }
            }

            if (isFullRow) {
                rowsCleared++;

                // Remove rectangles in the completed row
                for (int col = 0; col < TetrisBoard.WIDTH; col++) {
                    playingField[col][row].setColor(Color.WHITE); // Set to background color
                    playingField[col][row].setFrameColor(Color.WHITE);
                    playingField[col][row].setSize(0, 0); // Shrink the rectangle
                    occupiedCells[col][row] = false; // Mark as unoccupied
                }

                // Shift rows above down
                for (int clearRow = row; clearRow > 0; clearRow--) {
                    for (int col = 0; col < TetrisBoard.WIDTH; col++) {
                        playingField[col][clearRow] = playingField[col][clearRow - 1];
                        occupiedCells[col][clearRow] = occupiedCells[col][clearRow - 1];

                        if (playingField[col][clearRow] != null) {
                            playingField[col][clearRow].setLocation(col * Tetronimo.SIZE + 40, clearRow * Tetronimo.SIZE);
                            playingField[col][clearRow].setFrameColor(Color.BLACK);
                        }
                    }
                }

                // Clear the topmost row (row 0)
                for (int col = 0; col < TetrisBoard.WIDTH; col++) {
                    playingField[col][0].setColor(Color.WHITE);
                    playingField[col][0].setFrameColor(Color.BLACK);
                    playingField[col][0].setSize(Tetronimo.SIZE, Tetronimo.SIZE);
                    playingField[col][0].setLocation(col * Tetronimo.SIZE + 40, 0);
                    occupiedCells[col][0] = false;
                }

                row++; // Recheck the same row index after shifting
            }
        }

        // Update the score based on rows cleared
        if (rowsCleared > 0) {
            int points = 0;

            switch (rowsCleared) {
                case 1:
                    points = 100; // Single row
                    break;
                case 2:
                    points = 300; // Double rows
                    break;
                case 3:
                    points = 500; // Triple rows
                    break;
                case 4:
                    points = 800; // Tetris (4 rows)
                    break;
                default:
                    points = 0;
                    break;
            }
            this.score += points;
            this.updateScoreDisplay();
        }
    }

    /**
     * Builds the preview area for displaying the next tetronimo.
     */
    private void buildPreviewArea() {
        this.previewArea = new Rectangle[4]; // A tetromino consists of 4 blocks
        int previewStartX = 500; // Position for the preview area
        int previewStartY = 50;

        for (int i = 0; i < previewArea.length; i++) {
            previewArea[i] = new Rectangle();
            previewArea[i].setSize(Tetronimo.SIZE, Tetronimo.SIZE);
            previewArea[i].setLocation(previewStartX, previewStartY);
            previewArea[i].setColor(Color.WHITE);
            previewArea[i].setFrameColor(Color.BLACK);
        }
    }

    /**
     * Renders the next tetronimo in the preview area.
     *
     * @param nextTetromino The tetronimo to be displayed in the preview area.
     */
    private void renderPreviewTetromino(Tetronimo nextTetromino) {
        Rectangle[] blocks = {nextTetromino.getR1(), nextTetromino.getR2(), nextTetromino.getR3(), nextTetromino.getR4()};

        for (int i = 0; i < previewArea.length; i++) {
            previewArea[i].setLocation(blocks[i].getXLocation() + 460, blocks[i].getYLocation() + 20);
            previewArea[i].setColor(blocks[i].getColor());
            previewArea[i].setFrameColor(Color.BLACK);
        }
    }

    /**
     * Initializes the score display and sets the initial score to 0.
     */
    private void initializeScoreDisplay() {
        this.score = 0;

        this.scoreDisplay = new TextBox();
        this.scoreDisplay.setLocation(500, 200); // Position the score display
        this.scoreDisplay.setSize(100, 50);
        this.scoreDisplay.setText("Score: 0");
        this.scoreDisplay.setColor(Color.WHITE);
        this.scoreDisplay.setFrameColor(Color.BLACK);
    }

    /**
     * Updates the score display with the current score.
     */
    private void updateScoreDisplay() {
        this.scoreDisplay.setText("Score: " + this.score);
    }

}