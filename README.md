Dependency Inversion With Spring Boot
=====================================
Guide to this code
------------------
In this sample, we are going to implement a service that returns formatted versions of the time of day.

First, there is a very simple formatting.  We want to present the time of day formatted in 24 hour time, with the hours, minutes, and seconds formatted as 2 decimal digits with colons seperating the three values.  For example, 9 o'clock in the morning is represented as "09:00:00".

The second formatting is more demanding.  We want to represent the time as we might speak it.  For example, "09:00:00" should be formatted as "nine o'clock in the morning".  That is, after rounding the seconds to the nearest minute, we "speak" the time using words to the nearest 5 minute interval with such phrases as "almost ten after", "ten after", "a quarter after", "twenty after", "almost half past", "half past", "almost twenty before", "twenty before", "a quarter before", "ten before", and "almost".  We also have "in the morning", "in the afternoon", "in the evening", and "at night".

The code in BadCode does this (I think).
```java
package com.solutionsiq.timeofday;

import java.time.LocalDateTime;

public class BadCode {

    public String getTimeOfDayFormattedDonePoorly() {
        int hour = LocalDateTime.now().getHour();
        int minute = LocalDateTime.now().getMinute();
        int second = LocalDateTime.now().getSecond();
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public String getTimeOfDaySpokenDonePoorly() {
        String[] hours = {"twelve", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven"};

        int hour = LocalDateTime.now().getHour();
        int minute = LocalDateTime.now().getMinute();
        int second = LocalDateTime.now().getSecond();
        if (second >= 30) minute++;

        String output = "";
        if (minute >= 0) output = hours[hour%12];
        if (minute >= 5) output = "almost ten after " + hours[hour%12];
        if (minute >= 10) output = "ten after " + hours[hour%12];
        if (minute >= 15) output = "a quarter after " + hours[hour%12];
        if (minute >= 20) output = "twenty after " + hours[hour%12];
        if (minute >= 25) output = "almost half past " + hours[hour%12];
        if (minute >= 30) output = "half past " + hours[hour%12];
        if (minute >= 35) output = "almost twenty before " +hours[(hour+1)%12];
        if (minute >= 40) output = "twenty before " +hours[(hour+1)%12];
        if (minute >= 45) output = "a quarter before " +hours[(hour+1)%12];
        if (minute >= 50) output = "ten before " +hours[(hour+1)%12];
        if (minute >= 55) output = "almost " +hours[(hour+1)%12];

        output += " o'clock ";

        if ((hour >=0) && (hour <=5)) output += "at night";
        if ((hour >=6) && (hour <=11)) output += "in the morning";
        if ((hour >=12) && (hour <=17)) output += "in the afternoon";
        if ((hour >=18) && (hour <=23)) output += "in the evening";

        return output;
    }
}
```

I'm pretty sure that getTimeOfDayFormattedDonePoorly() will work.  I'm not as comfortable with getTimeOfDaySpokenDonePoorly().  Why?  Well, it's in the tests.

```java
package test.com.solutionsiq.timeofday;

import com.solutionsiq.timeofday.BadCode;
import org.junit.Test;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class BadCodeTest {

    @Test
    public void checkTimeOfDayFormatted() {
        assertThat(new BadCode().getTimeOfDayFormattedDonePoorly(),matchesPattern("^\\d\\d:\\d\\d:\\d\\d$"));
    }

    @Test
    public void checkTimeOfDaySpoken() {
        // with responses like "a quarter before eleven o'clock in the morning",
        // there aren't any meaningful tests that I can really come up with
        assertThat(new BadCode().getTimeOfDaySpokenDonePoorly(),is(not("")));
    }

}
```
We can't really make a really meaningful test for getTimeOfDayFormattedDonePoorly().  Every time we run it, the time will be different.  The best we can do is make sure that the string has 3 sets of 2 decimal digits seperated by the ":" symbol.

Maybe that's good enough for getTimeOfDayFormattedDonePoorly().  But see what happens for getTimeOfDaySpokenDonePoorly().  The best that I can reasonably think of is to make sure that the result isn't null.  Yeah.  I could have used an enourmous RegEx to make it slightly better.  But I'm not testing the logic in the routine, and debugging any defect would be impossible, since I don't know the conditions that the code ran against.
 
We know that this is because we are depending on an external compoent for time of dat directly in the code that we'd like to test.  And we know that an Inversion of Control, where we depend on an interface, and then pass the depended upon component into the implementation of the interface at run time will fix this.

But what if we wanted to use Spring Boot, with it's Dependency Injection mechanisms to do this?  The rest of the solution shows how to do this, and gives us one possible way to test our code.

You may have heard that Spring was hard to use because all of the components had to be wired together using XML configurations.  That's not far from the truth.  But these days, Spring can also be wired together using annotations in the code.  This example shows current Spring annotation based configuration.

Let's first take a look at the interface that we want to use in our inversion of control component.
```java
package com.solutionsiq.timeofday.springboot.services.definition;

public interface TimeOfDay {
    String getFormattedTimeOfDayStandard(int hours, int minutes, int seconds);
    String getFormattedTimeOfDaySpokenWords(int hours, int minutes, int seconds);
}
```
Immediately, you see that we are seperating the time of day from the logic that operates upon the time of day.

This interface is implemented in the following code:
```java
package com.solutionsiq.timeofday.springboot.services;

import com.solutionsiq.timeofday.springboot.services.definition.TimeOfDay;

public class TimeOfDayService implements TimeOfDay {

    public String getFormattedTimeOfDayStandard(int hours, int minutes, int seconds) {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String getFormattedTimeOfDaySpokenWords(int hour, int minute, int second) {
        String[] hours = {"twelve", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven"};

        if (second >= 30) minute++;

        String output = "";
        if (minute >= 0) output = hours[hour%12];
        if (minute >= 5) output = "almost ten after " + hours[hour%12];
        if (minute >= 10) output = "ten after " + hours[hour%12];
        if (minute >= 15) output = "a quarter after " + hours[hour%12];
        if (minute >= 20) output = "twenty after " + hours[hour%12];
        if (minute >= 25) output = "almost half past " + hours[hour%12];
        if (minute >= 30) output = "half past " + hours[hour%12];
        if (minute >= 35) output = "almost twenty before " +hours[(hour+1)%12];
        if (minute >= 40) output = "twenty before " +hours[(hour+1)%12];
        if (minute >= 45) output = "a quarter before " +hours[(hour+1)%12];
        if (minute >= 50) output = "ten before " +hours[(hour+1)%12];
        if (minute >= 55) output = "almost " +hours[(hour+1)%12];

        output += " o'clock ";

        if ((hour >=0) && (hour <=5)) output += "at night";
        if ((hour >=6) && (hour <=11)) output += "in the morning";
        if ((hour >=12) && (hour <=17)) output += "in the afternoon";
        if ((hour >=18) && (hour <=23)) output += "in the evening";

        return output;
    }

}
```
Nothing fancy so far.  In fact, we haven't even called upon Spring at all.

Now, let's start doing some Spring magic.  First, let's define the Bean that Spring will use and wire into a caller of the service.  That's done with the following code:
```java
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
```
The annotations for @Configuration and @Bean replace a ton of previously confusing XML configuration from yesteryear. 

And, with a little more code, we can define the consumer side of that bean.
```java
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
```
The annotations here expose and provide the ability for a caller to get answers from our service. 

On to the caller's code.  In this example, I am only providing the testing side of the code.  And it's organized as Cucumber for Java code with Junit and Hamcrest.  Let's start with the tests themselves.
```Gherkin
Feature: Time of day, formated as text and formatted as spoken words

  Scenario Outline: Check Standard Formatted

    When the hour is "<hour>"
    And the minute is "<minute>"
    And the second is "<second>"
    And I should see standard formatting of "<result>"

    Examples:
      |hour|minute|second|result  |
      |10  |11    |12    |10:11:12|
      |23  |0     |5     |23:00:05|

  Scenario Outline: Check Spoken Formatted

    When the hour is "<hour>"
    And the minute is "<minute>"
    And the second is "<second>"
    And I should see spoken formatting of "<result>"

    Examples:
      |hour|minute|second|result                                        |
      |9   |0     |0     |nine o'clock in the morning                   |
      |10  |11    |12    |ten after ten o'clock in the morning          |
      |10  |14    |35    |a quarter after ten o'clock in the morning    |
      |22  |44    |35    |a quarter before eleven o'clock in the evening|
      
  Scenario: Close up Spring
  
    When I reach here, I'm done
```
The reason that I'm testing in Cucumber is that I want my tests to be more human readable.  I use the outline form of Gherkin to encourage testing of all the the edge cases that we should be testing for.  Only a small sample of the tests are written in this sample.

We know that Cucumber requires some fairly boilerplate test runner code.
```java
package test.com.solutionsiq.timeofday.springboot;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(
//      dryRun   = false,
//      strict = true,
//      tags     = "",
        monochrome = false,
        features = { "src/test/java/test/com/solutionsiq/timeofday/springboot" },
        glue     = { "test.com.solutionsiq.timeofday.springboot" },
        plugin   = { "pretty", "html:target/cucumber-reports/cucumber-html-report", "json:target/cucumber-reports/cucumber-json-report.json" }
)

public class TimeOfDayServiceTestRunner {
}
```
The heavy lifting for the testing takes place in the stepdefs.
```java
package test.com.solutionsiq.timeofday.springboot;

import com.solutionsiq.timeofday.springboot.consumer.TimeOfDayConsumer;
import cucumber.api.java.en.When;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@ComponentScan("com.solutionsiq.timeofday.springboot")
public class TimeOfDayServiceTestStepdefs {

    private static AnnotationConfigApplicationContext context = null;

    private int hour = -1;
    private int minute = -1;
    private int second = -1;

    @When("^the hour is \"([^\"]*)\"$")
    public void the_hour_is(String hour) throws Throwable {
        if (context == null) {
            context = new AnnotationConfigApplicationContext(TimeOfDayServiceTestStepdefs.class);
        }
        this.hour = Integer.parseInt(hour);
    }

    @When("^the minute is \"([^\"]*)\"$")
    public void the_minute_is(String minute) throws Throwable {
        this.minute = Integer.parseInt(minute);
    }

    @When("^the second is \"([^\"]*)\"$")
    public void the_second_is(String second) throws Throwable {
        this.second = Integer.parseInt(second);
    }

    @When("^I should see standard formatting of \"([^\"]*)\"$")
    public void i_should_see_standard_formatting_of(String formattedString) throws Throwable {
        TimeOfDayConsumer timeOfDayConsumer = context.getBean(TimeOfDayConsumer.class);
        assertThat(timeOfDayConsumer.getFormattedTimeOfDay(hour, minute, second),is(formattedString));
    }

    @When("^I should see spoken formatting of \"([^\"]*)\"$")
    public void i_should_see_spoken_formatting_of(String formattedString) throws Throwable {
        TimeOfDayConsumer timeOfDayConsumer = context.getBean(TimeOfDayConsumer.class);
        assertThat(timeOfDayConsumer.getFormattedTimeOfDaySpokenWords(hour, minute, second),is(formattedString));
    }
    
    @When("^I reach here, I'm done$")
    public void i_reach_here_I_m_done() throws Throwable {
        context.close();
    }

}
```
Let's go through some of this code slowly.  

First, look at the annotation right before the class declaration.
```java
@ComponentScan("com.solutionsiq.timeofday.springboot")
```
This annotation tells Spring where to find beans to satisfy calls to the consumer side of the beans configured with Spring.

Spring comes to life and starts scanning when it establishes it's context with
```java
        if (context == null) {
            context = new AnnotationConfigApplicationContext(TimeOfDayServiceTestStepdefs.class);
        }
```
The context can be a costly operation.  That's why I made it static within the class.

This code is a litttle bit of a kludge in it.  The  context.close().

And don't forget to look at the pretty cucumber reports located at target/cucumber-pretty-reports/cucumber-html-reports/overview-features.html and target/cucumber-pretty-reports/cucumber-html-reports/overview-steps.html.