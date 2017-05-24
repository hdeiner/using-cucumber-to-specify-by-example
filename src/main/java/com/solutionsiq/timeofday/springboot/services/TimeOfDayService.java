package com.solutionsiq.timeofday.springboot.services;

import com.solutionsiq.timeofday.springboot.services.definition.TimeOfDay;

public class TimeOfDayService implements TimeOfDay {

    public String getFormattedTimeOfDayStandard(int hours, int minutes, int seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String getFormattedTimeOfDaySpokenWords(int hour, int minute, int second) {
        String[] hours = {"twelve", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven"};

        if (second >= 30) minute++;

        String output = "";
        if (minute >= 5) output = "almost ten after " + hours[hour%12];
        if (minute >= 10) output = "ten after " + hours[hour%12];
        if (minute >= 15) output = "a quarter after " + hours[hour%12];
        if (minute >= 20) output = "twenty after " + hours[hour%12];
        if (minute >= 25) output = "almost half past " + hours[hour%12];
        if (minute >= 30) output = "half past " + hours[hour%12];
        if (minute >= 35) output = "almost twenty before " +hours[(hour+1)%12];
        if (minute >= 40) output = "twenty before " +hours[(hour+1)%12];
        if (minute >= 45) output = "a quarter before " +hours[(hour+1)%12];
        if (minute >= 50) output = "ten before " +hours[(hour+1)%12];
        if (minute >= 55) output = "almost " +hours[(hour+1)%12];

        output += " o'clock ";

        if ((hour >=0) && (hour <=5)) output += "at night";
        if ((hour >=6) && (hour <=11)) output += "in the morning";
        if ((hour >=12) && (hour <=17)) output += "in the afternoon";
        if ((hour >=18) && (hour <=23)) output += "in the evening";

        return output;
    }

}