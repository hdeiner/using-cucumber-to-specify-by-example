package com.solutionsiq.timeofday.springboot.configuration;

import com.solutionsiq.timeofday.springboot.services.TimeOfDayService;
import com.solutionsiq.timeofday.springboot.services.definition.TimeOfDay;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DIConfiguration {

    @Bean
    public TimeOfDay getFormattedTimeOfDay(){
        return new TimeOfDayService();
    }

}