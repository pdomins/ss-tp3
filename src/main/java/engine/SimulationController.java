package engine;

import model.Element;
import model.Particle;
import model.Wall;

import java.util.Map;
import java.util.TreeMap;

import static Parser.CliParser.D;

public class SimulationController {

  public static Map.Entry<Double, Element[]> getMinimumTc(){
    TreeMap<Double, Element[]> events = new TreeMap<>();

    for (Particle p : SystemGenerator.particles) {
      Wall w = new Wall();
      if (p.xVel > 0) {
        if (p.xPos > SystemGenerator.width/2) {
          w = SystemGenerator.getWall(3);
          // wallPos = w.yPos + p.yPos;
        } else {
          if (p.yPos > (((SystemGenerator.height - D) / 2) + D)){
            w = SystemGenerator.getWall(5);
          } else {
            w = SystemGenerator.getWall(4);
          }
        }
        // events.put(calculateImpactTime(p.xVel,p.radius,p.xPos,w.yPos),new Element[]{p,w});
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
        events.put(calculateImpactTime(p,w),new Element[]{p,w});
      }

      if (p.yVel > 0) {
        w = SystemGenerator.getWall(0);
        events.put(calculateImpactTime(p,w),new Element[]{p,w});
      } else {
        w = SystemGenerator.getWall(1);
        events.put(calculateImpactTime(p,w),new Element[]{p,w});
      }
    }

    for (Particle p1 : SystemGenerator.particles) {
      for (Particle p2 : SystemGenerator.particles) {

      }
    }
    return events.ceilingEntry(events.firstKey()); //CHECK
  }

  public static void evolveParticles(){

  }

  private static double calculateImpactTime(Particle p, Wall w){
    return 0;
  }

  public static void saveState() {
  }
}
