package com.solutionsiq.computers.monadic;

import java.util.ArrayList;
import java.util.List;

//
//  So, this is a little better than BadCode.
//
//  We've implemented a Computer interface, and we've changed the various
//  monadic functions to use that interface (which says "I've got a method
//  called computeDoubler that returns a long when supplied with a long argument").
//
//  That's worked wonders for our computeAll method mess from BadCode.
//

public class InversionOfControlCode {

    public static void main(String[] args) {
        new ComputerProcessor()
                .addComputer(new Doubler())
                .addComputer(new Squarer())
                .addComputer(new Cuber())
                .addComputer(new Factorialer())
                .computeAll(8);
    }

    static class ComputerProcessor {

        private List<Computer> computers = new ArrayList<>();

        public ComputerProcessor addComputer(Computer c) {
            computers.add(c);
            return ComputerProcessor.this;
        }

        public void computeAll(long value) {
            for (Computer c : computers) {
                String name = c.getClass().getSimpleName();
                System.out.println("Computer: " + name + ", value: " + value + " computed value: " + c.compute(value));
            }
        }
    }

    interface Computer {

        long compute(long value);
    }

    static class Doubler implements Computer {

        public long compute(long value) {
            return value*2;
        }
    }

    static class Squarer implements Computer {

        public long compute(long value) {
            return value*value;
        }
    }

    static class Cuber implements Computer {

        public long compute(long value) {
            return value*value*value;
        }
    }

    static class Factorialer implements Computer {

        public long compute(long value) {
            long factorial = 1;
            for (long l=1; l<=value; l++) {
                factorial *= l;
            }
            return factorial;
        }
    }

}
