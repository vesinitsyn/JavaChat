package org.vesinitsyn.groupchat;

import org.vesinitsyn.groupchat.serverside.Server;

import java.io.*;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        new Server(4242);
    }
}
