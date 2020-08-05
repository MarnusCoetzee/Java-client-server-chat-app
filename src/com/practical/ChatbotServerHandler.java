package com.practical;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.StringTokenizer;

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

            int responseNumber = 0;
            int numQuestions = 0;
            out.println("HELLO - You may ask me 4 questions");
            boolean sessionStarted = false;
            String message = "";

            while ((numQuestions < 4) && (!message.equals("DONE"))) {
                message = in.readLine();
                StringTokenizer tokenizer = new StringTokenizer(message);
                String command = tokenizer.nextToken();
                System.out.println("command: " + command);
                if (command.equals("HELLO") && tokenizer.nextToken().equals("BOT")) {
                    sessionStarted = true;
                    System.out.println("Client started a session");
                    out.println("Ask me a question or done");
                } else if (command.equals("ASK") && sessionStarted) {
                    numQuestions += 1;
                    responseNumber += 1;
                    String question = "";
                    while (tokenizer.hasMoreTokens()) {
                        question += tokenizer.nextToken();
                    }
                    System.out.println("Question received: " + question);
                    if (question.contains("virus") || question.contains("covid-19")) {
                        out.println("0" + responseNumber + "Please see sacoronavirus.co.za");
                    } else if (question.startsWith("Are")) {
                        Random r = new Random();
                        int randomResponse = r.nextInt(3);
                        switch (randomResponse) {
                            case 0: out.println("0" + responseNumber + "Yes");
                                break;
                            case 1: out.println("0" + responseNumber + "No");
                                break;
                            case 2: out.println("0" + responseNumber + "Maybe");
                                break;
                        }
                    } else if (question.startsWith("Why")) {
                        out.println("0"+responseNumber+"Because the boss says so - see Ulink");
                    } else {
                        Random r = new Random();
                        int decision = r.nextInt(3);
                        switch(decision)
                        {
                            case 0: out.println("0" + responseNumber + "Escusez Moi?");
                                break;
                            case 1: out.println("0" + responseNumber + "Oh ok!");
                                break;
                            case 2:out.println("0" + responseNumber + "Meh");
                                break;
                        }
                    }
                    System.out.println(numQuestions + " questions asked so far");
                } else if (!message.equals(("DONE"))) {
                    out.println("The command you entered is incorrect");
                }
                if (message.equals("DONE")) {
                    out.println("0" + responseNumber + "OK BYE - "+numQuestions + " questions answered");
                } else {
                    out.println("HAPPY TO HAVE HELPED - "+numQuestions+" Questions answered");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Closing conenction");
                if (clientConnection != null) {
                    clientConnection.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
