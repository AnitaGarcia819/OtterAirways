package edu.csumb.garc4464.otterairways;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anitagarcia on 5/11/16.
 */

public class Transaction {
    protected String type;
    protected String username;
    protected String currentTime;

    public Transaction(){
        this.type = "UNKNOWN";
        this.username = "UNKNOWN";
        this.currentTime = "UNKNOWN";
    }
    public Transaction(int type, String username, Date time){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        switch(type){
            case 1:
                this.type = "(New Account)";
                break;
            case 2:
                this.type = "(Reserve Seat)";
                break;
            case 3:
                this.type = "(Cancelation)";
                break;
            default:
                this.type = "UNKNOWN";
                break;
        }
        this.username = username;
        this.currentTime = dateFormat.format(time);
    }
    public String toString(){
        return type + " | " + username + " | " + currentTime + "\n";
    }
}