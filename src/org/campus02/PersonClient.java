package org.campus02;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class PersonClient {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        try (Socket server = new Socket("localhost", 1234);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
             ObjectInputStream ois = new ObjectInputStream(server.getInputStream())
        ) {
            // an dieser Stelle -> mit Server verbunden

            // schicke GET ID
            bw.write("get 25");
            bw.newLine();
            bw.flush(); // !!!!!!
            // an dieser Stelle -> Command an Server gesendet

            // auf Antwort vom Server warten
            Person p25 = (Person) ois.readObject();
            System.out.println(p25);

            // GETALL

            bw.write("GETALL");
            bw.newLine();
            bw.flush();

            // hier auf die Personen lesen
            // Jede Person wird uns einzeln geschickt, bis null
            Person p;
            while ((p = (Person) ois.readObject()) != null) {
                System.out.println(p);
            }

            // 2. Möglichkeit
            // lese die ganze Liste
            ArrayList<Person> people = (ArrayList<Person>) ois.readObject();
            System.out.println(people);

        }
    }
}
