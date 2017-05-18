package com.solutionsiq.computers.monadic.springboot.consumer;

import com.solutionsiq.computers.monadic.springboot.services.definition.ComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComputingApplication {

    private ComputerService service;

    @Autowired
    public void setService(ComputerService service){
        this.service=service;
    }

    public long compute(long monadicArgument){
        return this.service.compute(monadicArgument);
    }
}