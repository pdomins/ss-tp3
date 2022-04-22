package engine;

import model.Element;
import model.Particle;
import model.Wall;

import java.util.Map;
import java.util.TreeMap;

import static Parser.CliParser.D;
import static Parser.CliParser.PERCENTAGE;
import static engine.SystemGenerator.particles;
import static engine.SystemGenerator.width;

public class SimulationController {

  public static Map.Entry<Double, Element[]> getMinimumTc(){
    TreeMap<Double, Element[]> events = new TreeMap<>();

    for (Particle p : SystemGenerator.particles) {
      Wall w;
      if (p.xVel > 0) {
        if (p.xPos > SystemGenerator.width/2) {
          w = SystemGenerator.getWall(3);
        } else {
          if (p.yPos > (((SystemGenerator.height - D) / 2) + D)){
            w = SystemGenerator.getWall(5);
          } else {
            w = SystemGenerator.getWall(4);
          }
        }
        events.put(calculateImpactTime(w.xPos, -p.radius, p.xPos, p.xVel),new Element[]{p,w});
      } else {
        if (p.xPos > SystemGenerator.width / 2) {
          if (p.yPos > (((SystemGenerator.height - D) / 2) + D)) {
            w = SystemGenerator.getWall(5);
          } else {
            w = SystemGenerator.getWall(4);
          }
        } else {
          w = SystemGenerator.getWall(2);
        }
        events.put(calculateImpactTime(w.xPos, p.radius, p.xPos, p.xVel),new Element[]{p,w});
      }

      if (p.yVel > 0) {
        w = SystemGenerator.getWall(0);
        events.put(calculateImpactTime(w.yPos, -p.radius, p.yPos, p.yVel),new Element[]{p,w});
      } else {
        w = SystemGenerator.getWall(1);
        events.put(calculateImpactTime(w.yPos, p.radius, p.yPos, p.yVel),new Element[]{p,w});
      }
    }

    for (Particle pi : SystemGenerator.particles) {
      for (Particle pj : SystemGenerator.particles) {
        double[] deltaV = new double[]{pj.xVel - pi.xVel, pj.yVel - pi.yVel};
        double[] deltaR = new double[]{pj.xPos - pi.xPos, pj.yPos - pi.yPos};
        double sigma = pj.radius + pi.radius;
        double VRProduct = scalarVectorMultiply(deltaV, deltaR);
        double VVProduct = scalarVectorMultiply(deltaV, deltaV);
        double d = Math.pow(VRProduct,2) - (VVProduct * (scalarVectorMultiply(deltaR, deltaR) - Math.pow(sigma, 2)));

        if(!isInfinite(VRProduct,d)){
          events.put(calculateImpactTime(VRProduct, d, VVProduct), new Element[]{pi,pj});
        }
      }
    }
    return events.ceilingEntry(events.firstKey()); //CHECK
  }

  public static void evolveParticles(Double tc){
    for (Particle p : particles) {
      p.calculateXPosition(tc);
      p.calculateYPosition(tc);
    }
  }

  private static double calculateImpactTime(double wallPos, double radius, double particlePos, double particleVel){
    return (wallPos + radius + particlePos)/particleVel;
  }

  private static double calculateImpactTime(double VR, double d, double VV){
    return -(VR + Math.sqrt(d))/VV;
  }

  private static double scalarVectorMultiply(double[] v1, double[] v2) {
    return (v1[0] * v2[0]) + (v1[1] * v2[1]);
  }

  private static boolean isInfinite(double VRProduct, double d){
    return VRProduct >= 0 || d < 0;
  }

  public static boolean verifiesEquilibrium(){
    int particlesLeft = getPariclesLeft();
    int particlesRight = getParticlesRight();

    if(Math.abs(particlesLeft - particlesRight) < (particles.size() * PERCENTAGE)){
      return true;
    }
    else{
      return false;
    }
  }

  public static int getPariclesLeft(){
    int particlesLeft = 0;
    for(Particle particle : particles) {
      if (particle.getXPos() < width / 2) {
        particlesLeft++;
      }
    }
    return particlesLeft;
  }

  public static int getParticlesRight(){
    int particlesRight = 0;
    for(Particle particle : particles) {
      if (particle.getXPos() > width / 2) {
        particlesRight++;
      }
    }
    return particlesRight;
  }

  public static void resolveNewSpeeds(Element[] particle){
    if (particle[0] instanceof Wall || particle[1] instanceof Wall){
      if(particle[0] instanceof Wall){
        if (particle[0].isHorizontal()){
          particle[1].yVel * -1;
        } else {
          particle[1].xVel * -1;
        }
      } else {
        if (particle[1].isHorizontal()){
          particle[0].yVel * -1;
        } else {
          particle[0].xVel * -1;
        }
      }
    } else {
      double[] deltaV = new double[]{particle[1].xVel - particle[0].xVel, particle[1].yVel - particle[0].yVel};
      double[] deltaR = new double[]{particle[1].xPos - particle[0].xPos, particle[1].yPos - particle[0].yPos};
      double sigma = particle[1].radius + particle[0].radius;
      double J = (2 * Math.pow(particle[0].weight,2) * scalarVectorMultiply(deltaV, deltaR))/(sigma*2*particle[0].weight);
      double Jx = (J * (particle[1].xPos-particle[0].xPos))/sigma;
      double Jy = (J * (particle[1].yPos-particle[0].yPos))/sigma;

      particle[0].xVel += Jx/particle[0].weight;
      particle[0].yVel += Jy/particle[0].weight;

      particle[1].xVel -= Jx/particle[1].weight;
      particle[1].yVel -= Jy/particle[1].weight
    }
  }
}
