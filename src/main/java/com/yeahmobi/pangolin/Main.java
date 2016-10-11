package com.yeahmobi.pangolin;

import com.yeahmobi.pangolin.proxy.SocksServer;
import org.apache.commons.cli.*;

public class Main {
    public static void main(String args[]) throws ParseException {
        Options options = new Options();
        options.addOption("p", "port", true, "port to bind");
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = commandLineParser.parse(options, args);

        if (!commandLine.hasOption("p")) {
            System.exit(1);
        }

        ServerConfig config = new ServerConfig();
        config.setPort(Integer.parseInt(commandLine.getOptionValue('p')));

        Server server = new SocksServer(config);
        server.start();
    }
}
