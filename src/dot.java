import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Represents an instance of dot and it's classes
 * @author Ken Liem
 * @version 1.0
 */
public class dot extends stationary {

    private boolean isActive = true;
    /**
     * Value for the dot if it is eaten
     */
    public final static int pointValue = 10;

    private final static Image DOT = new Image("res/dot.png");

    /**
     * Constructor for dot
     * @param coordinates, the location of the object on the screen
     */
    public dot(Point coordinates) {
       super(coordinates, DOT);

    }

    /**
     *gets if the object is on the screen or not
     * @return whether it is active or not(true or false)
     */
    public boolean isActive() {
        return isActive;
    }
    /**
     *sets  the object to be active or not
     *
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     *gets if the object is on the screen or not
     * @return whether it is active or not(true or false)
     */
    public int getPointValue() {
        return pointValue;
    }

    /**
     *draws the image of a dot based on if it active or not
     *
     */
    public void render() {
        if (isActive) {
            super.render();
        }

    }
}


