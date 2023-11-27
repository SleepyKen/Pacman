import bagel.Image;
import bagel.util.Point;

/**
 * This class represents the pellet object and it's methods
 */
public class pellet extends stationary {


    private boolean isActive = true;

    private final static Image PELLET = new Image("res/pellet.png");

    /**
     * Constructor for pellet
     * @param coordinates on the position of the pellet
     */
    public pellet(Point coordinates) {
        super(coordinates, PELLET);
    }

    /**
     * checks if the object is active or not
     * @return whether the object is active or not
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * sets the object to be either active or not active
     *
     */
    public void setActive(boolean active) {
        isActive = active;
    }


    /**
     * Draws the image based on whether it is active or not
     *
     */

    public void drawPellet() {
        if (isActive) {
           super.render();
        }

    }
}





