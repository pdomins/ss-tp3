package Parser;

import org.apache.commons.cli.*;

public class CliParser {

    public static int N;
    public static double D;
    public static double V;
    public static double R;
    public static double W;
    public static double PERCENTAGE;


    private Options getCommandlineOptions() {
        Options options = new Options();
        options.addOption("n", true, "total amount of particles");
        options.addOption("N", true, "total amount of particles");

        options.addOption("d", true, "opening size");
        options.addOption("D", true, "opening size");

        options.addOption("v", true, "velocity");
        options.getOption("v").setOptionalArg(true);
        options.addOption("V", true, "velocity");
        options.getOption("V").setOptionalArg(true);

        options.addOption("d", true, "default settings");
        options.getOption("d").setOptionalArg(true);
        options.addOption("D", true, "default settings");
        options.getOption("D").setOptionalArg(true);

        options.addOption("h", false, "help");
        options.addOption("help", false, "help");

        return options;
    }

    private static void help() {
        System.out.println("Please remember the valid options are: ");
        System.out.println("-n or -N <value> to specify the number of particles,");
        System.out.println("-d or -D <value> to specify the size of the gap,");
        System.out.println("-v or -V <value> to specify the velocity of the particles (Optional),");
        System.out.println("-d or -D <value> to set default settings (Optional),");
        System.out.println("-h or -help to see the menu.");
    }

    public CliParser(String[] args) {
        Options options = getCommandlineOptions();
        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);

            if (commandLine.hasOption("h") || commandLine.hasOption("help")) {
                help();
                System.exit(1);
            }
            if (commandLine.hasOption("n")) {
                N = Integer.parseInt(commandLine.getOptionValue("n"));
                if(N<= 0 || N>= 10000){
                    System.out.println("The specified number for n is too big or too small, by default n will now be 2000. ");
                    N=200;
                }
            } else if (commandLine.hasOption("N")) {
                N = Integer.parseInt(commandLine.getOptionValue("N"));
                if(N<= 0 || N>= 10000){
                    System.out.println("The specified number for N is too big or too small, by default N will now be 2000. ");
                    N=200;
                }
            } else {
                N = 200;
            }
            if (commandLine.hasOption("d")) {
                D = Integer.parseInt(commandLine.getOptionValue("d"));
            } else if (commandLine.hasOption("D")) {
                D = Integer.parseInt(commandLine.getOptionValue("D"));
            } else {
                D = 0.02;
            }
            if (commandLine.hasOption("d") ||commandLine.hasOption("D")) {
                V = 0.01;
                R = 0.0015;
                W = 1;
            }
            V = 0.01;
            R = 0.0015;
            W = 1;
            PERCENTAGE = 0.5;
        } catch (ParseException e) {
            System.out.println(e.getMessage() + ".");
            help();
            System.exit(1);
        }
    }

}