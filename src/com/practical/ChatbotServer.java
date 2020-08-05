package com.practical;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatbotServer {

    private static ServerSocket serverSocket;
    private final static int PORT = 8080;

    public static void main(String[] args) {
        try {
            System.out.println("Server attempting to bind to port: " + PORT);
            serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for connections...\n");

            do {
                Socket clientConnection = serverSocket.accept();
                ChatbotServerHandler serverHandler = new ChatbotServerHandler(clientConnection);
                Thread t = new Thread(serverHandler);
                t.start();
            } while (true);
        } catch (IOException e) {
            System.err.println("Unable to attach to a port");
        }
    }
}
