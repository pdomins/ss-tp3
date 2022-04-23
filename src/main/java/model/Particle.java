package model;

public class Particle extends Element {

    public double xVel;
    public double yVel;
    public String lastCollision;

    public Particle(double xPos, double yPos, double weight, double radius, double xVel, double yVel) {
        super(xPos, yPos, weight, radius);
        this.xVel = xVel;
        this.yVel = yVel;
    }

    public void calculateXPosition(double time) {
        this.xPos += (this.xVel * time);
    }

    public void calculateYPosition(double time) {
        this.yPos += (this.yVel * time);
    }

    public double getxVel() {
        return this.xVel;
    }

    public double getyVel() {
        return this.yVel;
    }

    public void setLastCollision(String lastCollision) {
        this.lastCollision = lastCollision;
    }

    public String lastCollision() {
        return this.lastCollision;
    }
}
