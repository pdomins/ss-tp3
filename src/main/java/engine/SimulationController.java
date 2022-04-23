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

    public static Map.Entry<Double, Element[]> getMinimumTc() {
        TreeMap<Double, Element[]> events = new TreeMap<>();

        for (Particle p : SystemGenerator.particles) {
            Wall w;
            if (p.xVel > 0) {
                if (p.xPos > SystemGenerator.width / 2) {
                    w = SystemGenerator.getWall(3);
                } else {
                    if (p.yPos > (((SystemGenerator.height - D) / 2) + D)) {
                        w = SystemGenerator.getWall(5);
                    } else if (p.yPos < (((SystemGenerator.height - D) / 2))) {
                        w = SystemGenerator.getWall(4);
                    } else {
                        w = SystemGenerator.getWall(3);
                    }
                }
                events.put(calculateImpactTime(w.xPos, -p.radius, p.xPos, p.xVel), new Element[]{p, w});
            } else {
                if (p.xPos > SystemGenerator.width / 2) {
                    if (p.yPos > (((SystemGenerator.height - D) / 2) + D)) {
                        w = SystemGenerator.getWall(5);
                    } else if (p.yPos < (((SystemGenerator.height - D) / 2))) {
                        w = SystemGenerator.getWall(4);
                    } else {
                        w = SystemGenerator.getWall(2);
                    }
                } else {
                    w = SystemGenerator.getWall(2);
                }
                events.put(calculateImpactTime(w.xPos, p.radius, p.xPos, p.xVel), new Element[]{p, w});
            }

            if (p.yVel > 0) {
                w = SystemGenerator.getWall(1);
                events.put(calculateImpactTime(w.yPos, -p.radius, p.yPos, p.yVel), new Element[]{p, w});
            } else {
                w = SystemGenerator.getWall(0);
                events.put(calculateImpactTime(w.yPos, p.radius, p.yPos, p.yVel), new Element[]{p, w});
            }
        }

        for (Particle pi : SystemGenerator.particles) {
            for (Particle pj : SystemGenerator.particles) {
                double[] deltaV = new double[]{pj.xVel - pi.xVel, pj.yVel - pi.yVel};
                double[] deltaR = new double[]{pj.xPos - pi.xPos, pj.yPos - pi.yPos};
                double sigma = pj.radius + pi.radius;
                double VRProduct = scalarVectorMultiply(deltaV, deltaR);
                double VVProduct = scalarVectorMultiply(deltaV, deltaV);
                double d = Math.pow(VRProduct, 2) - (VVProduct * (scalarVectorMultiply(deltaR, deltaR) - Math.pow(sigma, 2)));

                if (!isInfinite(VRProduct, d)) {
                    events.put(calculateImpactTime(VRProduct, d, VVProduct), new Element[]{pi, pj});
                }
            }
        }

        return events.ceilingEntry(events.firstKey()); //CHECK
    }

    public static void evolveParticles(Double tc) {
        for (Particle p : particles) {
            if (!p.isBorder()) {
                p.calculateXPosition(tc);
                p.calculateYPosition(tc);
            }
        }
    }

    private static double calculateImpactTime(double wallPos, double radius, double particlePos, double particleVel) {
        if ((wallPos + radius - particlePos) / particleVel < 0) return 0;
        return (wallPos + radius - particlePos) / particleVel;
    }

    private static double calculateImpactTime(double VR, double d, double VV) {
        return -(VR + Math.sqrt(d)) / VV;
    }

    private static double scalarVectorMultiply(double[] v1, double[] v2) {
        return (v1[0] * v2[0]) + (v1[1] * v2[1]);
    }

    private static boolean isInfinite(double VRProduct, double d) {
        return VRProduct >= 0 || d < 0;
    }

    public static boolean verifiesEquilibrium() {
        int particlesLeft = getParticlesLeft();
        int particlesRight = getParticlesRight();

        System.out.println(particlesLeft + " " + particlesRight);

//        return Math.abs(particlesLeft - particlesRight) < (particles.size() * PERCENTAGE);
//        return (particlesRight == particles.size() * PERCENTAGE * 0.8);
        return particlesLeft == particlesRight;
    }

    public static int getParticlesLeft() {
        int particlesLeft = 0;
        for (Particle particle : particles) {
            if (particle.getXPos() < width / 2) {
                particlesLeft++;
            }
        }
        return particlesLeft;
    }

    public static int getParticlesRight() {
        int particlesRight = 0;
        for (Particle particle : particles) {
            if (particle.getXPos() > width / 2) {
                particlesRight++;
            }
        }
        return particlesRight;
    }

    public static void resolveNewSpeeds(Element[] particle) {
        Wall wall;
        Particle pi, pj; // pi = particle[0], pj = particle[1]
        if (particle[0] instanceof Wall || particle[1] instanceof Wall) {
            if (particle[0] instanceof Wall) {
                wall = (Wall) particle[0];
                pi = (Particle) particle[1];
                if (wall.isHorizontal()) {
                    pi.yVel *= -1;
                } else {
                    pi.xVel *= -1;
                }
            } else {
                wall = (Wall) particle[1];
                pi = (Particle) particle[0];
                if (wall.isHorizontal()) {
                    pi.yVel *= -1;
                } else {
                    pi.xVel *= -1;
                }
            }
        } else {
            pi = (Particle) particle[0];
            pj = (Particle) particle[1];
            double[] deltaV = new double[]{pj.xVel - pi.xVel, pj.yVel - pi.yVel};
            double[] deltaR = new double[]{pj.xPos - pi.xPos, pj.yPos - pi.yPos};
            double sigma = pj.radius + particle[0].radius;
            double J = (2 * Math.pow(pi.weight, 2) * scalarVectorMultiply(deltaV, deltaR)) / (sigma * 2 * particle[0].weight);
            double Jx = (J * (pj.xPos - pi.xPos)) / sigma;
            double Jy = (J * (pj.yPos - pi.yPos)) / sigma;

            if (!pi.isBorder()) {
                pi.xVel += Jx / particle[0].weight;
                pi.yVel += Jy / particle[0].weight;
            }
            if (!pj.isBorder()) {
                pj.xVel -= Jx / particle[1].weight;
                pj.yVel -= Jy / particle[1].weight;
            }
        }
    }

    public static double getSystemPressure() {
        double pressure = 0;

        return pressure;
    }

}
