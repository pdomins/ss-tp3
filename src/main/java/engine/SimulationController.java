package engine;

import model.Element;
import static Parser.CliParser.N;

public class SimulationController {
  public static double height = 0.09;
  public static double width = 0.24;
  public static double radius = 0.0015;

  public static Element[][] cells;

  public static void simulate(){
    ParticleGenerator.generate(height, width, radius, N, cells);


  }
}
