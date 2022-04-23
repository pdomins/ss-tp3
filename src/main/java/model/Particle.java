package model;

public class Particle extends Element {

    public double xVel;
    public double yVel;
    public int id;
    public String lastCollision;
    public boolean isBorder;

    public Particle(int id, double xPos, double yPos, double weight, double radius, double xVel, double yVel) {
        super(xPos, yPos, weight, radius);
        this.id = id;
        this.xVel = xVel;
        this.yVel = yVel;
        this.isBorder = false;
    }

    public Particle(int id, double xPos, double yPos, boolean isBorder) {
        super(xPos, yPos, 1, 0.0001);
        this.id = id;
        this.xVel = 0;
        this.yVel = 0;
        this.isBorder = isBorder;
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

    public boolean isBorder() {
        return isBorder;
    }

    public void setLastCollision(String lastCollision) {
        this.lastCollision = lastCollision;
    }

    public String lastCollision() {
        return this.lastCollision;
    }
}
