package org.campus02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PersonLoader {

    private String filePath;

    public PersonLoader(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Person> load() throws PersonLoadException {
        ArrayList<Person> people = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader(filePath))) {
            String line;

            // wenn notwendig, 1. Zeile skippen
            // br.readLine();
            while ((line = br.readLine()) != null) {
                // 1;Vorname;Nachname
                String[] personData = line.split(";");
                // [id, Vorname, Nachname]
                // Zugriff auf den Vorname
                // personData[1]

                int id = Integer.parseInt(personData[0]);
                String firstName = personData[1];
                String lastName = personData[2];
                Person p = new Person(id, firstName, lastName);
                people.add(p);

                // people.add(new Person(Integer.parseInt(personData[0]), personData[1], personData[2]));
            }
        } catch (FileNotFoundException e) {
            throw new PersonLoadException("file does not exist", e);
        } catch (IOException e) {
            throw new PersonLoadException("can not read file", e);
        }

        // default Sortierung nach Comparable
        Collections.sort(people);

        // andere Sortierungen mittels Comparator
        return people;

    }
}
