package com.yeahmobi.pangolin;

import org.apache.commons.cli.*;

public class Main {

    private static void showHelp(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Pangolin Tools", options);
    }

    public static void main(String args[]) throws ParseException {
        Options options = new Options();
        options.addOption("p", "port", true, "port to bind");
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = commandLineParser.parse(options, args);

        if (!commandLine.hasOption("p")) {
            showHelp(options);
            System.exit(1);
        }

        ServerConfig config = new ServerConfig();
        config.setPort(Integer.parseInt(commandLine.getOptionValue('p')));

        Server server = new TestServer(config);
        server.start();
    }
}
