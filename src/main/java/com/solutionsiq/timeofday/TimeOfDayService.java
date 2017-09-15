package com.solutionsiq.timeofday;

import com.solutionsiq.timeofday.definition.TimeOfDay;

public class TimeOfDayService implements TimeOfDay {

    private static final int SECONDS_IN_A_HALF_MINUTE = 30;
    private static final int HOURS_IN_A_QUARTER_OF_A_DAY = 6;
    private static final int MINUTES_TO_ROUND_TO = 5;
    private static final int MINUTE_TO_START_FUZZING_INTO_NEXT_HOUR = 35;

    private static final String[] NAMES_OF_THE_HOURS = {"twelve", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven"};
    private static final String[] FUZZY_TIME_WORDS = {"about", "a little after", "about ten after", "about a quarter after", "about twenty after", "almost half past", "about half past", "almost twenty before", "about twenty before", "about a quarter of", "about ten of", "almost", "about"};
    private static final String[] QUADRANTS_OF_THE_DAY = {"at night", "in the morning", "in the afternoon", "in the evening"};

    public String getFormattedTimeOfDaySpokenWords(int hour, int minute, int second){
        String formattedTime = "";

        if (second >= SECONDS_IN_A_HALF_MINUTE) minute++;

        formattedTime += FUZZY_TIME_WORDS[(minute+(MINUTES_TO_ROUND_TO/2))/MINUTES_TO_ROUND_TO] + " ";

        if (minute < MINUTE_TO_START_FUZZING_INTO_NEXT_HOUR) {
            formattedTime += NAMES_OF_THE_HOURS[hour % NAMES_OF_THE_HOURS.length];
        }  else {
            formattedTime += NAMES_OF_THE_HOURS[(hour+1) % NAMES_OF_THE_HOURS.length];
        }

        formattedTime += " " + QUADRANTS_OF_THE_DAY[hour/HOURS_IN_A_QUARTER_OF_A_DAY];

        return formattedTime;
    }
}