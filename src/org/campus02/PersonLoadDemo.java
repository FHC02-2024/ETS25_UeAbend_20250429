package org.campus02;

import java.util.ArrayList;

public class PersonLoadDemo {

    public static void main(String[] args) throws PersonLoadException {
        PersonLoader pl = new PersonLoader("daten/persons.csv");
        ArrayList<Person> people = pl.load();
        System.out.println(people);
    }
}
