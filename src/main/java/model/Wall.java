package model;

import java.util.Objects;

public class Wall extends Element {
    private boolean isHorizontal;
    private double xPosition;
    private double yPosition;
    private double length;

    public Wall() {
        super();
    }

    public Wall(boolean isHorizontal, double xPosition, double yPosition, double length) {
        super(xPosition, yPosition, 0, 0); //esto vuela cuando se vaya Element
        this.isHorizontal = isHorizontal;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.length = length;
    }

    public boolean isHorizontal(){
        return isHorizontal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wall wall = (Wall) o;
        return isHorizontal == wall.isHorizontal && Double.compare(wall.xPosition, xPosition) == 0 && Double.compare(wall.yPosition, yPosition) == 0 && Double.compare(wall.length, length) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isHorizontal, xPosition, yPosition, length);
    }

    public Wall(double xPos, double yPos) {
        super(xPos, yPos, 0, 0);
    }
}
