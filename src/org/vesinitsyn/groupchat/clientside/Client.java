package org.vesinitsyn.groupchat.clientside;

import org.vesinitsyn.groupchat.model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

public class Client extends Observable {

    /**
     * Person's name.
     */
    private String name;
    private ObjectOutputStream writer;
    private BufferedReader reader;
    private Socket socket;

    public Client(String host, int port, String personsName) throws IOException {
        this(host, port);
        setName(personsName);
    }

    public Client(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.writer = new ObjectOutputStream(socket.getOutputStream());
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        launch();
    }

    /**
     * Sends message to server.
     */
    public void sendToServer(String message) throws IOException {
        writer.writeObject(new Message(name, message));
    }

    /**
     * Create another thread to handle incoming messages while sending person's input to server.
     */
    private void launch() {
        Thread thread = new Thread(new ServerHandler());
        thread.start();
    }

    private class ServerHandler implements Runnable {

        @Override
        public void run() {
            String message;

            try {
                while ((message = reader.readLine()) != null) {
                    setChanged();
                    notifyObservers(message); // notify observers who are listening to this client e.g. UI
                    clearChanged();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            } finally {
                try {
                    System.out.println("finally");
                    socket.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void closeClient() {
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }
}
