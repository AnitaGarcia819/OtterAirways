package edu.csumb.garc4464.otterairways;

/**
 * Created by anitagarcia on 5/11/16.
 */
public class Customer {
    private String username;
    private String password;

    public Customer(){
        this.username = "UNKNOWN";
        this.password = "UNKNOWN";
    }
    public Customer(String username, String password){
        this.username = username;
        this.password = password;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String toString(){
        return username + " " + password;
    }
}
