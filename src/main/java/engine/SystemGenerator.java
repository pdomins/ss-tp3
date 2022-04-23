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
//        walls.add(new Wall(false, width / 2, ((height + D) / 2), height));
        walls.add(new Wall(false, width / 2, ((height - D) / 2) + D, (height - D) / 2));
    }

    private static void generateParticles() {
        for (int i = 0; i < N; i++) {
            particles.add(generateParticle(i));
        }
    }

    private static Particle generateParticle(int id) {
        double toSetX = 0, toSetY = 0, velX, velY;
        boolean particleIsSet = false;
        while (!particleIsSet) {
            toSetX = getRandom(0, (width / 2));
            toSetY = getRandom(0, height);
            if (!checkOverlap(toSetX, toSetY)) {
                particleIsSet = true; //if they dont overlap, then set the particle
            }
        }
        double angle = getRandom(0, 360);
        double angleInRadians = angle * Math.PI / 180.0;
        velX = Math.cos(angleInRadians) * V;
        velY = Math.sin(angleInRadians) * V;
        return new Particle(id, toSetX, toSetY, W, R, velX, velY);
    }

    private static boolean checkOverlap(double x, double y) {
        if (x < R || y < R)
            return true;
        if ((x + R > width / 2) || (y + R > height))
            return true;

        for (Particle particle : particles) {
            if (((Math.hypot(particle.xPos - x, particle.yPos - y)) - particle.radius - R) < 0)
                return true;
        }
        return false;
    }

    private static double getRandom(double min, double max) {
        return (Math.random() * (max - min + 1) + min);
    }

    public static Wall getWall(int idx) {
        return walls.get(idx);
    }
}
