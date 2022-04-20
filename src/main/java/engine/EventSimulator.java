package engine;


import model.Element;
import model.Particle;

import java.util.Map;

public class EventSimulator {
    public static void simulate(){
        boolean isBalanced = false;
        while (!isBalanced) {
            // 1) Se definen las posiciones y velocidades iniciales, los radios y tamaño de la caja.

            SystemGenerator.generate();

            // 2) Se calcula el tiempo hasta el primer choque (evento!) (tc).

            Map.Entry<Double, Element[]> event = SimulationController.getMinimumTc();

            // 3) Se evolucionan todas las partículas según sus ecuaciones de movimiento hasta tc.

            SimulationController.evolveParticles(event.getKey());

            // 4) Se guarda el estado del sistema (posiciones y velocidades) en t = tc.

            SimulationController.saveState();

            // 5) Con el “operador de colisión” se determinan las nuevas velocidades después del choque,
            // solo para las partículas que chocaron.

            isBalanced = SimulationController.verifiesEquilibrium();
            isBalanced = true;  // borrar
            // 6) Verifica si se llegó al equilibrio. Si llegó corta, sino vuelve al paso 2).
        }
    }
}
