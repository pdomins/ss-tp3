import Parser.CliParser;
import engine.SimulationController;

public class App {
    public static void main(String[] args) {
        new CliParser(args);
        //SimulationController.simulate();
    }
}
