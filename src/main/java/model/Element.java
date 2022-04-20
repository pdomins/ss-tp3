package model;

public abstract class Element {
  public double weight;
  public double xPos;
  public double yPos;
  public double radius;

  public Element(double xPos, double yPos, double weight, double radius) {
    this.weight = weight;
    this.xPos = xPos;
    this.yPos = yPos;
    this.radius = radius;
  }

  public Element(){}

  public double getXPos(){
    return this.xPos;
  }

  public double getYPos(){
    return this.yPos;
  }

  public double getRadius(){
    return this.radius;
  }

  public double getWeight(){
    return this.weight;
  }
}