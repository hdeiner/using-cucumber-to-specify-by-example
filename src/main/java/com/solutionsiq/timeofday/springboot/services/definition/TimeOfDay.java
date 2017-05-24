package com.solutionsiq.timeofday.springboot.services.definition;

public interface TimeOfDay {
    String getFormattedTimeOfDayStandard(int hours, int minutes, int seconds);
    String getFormattedTimeOfDaySpokenWords(int hours, int minutes, int seconds);
}
