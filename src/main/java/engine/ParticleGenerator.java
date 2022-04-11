package engine;

import model.Element;
import model.Particle;
import model.Wall;

import java.util.Random;

public class ParticleGenerator {

  public static void generate(double height, double width, double radius, int N, Element[][] grid){
    int maxCells = (int) Math.floor((height * width)/Math.pow((radius * 2.0),2)); // esto creo que seria un first step
                                                                                  // para evitar superposicion de partic
    if (N > maxCells) {
      throw new Error(String.format("Can't place more than %d particles", maxCells));
    }

    int rows = (int) Math.floor(height / (radius*2)) + 3;
    int cols = (int) Math.floor(width / (radius*2)) + 3; // "celdas" de 0.03 x 0.03 con 3 posibles walls

    for (int i = 0; i < rows; i++) {
      grid[i][0] = new Wall(0,i);
      if(i != (int)(rows/2.0) && i != (int)(rows/2.0) + 1){
        grid[i][cols/2] = new Wall((int)(cols/2.0),i);
      }
      grid[i][cols] = new Wall(cols,i);
    }

    for (int j = 0; j < cols; j++){
      grid[0][j] = new Wall(j,0);
      grid[rows][j] = new Wall(j, rows);
    }

    int placedParticles = 0;

    while (placedParticles < N) {
      double x = Math.random() * cols/2.0;
      double y = Math.random() * rows;
      double xVel = ((Math.random() * 2) - 1) * 0.01; // para que quede en el rango [-0.001, 0.001]
      double yVel = Math.sqrt(0.0001 - Math.pow(xVel,2));
      if (grid[(int)x][(int)y] == null) {
        grid[(int) x][(int) y] = new Particle(x, y, 1, radius, xVel, yVel);
        placedParticles++;
      }
    }
  }
}
