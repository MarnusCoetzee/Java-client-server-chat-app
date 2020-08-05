package com.practical;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatbotServerHandler implements Runnable {

    private Socket clientConnection;

    public ChatbotServerHandler(Socket clientConnection) {
        this.clientConnection = clientConnection;
    }

    @Override
    public void run() {
        // set up streams
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader((clientConnection.getInputStream())));
            PrintWriter out = new PrintWriter(clientConnection.getOutputStream(), true);
        } catch (IOException e) {

        }
    }
}
