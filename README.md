Dependency Inversion With Spring Boot
=====================================
blah

Guide to this code
------------------
This example will implement a service which computes from value from an argument, as this is the simplest thing that we can implement to demonstrate the concepts we want to talk about.

We will code a computing application that implements a computer service that takes a number and doubles it.  We will also implement variations on that interface.  We will implement a version of it which can square the number, cube the number, or take the factorial of the number. 

We first will look at a bad way of doing this in 
```java
com.solutionsiq.computers.monadic.BadCode
```

The bad code example looks like this:
```java
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
```
So, what's so bad about the code?

First of all, notice how every time we add a new monadic function for computing, we havw to change our code in multiple places.  This is classic "Shotgun Surgery".

And then there's that big if/then/else block in computeAll that is error prone and just hard to look at!

And, most importantly, the code is very repetative.  Not DRY ("Don't repeat yourself") in the least!

How can be make it better?  Let's take a look at
```java
com.solutionsiq.computers.monadic.InversionOfControlCode
```
```java
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
```
What's better about this code?  In a word, it's called interface.  We defined a "Computer" interface to say that there is a method called "compute" which takes one long argument and returns a long.  Now, we are free to implement "Computer" for doubling, squaring, cubing, and taking a factorial of a number.  We can take any of these implementations and execute the "compute" method without having to know, at compile time, what object we are using.  This is known as "Inversion of Control".

So, what if we took this one step further?  What if, instead of using our own devices to code an inversion of control, we use a framework that doesn't wire the packages together until runtime?  This is what is at the core of Spring Boot and is known as Dependency Injection.  The dependency is an object that will be used as a service. And the injection is the passing of the dependency to a dependent object (a client) that then uses it without the need to compile one to the other.

The rest of this project is about how to recreate BadCode and InversionOfControlCode as a Spring Boot application.  It also shows us how to test our code and utilize the promise of Dependency Injection.  We will override the configured service during Junit tests and replace them with alternative implementations of the interface the we depended upon.

Let's break down the code into smaller chunks to understamd what's going on.

The interface that we will depend on and implement is defined as
```java
package com.solutionsiq.computers.monadic.springboot.services.definition;

public interface ComputerService {

    long compute(long monandicArgument);
}
```
We then use that in the service definition
```java
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
```
The actual code that implements the interface is 
```java
package com.solutionsiq.computers.monadic.springboot.services;

import com.solutionsiq.computers.monadic.springboot.services.definition.ComputerService;

public class DoublerService implements ComputerService {

    public long compute(long monandicArgument) {
        return monandicArgument*2;
    }

}
```
And this gets wired into the calling application's runtime Spring Boot context through
```java
package com.solutionsiq.computers.monadic.springboot.configuration;

import com.solutionsiq.computers.monadic.springboot.services.DoublerService;
import com.solutionsiq.computers.monadic.springboot.services.definition.ComputerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DIConfiguration {

    @Bean
    public ComputerService compute(){
        return new DoublerService();
    }

}
```
And, finally, this is the code that exercises everything
```java
package test.com.solutionsiq.computers.monadic.springboot;

import com.solutionsiq.computers.monadic.springboot.consumer.ComputingApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ComponentScan("com.solutionsiq.computers.monadic.springboot")
public class InversionOfControlUsingSpringBoot_AsAutowired_DoublerTest {

    private AnnotationConfigApplicationContext context = null;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(InversionOfControlUsingSpringBoot_AsAutowired_DoublerTest.class);
    }

    @After
    public void tearDown() throws Exception {
        context.close();
    }

    @Test
    public void checkDoublerService() {
        ComputingApplication app = context.getBean(ComputingApplication.class);
        assertThat(app.compute(8),is((long)16));
    }

}
```
But what about the promise of using Dependency Injection to provide alternate implementations of the interface being depended upon?  How can we "wire in" an alternative implementation of code that we'd rather test double when doing unit testing?  This is needed when the implementation depends on external systems.  Perhaps ones that are slow (for example, databases), or expensive (for example, trading on a stock exchange), or non-deterministic (for example, the weather for a region)?

Here's test code that uses a different implementation of our ComputerService interface.  Notice how @ComponentScan will use another package to get the alternative defintion from and overide the wiring of the interface to the code that implenents it
```java
package test.com.solutionsiq.computers.monadic.springboot;

import com.solutionsiq.computers.monadic.springboot.consumer.ComputingApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ComponentScan({"com.solutionsiq.computers.monadic.springboot", "test.com.solutionsiq.computers.monadic.springboot.configuration.squarer"})
public class InversionOfControlUsingSpringBootSquarerTest {

    private AnnotationConfigApplicationContext context = null;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(InversionOfControlUsingSpringBootSquarerTest.class);
    }

    @After
    public void tearDown() throws Exception {
        context.close();
    }

    @Test
    public void checkCuberService() {
        ComputingApplication app = context.getBean(ComputingApplication.class);

        assertThat(app.compute(8),is((long)64));
    }

}
```
To get to the alternate implementation of the interface, we have to override the definition of the wiring of bean into Spring
```java
package test.com.solutionsiq.computers.monadic.springboot.configuration.squarer;

import com.solutionsiq.computers.monadic.springboot.configuration.DIConfiguration;
import com.solutionsiq.computers.monadic.springboot.services.SquarerService;
import com.solutionsiq.computers.monadic.springboot.services.definition.ComputerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DIConfiguration.class)
public class DiConfigurationSquarer {

    @Bean
    public ComputerService compute(){
        return new SquarerService();
    }

}
```
That's pretty much it.