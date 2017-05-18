package com.solutionsiq.computers.monadic.springboot.configuration;

import com.solutionsiq.computers.monadic.springboot.services.DoublerService;
import com.solutionsiq.computers.monadic.springboot.services.definition.ComputerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DIConfigurationDoubler {

    @Bean
    public ComputerService compute(){
        return new DoublerService();
    }

}