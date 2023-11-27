import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * This class represents a stationary object
 * @author Ken Liem
 * @version 1.0
 */
public class stationary {
    private final Point coordinates;
    private final Image image;
    /**
     * Constructor for the stationary object
     */
    public stationary(Point coordinates, Image image) {
        this.coordinates = coordinates;
        this.image = image;
    }

    /**
     * Gets the current coordinates for the stationary object
     * @return the current coordinates of the object (point)
     */
    public Point getCoordinates() {
        return coordinates;
    }

    /**
     * Renders the image for the stationary object
     */
    public void render(){
        image.drawFromTopLeft(coordinates.x, coordinates.y);
    }

    /**
     * Gets a rectangle of the object at the current position
     * @return Rectangle of the image at the point (rectangle)
     */
    public Rectangle getRectangle(){
        return new Rectangle(coordinates, image.getWidth(), image.getHeight());
    }


}
