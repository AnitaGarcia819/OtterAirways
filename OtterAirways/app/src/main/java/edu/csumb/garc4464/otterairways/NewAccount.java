package edu.csumb.garc4464.otterairways;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by anitagarcia on 5/11/16.
 */

public class NewAccount extends Transaction{
    public NewAccount(){

    }
    public NewAccount(int type, String username, Date time){
        super(1,username, time);
    }
    public String toString(){
        // See what happend without this method
        return super.toString();
    }
}

