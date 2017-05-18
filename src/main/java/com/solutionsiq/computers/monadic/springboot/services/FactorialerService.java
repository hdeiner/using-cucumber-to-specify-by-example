package com.solutionsiq.computers.monadic.springboot.services;

import com.solutionsiq.computers.monadic.springboot.services.definition.ComputerService;

public class FactorialerService implements ComputerService {

    public long compute(long monadicArgument) {
        long factorial = 1;
        for (long l=1; l<=monadicArgument; l++) {
            factorial *= l;
        }
        return factorial;
    }

}
