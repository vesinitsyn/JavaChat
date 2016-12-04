package org.vesinitsyn.groupchat;

import org.vesinitsyn.groupchat.serverside.Server;

import java.io.*;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        System.setErr(new PrintStream(new File("log.txt")));
        new Server(4242);
    }
}
