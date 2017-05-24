package com.solutionsiq.timeofday.springboot.consumer;

import com.solutionsiq.timeofday.springboot.services.definition.TimeOfDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeOfDayConsumer {

    private TimeOfDay service;

    @Autowired
    public void setService(TimeOfDay service){
        this.service=service;
    }

    public String getFormattedTimeOfDay(int hours, int minutes, int seconds){
        return this.service.getFormattedTimeOfDayStandard(hours, minutes, seconds);
    }

    public String getFormattedTimeOfDaySpokenWords(int hours, int minutes, int seconds){
        return this.service.getFormattedTimeOfDaySpokenWords(hours, minutes, seconds);
    }

}