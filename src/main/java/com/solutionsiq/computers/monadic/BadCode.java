package com.solutionsiq.computers.monadic;

import java.util.ArrayList;
import java.util.List;

//
//  DON'T DO THIS AT HOME!!!
//
//  Notice how every time we add a new monadic function for computing, we havw to change
//  our code in multiple places.  This is classic "Shotgun Surgery".
//
//  And that big if/then/else block in computeAll is a large PIA.
//
//  Also, our code is very repetative.  Not DRY ("Don't repeat yourself") in the least!
//

public class BadCode {

    public static void main(String[] args) {
        new ComputerProcessor()
                .addComputer(new Doubler())
                .addComputer(new Squarer())
                .addComputer(new Cuber())
                .addComputer(new Factorialer())
                .computeAll(8);
    }

    static class ComputerProcessor {

        private List computers = new ArrayList();

        public ComputerProcessor addComputer(Object o) {
            computers.add(o);
            return ComputerProcessor.this;
        }

        public void computeAll(long value) {
            for (Object o : computers) {
                long computedValue = -1;

                if (o instanceof Doubler) {
                    computedValue = ((Doubler) o).computeDouble(value);
                } else if (o instanceof Squarer) {
                    computedValue = ((Squarer) o).computeSquare(value);
                } else if (o instanceof Cuber) {
                    computedValue = ((Cuber) o).computeCube(value);
                } else if (o instanceof Factorialer) {
                    computedValue = ((Factorialer) o).computeFactorial(value);
                }
                String name = o.getClass().getSimpleName();
                System.out.println("Computer: " + name + ", value: " + value + " computed value: " + computedValue);
            }
        }
    }

    static class Doubler {

        public long computeDouble(long value) {
            return value*2;
        }
    }

    static class Squarer {

        public long computeSquare(long value) {
            return value*value;
        }
    }

    static class Cuber {

        public long computeCube(long value) {
            return value*value*value;
        }
    }

    static class Factorialer {

        public long computeFactorial(long value) {
            long factorial = 1;
            for (long l=1; l<=value; l++) {
                factorial *= l;
            }
            return factorial;
        }
    }
}