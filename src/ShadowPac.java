import bagel.*;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;


/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2023
 * Please enter your name below
 *
 * @author Ken Liem
 * @version 1.0
 */
public class ShadowPac extends AbstractGame {
    private final String LEVEL_0_INFO = "res/level0.csv";
    private final String LEVEL_1_INFO = "res/level1.csv";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final static String MSG_TITLE_1 = "PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE";
    private final static String MSG_TITLE_2 = "Eat Pellets to Attack";
    private final static Point TITLE_POINT = new Point(260, 250);
    private final static String MSG_WON_LVL_1 = "WELL DONE!";
    private final static String MSG_WON_LVL_0 = "LEVEL COMPLETED";
    private final int[] levels = {0, 1};
    private final Font TITLE_FONT = new Font("res/FSO8BITR.TTF", 64);
    private final Font MSG_FONT = new Font("res/FSO8BITR.TTF", 24);
    private final Font SECOND_MSG_FONT = new Font("res/FSO8BITR.TTF", 40);
    private final static String MSG_LOST = "GAME OVER!";
    private final Point FIRST_MSG_POINT = new Point(60, 190);
    private final Point SECOND_MSG_POINT = new Point(200, 350);
    private boolean gameStarted = false;
    private final int gamePieces = 271;
    private final int gamePiecesLVL1 = 266;
    private String[][] gameInfo = new String[gamePieces][];
    private ArrayList<wall> wallInfo = new ArrayList<>();
    private ArrayList<dot> dotInfo = new ArrayList<>();
    private ArrayList<ghost> ghostInfo = new ArrayList<>();
    private ArrayList<cherry> cherryInfo = new ArrayList<>();
    private ArrayList<pellet> pelletInfo = new ArrayList<>();
    private int currentLevel = 0;
    private final String LEFT = "LEFT";
    private final String RIGHT = "RIGHT";
    private final String UP = "UP";
    private final String DOWN = "DOWN";
    private pacman pacman;
    private int winningframes = 0;
    private Point initalPoint = new Point();
    private boolean won = false;
    private final String GHOST_TEXT = "ghost";
    private final String RED_TEXT = "red";
    private final String BLUE_TEXT = "blue";
    private final String GREEN_TEXT = "green";
    private final String PINK_TEXT = "pink";
    private final String PELLET_TEXT = "pellet";
    private final String DOT_TEXT = "dot";
    private final String CHERRY_TEXT = "cherry";
    private final String WALL_TEXT = "wall";


    public ShadowPac() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        readCSV(LEVEL_0_INFO);
    }

    /**
     * Method used to read file and create objects (you can change
     * this method as you wish).
     */
    private void readCSV(String filename) {
        //reads the CSV

        int i = 0;
        try (BufferedReader br =
                     new BufferedReader(new FileReader(filename))) {
            String text;

            while ((text = br.readLine()) != null) {
                //Store the information within this array
                gameInfo[i] = text.split(",");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setGamePieces();
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     * Allows for the game to start when the space key is pressed.
     * Allows the game to check what direction the player wants to move in,when either the up, down, left, right key
     * is pressed.
     * Checks if player has won or loss.
     */
    @Override
    protected void update(Input input) {
        Point newPosition;
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        if (input.wasPressed(Keys.W)) {
            currentLevel = levels[1];
            clear_info();
            gameInfo = new String[gamePiecesLVL1][];
            readCSV("res/level1.csv");
        }
        //Checks whether the game has started
        if (input.wasPressed(Keys.SPACE)) {
            gameStarted = true;


        }
        if (!won) {
            checkInteraction(pacman.getCoordinates());
            //Updates Pacman's position and direction;
            if (input.isDown(Keys.DOWN)) {
                pacman.setCurrentDirection(DOWN);
                newPosition = new Point(pacman.getCoordinates().x, pacman.getCoordinates().y + pacman.getMOVEMENT());
                checkInteraction(newPosition);
            } else if (input.isDown(Keys.UP)) {
                pacman.setCurrentDirection(UP);
                newPosition = new Point(pacman.getCoordinates().x, pacman.getCoordinates().y - pacman.getMOVEMENT());
                checkInteraction(newPosition);
            } else if (input.isDown(Keys.LEFT)) {
                pacman.setCurrentDirection(LEFT);
                newPosition = new Point(pacman.getCoordinates().x - pacman.getMOVEMENT(), pacman.getCoordinates().y);
                checkInteraction(newPosition);
            } else if (input.isDown(Keys.RIGHT)) {
                pacman.setCurrentDirection(RIGHT);
                newPosition = new Point(pacman.getCoordinates().x + pacman.getMOVEMENT(), pacman.getCoordinates().y);
                checkInteraction(newPosition);

            }
            for (ghost g : ghostInfo) {
                g.move(wallInfo);
            }
        }
        //Draws the title screen and it's Messages
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        if (!gameStarted) {
            Display_msgs();
        }
        //If the player has lost all their lives, the game is over
        else if (pacman.getLives() < 0) {
            Point lostPoint = getMiddleScreen(MSG_LOST);
            TITLE_FONT.drawString(MSG_LOST, lostPoint.x, lostPoint.y);
        }
        //Checks if the player has won the current level
        else if (hasWon()) {
            if (currentLevel == levels[0]) {
                Point winPoint = getMiddleScreen(MSG_WON_LVL_0);
                TITLE_FONT.drawString(MSG_WON_LVL_0, winPoint.x, winPoint.y);
                winningframes += 1;
                if (winningframes == 300) {

                    gameStarted = false;
                    currentLevel = levels[1];
                    clear_info();
                    gameInfo = new String[gamePiecesLVL1][];
                    readCSV(LEVEL_1_INFO);
                    winningframes = 0;
                    won = false;
                }

            } else {
                Point winPoint = getMiddleScreen(MSG_WON_LVL_1);
                TITLE_FONT.drawString(MSG_WON_LVL_1, winPoint.x, winPoint.y);

            }
        } else {
            draw_game();
        }
    }

    /**
     * Checks if the player has won depending on the level's condition
     *
     * @return true or false
     */
    public boolean hasWon() {
        if (currentLevel == levels[0]) {

            for (dot d : dotInfo) {
                if (d.isActive()) {

                    return false;
                }
            }
            won = true;
            return true;
        } else {
            if (pacman.getScore() == pacman.WINNINGSCORE) {
                won = true;
                return true;
            }
        }
        return false;

    }

    /**
     * Displays all the messages that would be on the screen
     */
//Prints out the Messages for the title screen
    public void Display_msgs() {
        //To calculate the point of the bottom corner of the Messages based on the Title's Point
        if (currentLevel == levels[0]) {
            Point msgPoint = new Point(TITLE_POINT.x + FIRST_MSG_POINT.x, TITLE_POINT.y + FIRST_MSG_POINT.y);
            TITLE_FONT.drawString(GAME_TITLE, TITLE_POINT.x, TITLE_POINT.y);
            MSG_FONT.drawString(MSG_TITLE_1, msgPoint.x, msgPoint.y);
        } else {
            SECOND_MSG_FONT.drawString(MSG_TITLE_1 + '\n' + MSG_TITLE_2, SECOND_MSG_POINT.x, SECOND_MSG_POINT.y);
        }
    }

    /**
     * Draw all the object's images onto the screen based on their position.
     */
    public void draw_game() {
        pacman.drawPacman();

        for (wall w : wallInfo) {
            w.render();
        }
        for (dot d : dotInfo) {
            d.render();
        }
        for (ghost g : ghostInfo) {
            //Since ghost has different states, it checks whether ghost needs to be drawn and what kind.
            if (!g.isDead()) {
                if (pacman.isFrenzy()) {
                    g.drawFrenzyGhost(g.getCoordinates());


                } else {
                    g.drawGhost(g.getCoordinates());
                }

            }

            if (g.isFrenzy()) {
                g.increaseFrenzyFrames();
            }

        }

        for (cherry c : cherryInfo) {
            c.render();
        }
        for (pellet p : pelletInfo) {
            p.drawPellet();
        }

    }


    /**
     * Takes the information from gameInfo array and makes new game pieces based on the First column
     */
//Sets up the game pieces before the game starts
    void setGamePieces() {
        Point point = new Point(Integer.parseInt(gameInfo[0][1]), Integer.parseInt(gameInfo[0][2]));
        pacman = new pacman(point, false);
        //Allocates all the game pieces into their own array
        for (int i = 1; i < gameInfo.length; i++) {
            initalPoint = new Point(Integer.parseInt(gameInfo[i][1]), Integer.parseInt(gameInfo[i][2]));
            if (gameInfo[i][0].toLowerCase().contains(GHOST_TEXT)) {
                if (gameInfo[i][0].toLowerCase().contains(RED_TEXT)) {
                    ghostInfo.add(new redGhost(initalPoint));
                } else if (gameInfo[i][0].toLowerCase().contains(BLUE_TEXT)) {
                    ghostInfo.add(new blueGhost(initalPoint));
                } else if (gameInfo[i][0].toLowerCase().contains(PINK_TEXT)) {
                    ghostInfo.add(new pinkGhost(initalPoint));

                } else if (gameInfo[i][0].toLowerCase().contains(GREEN_TEXT)) {
                    ghostInfo.add(new greenGhost(initalPoint));
                } else {
                    ghostInfo.add(new ghost(initalPoint));
                }
            } else if (gameInfo[i][0].equalsIgnoreCase(WALL_TEXT)) {
                wallInfo.add(new wall(initalPoint));
            } else if (gameInfo[i][0].equalsIgnoreCase(CHERRY_TEXT)) {
                cherryInfo.add(new cherry(initalPoint));
            } else if (gameInfo[i][0].equalsIgnoreCase(PELLET_TEXT)) {
                pelletInfo.add(new pellet(initalPoint));

            } else if (gameInfo[i][0].equalsIgnoreCase(DOT_TEXT)) {
                dotInfo.add(new dot(initalPoint));

            } else {

            }
        }
    }


    /**
     * Gets the middle of the screen with the message's width and length
     *
     * @param Message is the string that is used to print on the screen
     * @return Point
     */
    public Point getMiddleScreen(String Message) {

        return new Point((Window.getWidth() - TITLE_FONT.getWidth(Message)) / 2.0, Window.getHeight() / 2.0);
    }

    /**
     * Clears all information from every single object's arraylist
     */
    public void clear_info() {
        dotInfo.clear();
        wallInfo.clear();
        ghostInfo.clear();
        cherryInfo.clear();
        pelletInfo.clear();
    }

    /**
     * Checks the interaction between the player and other objects
     *
     * @param newPosition is the next position the player is going to be in.
     */
    public void checkInteraction(Point newPosition) {
        //Checks will Pacman be in the wall if it continues it's movement
        if (!pacman.wallCollided(wallInfo, newPosition.x, newPosition.y)) {
            pacman.setCoordinates(newPosition);
            //Checks whether the next movement collides into a ghost
            if (pacman.ghostCollided(ghostInfo)) {
                if (!pacman.isFrenzy()) {
                    pacman.killed();
                } else {
                    pacman.addScore(ghost.VALUE);
                }

            }
            //Checks whether the next movement collides into a dot
            if (pacman.dotCollided(dotInfo)) {
                pacman.addScore(dot.pointValue);
            }
            //Checks whether the next movement will collide into a cherry
            if (pacman.cherryCollided(cherryInfo)) {
                pacman.addScore(cherry.pointValue);

            }
            //Checks whether the next movement will collide into a pellet
            if (pacman.pelletCollided(pelletInfo)) {
                pacman.frenzyMode();
                for (ghost g : ghostInfo) {
                    g.FrenzyMode();
                }
            }
        }
    }
}



