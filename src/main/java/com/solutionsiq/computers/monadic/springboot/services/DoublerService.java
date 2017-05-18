package com.solutionsiq.computers.monadic.springboot.services;

import com.solutionsiq.computers.monadic.springboot.services.definition.ComputerService;

public class DoublerService implements ComputerService {

    public long compute(long monandicArgument) {
        return monandicArgument*2;
    }

}
