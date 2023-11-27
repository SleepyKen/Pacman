import bagel.Image;
import bagel.util.Point;

/**
 * Represents an instance of cherry and it's methods
 * @author Ken Liem
 * @version 1.0
 */
public class cherry extends stationary{


    private boolean isActive = true;

    private final static Image CHERRY = new Image("res/cherry.png");
    /**
     * Value for the cherry if it is eaten
     */
    public final static int pointValue = 20;


    public cherry(Point coordinates) {
        super(coordinates, CHERRY);
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
     * Draws the image  based on whether it is active or not
     *
     */

    public void render() {
        if (isActive) {
            super.render();
        }

    }
}





