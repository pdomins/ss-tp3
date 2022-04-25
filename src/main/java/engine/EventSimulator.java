package engine;


import model.Element;
import model.FileGenerator;
import model.Particle;

import static Parser.CliParser.*;
import static engine.SystemGenerator.*;
import static engine.SystemGenerator.particles;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EventSimulator {
    public static void simulate() {
        double longitud = 2 * width + 2 * height + 2 * (height-D);

        double totalImpulse, pressure, initialImpulse, finalImpulse = 0;
        long startTime, endTime, totalTime;
        boolean isBalanced = false;
        double tcSum = 0;
        double delta = T;

        // 1) Se definen las posiciones y velocidades iniciales, los radios y tamaño de la caja.
        SystemGenerator.generate();

        FileGenerator fileGenerator = new FileGenerator(walls);

        startTime = System.currentTimeMillis();
        Map.Entry<Double, Element[]> event;
        while (!isBalanced) {

            endTime = System.currentTimeMillis();

            if (tcSum > delta) {
                // agrega las particulas al outputFile
                fileGenerator.addParticles(particles);
                fileGenerator.writeCSV(endTime - startTime, particles.size(), SimulationController.getParticlesLeft(), SimulationController.getParticlesRight());
                tcSum = 0;
            }

            // 2) Se calcula el tiempo hasta el primer choque (evento!) (tc).

            event = SimulationController.getMinimumTc();

            tcSum += event.getKey();

            // 3 y 4) Se evolucionan todas las partículas según sus ecuaciones de movimiento hasta tc y se guarda el estado.

            SimulationController.evolveParticles(event.getKey());

            // 5) Con el “operador de colisión” se determinan las nuevas velocidades después del choque,
            // solo para las partículas que chocaron.

            SimulationController.resolveNewSpeeds(event.getValue());

            // 6) Verifica si se llegó al equilibrio. Si llegó corta, sino vuelve al paso 2).
            isBalanced = SimulationController.verifiesEquilibrium();

        }

        totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Simulation took " + totalTime + " ms with " + particles.size() + " particles and D = " + D);

        //Calcula la presion del sistema
        long pressureStartTime = System.currentTimeMillis();
        long pressureCurrentTime = 0;
        double timeAfterEquilibrium = 10000;          // 10 segundos

        while (pressureCurrentTime < 10000) {
          //calculamos la presion
//          fileGenerator.addPressure(pressureCurrentTime, SimulationController.getSystemPressure());
            event = SimulationController.getMinimumTc();
            SimulationController.evolveParticles(event.getKey());
            SimulationController.resolveNewSpeeds(event.getValue());
            initialImpulse = calculatesImpulse(particles);
            finalImpulse += initialImpulse;
            pressureCurrentTime = System.currentTimeMillis() - pressureStartTime;
        }
        totalImpulse = finalImpulse;

        pressure = (totalImpulse / (longitud * (10000)));
        System.out.println("Pressure: " + pressure);
        fileGenerator.closeFiles();
    }

    public static double calculatesImpulse(List<Particle> particles){
        double impulse = 0;
        for(Particle particle :particles){
            if(Objects.equals(particle.getLastCollision(), "h")){
                impulse += 2 * Math.abs(particle.getyVel()) * particle.getWeight();
            }
            else if(Objects.equals(particle.getLastCollision(), "v")){
                impulse += 2 * Math.abs(particle.getxVel()) * particle.getWeight();
            }
        }
        return  impulse;
    }
}
