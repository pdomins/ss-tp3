package engine;

import model.Particle;
import model.Wall;

import java.util.ArrayList;
import java.util.List;

import static Parser.CliParser.*;

public class SystemGenerator {
    public static List<Particle> particles = new ArrayList<>();
    public static List<Wall> walls = new ArrayList<>();
    public static double height = 0.09;
    public static double width = 0.24;
    public static double radius = 0.0015;

    public static void generate() {
        generateWalls(); //set box size
        generateParticles(); //generate and set particles positions
    }

    private static void generateWalls() {
        walls.add(new Wall(true, 0, 0, width));
        walls.add(new Wall(true, 0, height, width));
        walls.add(new Wall(false, 0, 0, height));
        walls.add(new Wall(false, width, 0, height));
        walls.add(new Wall(false, width / 2, 0, (height - D) / 2));
        walls.add(new Wall(false, width / 2, ((height - D) / 2) + D, (height - D) / 2));
    }

    private static void generateParticles() {
        for (int i = 0; i < N ; i++){
            particles.add(generateParticle(i));
        }
    }
    private static Particle generateParticle(int id){

    }
}
