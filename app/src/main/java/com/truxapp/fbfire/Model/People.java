package com.truxapp.fbfire.Model;

/**
 * Created by bc9vq1 on 9/1/17.
 */

public class People {

    private String name;
    private String lastName;
    private String fullNme;
    private int id;

    public People(String name, String lastName, int id) {
        this.name = name;
        this.lastName = lastName;
        this.id = id;
        this.fullNme = name + lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullNme() {
        return fullNme;
    }

    public void setFullNme(String fullNme) {
        this.fullNme = fullNme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }
}
