package org.vesinitsyn.groupchat.serverside;

import org.vesinitsyn.groupchat.model.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    /**
     * Waits for requests.
     */
    private ServerSocket server;

    /**
     * Contains socket's outputSteams wrapped in PrintWriter to send messages to all registered sockets.
     */
    private ArrayList<PrintWriter> writers;

    public Server(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            this.server = serverSocket;
            this.writers = new ArrayList<>();
            launch();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Starts server.
     */
    private void launch() {
        while (true) {
            try {
                Socket socket = server.accept();
                writers.add(new PrintWriter(socket.getOutputStream()));
                /* Add socket's outputStream to pull of streams. */

                handleNewClient(socket);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Starts new thread to handle concrete socket's requests.
     */
    private void handleNewClient(Socket socket) throws IOException {
        Thread thread = new Thread(new ClientHandler(socket));
        thread.start();
    }

    /**
     * The class that takes message from one socket and send it to all registered sockets.
     */
    private class ClientHandler implements Runnable {

        private Socket socket;
        private ObjectInputStream reader;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
        }

        @Override
        public void run() {
            Message message;

            try (ObjectInputStream stream = new ObjectInputStream(socket.getInputStream())) {
                this.reader = stream;

                while ((message = (Message) reader.readObject()) != null) { // until end of stream is reached

                    for (PrintWriter writer : writers) {
                        sendToClient(writer, message);
                    }
                }
            } catch (IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        }

        /**
         * Sends message to one of clients from pull of outPutStreams.
         */
        private void sendToClient(PrintWriter writer, Message message) {
            writer.println(message.getSender() + ": " + message.getBody());
            writer.flush();
        }
    }
}
