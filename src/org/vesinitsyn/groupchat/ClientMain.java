package org.vesinitsyn.groupchat;

import org.vesinitsyn.groupchat.clientside.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

public class ClientMain {

    public static void main(String[] args) throws IOException {

        Client client = new Client("192.168.1.76", 4242);
        client.addObserver(new Obser());

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your name: ");
        String name = reader.readLine();
        client.setName(name);

        System.out.println("Start conversation..");

        String command;
        while (!(command = reader.readLine()).equals("exit")) {
            client.sendToServer(command);
        }

        client.closeClient();
    }
}

class Obser implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);
    }
}
