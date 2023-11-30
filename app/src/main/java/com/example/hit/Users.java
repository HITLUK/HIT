package com.example.hit;

import java.io.Serializable;

public class Users implements Serializable {
    private long id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String role;

    public Users (long id, String login, String password, String name, String surname, String role ){
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }
    public long getId(){
        return id;
    }
    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return password;
    }
    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getRole(){
        return role;
    }
}
