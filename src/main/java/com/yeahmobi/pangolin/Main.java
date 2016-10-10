package com.yeahmobi.pangolin;

public class Main {
    public static void main(String args[]) {
        ServerConfig config = new ServerConfig();
        Server server = new Server(config);
        server.start();
    }
}
