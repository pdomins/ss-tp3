package model;

public class Particle extends Element {

  public double xVel;
  public double yVel;

  public Particle(double xPos, double yPos, double weight, double radius, double xVel, double yVel) {
    super(xPos, yPos, weight, radius);
    this.xVel = xVel;
    this.yVel = yVel;
  }

  public void calculateXPosition(double time) {
    this.xVel += (this.xVel * time);
  }

  public void calculateYPosition(double time) {
    this.yVel += (this.yVel * time);
  }

  public double getxVel(){
    return this.xVel;
  }

  public double getyVel() {
    return this.yVel;
  }
}
