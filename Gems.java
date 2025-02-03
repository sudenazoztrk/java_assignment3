/**
 * The Gems class represents a gemstone with its worth, weight, and coordinates.
 */
public class Gems {
    private final double worth; // The worth of the gem
    private final double weight; // The weight of the gem
    private double xCoordinate;  //The x-coordinate of the gem
    private double yCoordinate;  // The y-coordinate of the gem


    /**
     * Constructs a new Gems object with the specified worth and weight.
     *
     * @param worth  the worth of the gemstone
     * @param weight the weight of the gemstone
     */
    public Gems(double worth, double weight) {
        this.worth = worth;
        this.weight = weight;

    }


    /**
     * Gets the x-coordinate of the gemstone.
     *
     * @return the x-coordinate of the gemstone
     */
    public double getxCoordinate() {
        return xCoordinate;
    }

    /**
     * Sets the x-coordinate of the gemstone.
     *
     * @param xCoordinate the new x-coordinate of the gemstone
     */
    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * Gets the y-coordinate of the gemstone.
     *
     * @return the y-coordinate of the gemstone
     */
    public double getyCoordinate() {
        return yCoordinate;
    }

    /**
     * Sets the y-coordinate of the gemstone.
     *
     * @param yCoordinate the new y-coordinate of the gemstone
     */
    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    /**
     * Gets the worth of the gemstone.
     *
     * @return the worth of the gemstone
     */
    public double getWorth() {
        return worth;
    }

    /**
     * Gets the weight of the gemstone.
     *
     * @return the weight of the gemstone
     */
    public double getWeight() {
        return weight;
    }

}
