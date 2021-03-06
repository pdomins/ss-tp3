package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static engine.SystemGenerator.height;
import static engine.SystemGenerator.width;
import static Parser.CliParser.*;

public class FileGenerator {
    private FileWriter fw1;
    private FileWriter fw2;
    private FileWriter fw3;
    private final BufferedWriter buffer1;
    private final BufferedWriter buffer2;
    private final BufferedWriter buffer3;

    public FileGenerator(List<Wall> walls) {
        try {
            FileWriter pw1 = new FileWriter("OutputPosition.xyz");
            FileWriter pw2 = new FileWriter("FpTime.csv");
            FileWriter pw3 = new FileWriter("OutputPressure.csv");
            pw1.close();
            pw2.close();
            pw3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.fw1 = new FileWriter("OutputPosition.xyz", true);
            this.fw2 = new FileWriter("FpTime.csv", true);
            this.fw3 = new FileWriter("OutputPressure.csv", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.buffer1 = new BufferedWriter(fw1);
        this.buffer2 = new BufferedWriter(fw2);
        this.buffer3 = new BufferedWriter(fw3);

        try {
            buffer2.write("t, pL, pR\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            buffer3.write("t, P\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeWalls(walls);
    }

    public void addPressure(long time, double pressure) {
        try {
            buffer3.write(time + "," + pressure + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addParticles(List<Particle> particles) {
        try {
            buffer1.write(particles.size() + "\n");
            buffer1.write("X Y XVelo YVelo Radius Weight Color\n");
            for (Particle particle : particles) {
                if(particle.getXPos() > width /2){
                    buffer1.write(particle.getXPos() + " " + particle.getYPos() + " " + particle.getxVel() + " "
                            + particle.getyVel() + " " + particle.getRadius() + " " + particle.getWeight() + " " + 0.5 + "\n");
                }
                else {
                    buffer1.write(particle.getXPos() + " " + particle.getYPos() + " " + particle.getxVel() + " "
                            + particle.getyVel() + " " + particle.getRadius() + " " + particle.getWeight() + " " + 1 + "\n");
                }
            }
            //buffer1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void writeWalls(List<Wall> walls) {
        double x, y;
        try {
            FileWriter fw = new FileWriter("Walls.xyz");
            fw.close();
            fw = new FileWriter("Walls.xyz", true);
            BufferedWriter buffer = new BufferedWriter(fw);
            buffer.write( "6903" + "\n");
            buffer.write("X Y Radius\n");
            for (Wall wall : walls) {
                x = wall.getXPos();
                y = wall.getYPos();
                if (wall.isHorizontal()) {
                    while (x < width) {
                        buffer.write(x + " " + y + " " + wall.getRadius() + "\n");
                        x += 0.0001;
                    }
                } else {
                    if ((x == width / 2) && (y == 0.0)) {
                        while (y < ((height - D) / 2)) {
                            buffer.write(x + " " + y + " " + wall.getRadius() + "\n");
                            y += 0.0001;
                        }
                    } else {
                        while (y < height) {
                            buffer.write(x + " " + y + " " + wall.getRadius() + "\n");
                            y += 0.0001;
                        }
                    }
                }

            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCSV(long time, int particlesQuantity, int leftParticles, int rightParticles) {
        try {
            buffer2.write(time + "," + ((double) leftParticles / (double) particlesQuantity) + "," + (((double) rightParticles / (double) particlesQuantity) + "\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFiles() {
        try {
            buffer1.close();
            buffer2.close();
            buffer3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
