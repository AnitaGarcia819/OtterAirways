package edu.csumb.garc4464.otterairways;

/**
 * Created by anitagarcia on 5/11/16.
 */

public class Time {
    private int hour;
    private int minute;
    private boolean meridiem;// 0 = AM 1 = PM

    public Time(){
        this.hour = 00;
        this.minute = 00;
        this.meridiem = false;
    }
    public Time(int hour, int minute, boolean meridiem){
        this.hour = hour;
        this.minute = minute;
        this.meridiem = meridiem;
    }
    public String toString(){
        String result_meridiem;
        if(meridiem)
            result_meridiem = "AM";
        else
            result_meridiem = "PM";
        return hour + ":" + minute + "(" + result_meridiem + ")";
    }
}

