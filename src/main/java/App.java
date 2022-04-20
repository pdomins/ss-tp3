import Parser.CliParser;
import engine.EventSimulator;

public class App {
    public static void main(String[] args) {
        new CliParser(args);
        EventSimulator.simulate();
    }
}
