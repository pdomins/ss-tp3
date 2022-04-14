package model;

@Deprecated
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
}