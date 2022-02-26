package fr.angel.lyceeconnecte.Models;

import java.util.ArrayList;

public class School {

    private String name, id;
    private ArrayList<String> classes;

    public School(String name, String id, ArrayList<String> classes) {
        this.name = name;
        this.id = id;
        this.classes = classes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<String> classes) {
        this.classes = classes;
    }

    public School() {}
}
