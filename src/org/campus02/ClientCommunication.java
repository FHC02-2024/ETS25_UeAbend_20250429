package org.campus02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientCommunication implements Runnable {

    private Socket client;

    public ClientCommunication(Socket client) {
        this.client = client;
    }

    private void handleClient() {
        try {
            ArrayList<Person> people = new PersonLoader("daten/persons.csv").load();

            // BufferedReade zum Lesen
            // ObjectOutputStream zum Schreiben
            try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream())
            ) {
                String command;
                while ((command = br.readLine()) != null) {
                    // wir reagieren auf den Client seinen Wunsch (command)

                    // equalIgnoreCase => exIt, EXIT, exit, EXit,...
                    if (command.equalsIgnoreCase("exit")) {
                        System.out.println("client wants to exit");
                        client.close();
                        //break;
                    }

                    if (command.equalsIgnoreCase("getall")) {
                        for (Person p : people) {
                            oos.writeObject(p);
                        }
                        oos.writeObject(null);

                        // 2. Möglichkeit
                        // schicke ganze Liste
                        oos.writeObject(people);
                    } else {
                        // GET 1
                        // GET 4
                        // GET person 3
                        String[] input = command.split(" ");
                        if (input.length != 2) {
                            System.out.println("Wrong command");
                            oos.writeObject(null);
                        } else {
                            int id = Integer.parseInt(input[1]); // welche id ist gesucht
                            for (Person p : people) {
                                if (p.getId() == id) {
                                    oos.writeObject(p);
                                }
                            }
                            //oos.writeObject(null);
                        }
                    }

                    oos.flush(); // !!!!!!
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (PersonLoadException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
        // eigentliche Logik
        handleClient();
    }
}
