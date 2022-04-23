package engine;


import model.Element;
import model.FileGenerator;

import static engine.SystemGenerator.particles;
import static engine.SystemGenerator.walls;
import static Parser.CliParser.*;

import java.util.Map;

public class EventSimulator {
    public static void simulate() {
        long startTime, endTime, totalTime = 0;
        boolean isBalanced = false;

        // 1) Se definen las posiciones y velocidades iniciales, los radios y tamaño de la caja.
        SystemGenerator.generate();

        FileGenerator fileGenerator = new FileGenerator(walls);

        startTime = System.currentTimeMillis();
        Map.Entry<Double, Element[]> event;
        while (!isBalanced) {

            endTime = System.currentTimeMillis();

            // agrega las particulas al outputFile
            fileGenerator.addParticles(particles);
            fileGenerator.writeCSV(endTime - startTime, particles.size(), SimulationController.getParticlesLeft(), SimulationController.getParticlesRight());
            // 2) Se calcula el tiempo hasta el primer choque (evento!) (tc).

            event = SimulationController.getMinimumTc();

            // 3 y 4) Se evolucionan todas las partículas según sus ecuaciones de movimiento hasta tc y se guarda el estado.

            SimulationController.evolveParticles(event.getKey());

            // 5) Con el “operador de colisión” se determinan las nuevas velocidades después del choque,
            // solo para las partículas que chocaron.

            SimulationController.resolveNewSpeeds(event.getValue());

            // 6) Verifica si se llegó al equilibrio. Si llegó corta, sino vuelve al paso 2).
            isBalanced = SimulationController.verifiesEquilibrium();
            if (isBalanced) {
                totalTime = System.currentTimeMillis() - startTime;
                System.out.println("Simulation took " + totalTime + " ms with " + particles.size() + " particles and D = " + D + "\n");
//                long pressureStartTime = System.currentTimeMillis(), pressureCurrentTime = 0;
//                while (pressureCurrentTime < (totalTime - startTime) * 0.2) {
//                    //calculamos la presion
//                    fileGenerator.addPressure(pressureCurrentTime, SimulationController.getSystemPressure());
//                    event = SimulationController.getMinimumTc();
//                    SimulationController.evolveParticles(event.getKey());
//                    SimulationController.resolveNewSpeeds(event.getValue());
//                    pressureCurrentTime = System.currentTimeMillis() - pressureStartTime;
//                }
            }

        }
        fileGenerator.closeFiles();
    }
}
