package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileGenerator {
    private FileWriter fw1;
    //private FileWriter fw2;
    private final BufferedWriter buffer1;
    //private final BufferedWriter buffer2;

    public FileGenerator(List <Wall> walls){
        try{
            FileWriter pw1 = new FileWriter("OutputPosition.xyz");
            //FileWriter pw2 = new FileWriter("OutputTime.csv");
            pw1.close();
            //pw2.close();
            this.fw1 = new FileWriter("OutputPosition.xyz", true);
            //this.fw2 = new FileWriter("OutputTime.csv", true);
        }catch (IOException e){
            e.printStackTrace();
        }

        this.buffer1 = new BufferedWriter(fw1);
        //this.buffer2 = new BufferedWriter(fw2);

        /*try{
            buffer2.write("t, pL, pR\n");
        }catch (IOException e){
            e.printStackTrace();
        }*/
        writeWalls(walls);
    }



    public void addParticles(List<Particle> particles){
        try{
            buffer1.write(particles.size() + "\n");
            buffer1.write("X Y XVelo YVelo Radius Weight\n");
            for(Particle particle : particles){
                buffer1.write(particle.getXPos() + " "  + particle.getYPos() + " "  + particle.getxVel() + " "
                        + particle.getyVel() + " "  + particle.getRadius() + " "  + particle.getWeight()  + "\n");
            }
            //buffer1.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }



    private void writeWalls(List<Wall> walls){
        try{
            FileWriter fw = new FileWriter("Walls.xyz");
            fw.close();
            fw = new FileWriter("Walls.xyz", true);
            BufferedWriter buffer = new BufferedWriter(fw);

            buffer.write("X Y Radius\n");
                    //escribe paredes
            buffer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeCSV(long time, int particlesQuantity, int leftParticles, int rightParticles){
        /*try {
            buffer2.write(time + "," + ((double) leftParticles / (double) particlesQuantity) + "," + (((double) rightParticles / (double) particlesQuantity) + "\n"));
        }catch (IOException e){
            e.printStackTrace();
        }*/
    }

    public void closeFiles(){
        try {
            buffer1.close();
            //buffer2.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
