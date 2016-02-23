package com.TheIronYard;

/**
 * Created by branden on 2/22/16 at 12:00.
 */
public class User {

    String name, password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name) {
        this.name = name;
    }
}