package org.campus02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PersonServer {

    public static void main(String[] args) throws IOException {

        try (ServerSocket ss = new ServerSocket(1234)) {
            // server soll ewig laufen
            while (true) {
                System.out.println("warte auf client");
                Socket client = ss.accept();
                System.out.println("client hat sich verbunden");
                ClientCommunication clientCommunication = new ClientCommunication(client);
                new Thread(clientCommunication).start(); // hier wird die run-Methode der clientCommunication aufgerufen

            }
        }
    }
}
