Using Cucumber to Specify By Example
====================================

### Status
[![Build Status](https://travis-ci.org/hdeiner/cucumber-driven-drools.svg?branch=master)](https://travis-ci.org/hdeiner/cucumber-driven-drools)

Guide to this code
------------------
Let's talk how we develop any amount of non-trival code.

It used to be that we gathered requirements, wrote an analysis/design document, coded the application, and tested it.

And that was fraught with issues.  We didn't understand the problem well enough to design the solution.  We miscoded something.  We found errors.  When we finally showed our customer, we really found errors.  In fixing stuff, things that used to work stopped working, and we really didn't know which end was up.

There's a better way to do this.  It's called Specification By Example.  It's done with "The Three Amigos".  A business type (who understands the problem to solve), a developer type (who will untimately solve the problem), and a QA type (who also solves the problem, but is here right now to make sure that the business person and developer don't miss stuff).  All three get together and discuss the problem to solve.

But instead of then writing requirements, we write tests.  Those tests become our executable requirements.  

For this example, we will use Cucumber to execute our tests, and use Gherkin to express those tests.

The problem that we are solving is the business person's desire to "say the time, not just display it."  So, if the time is "20:04:45", say it as "almost ten after eight in the evening".  As you can see, there are plenty of "nocks and crannies for things to go wrong".

So, the first thing we do is specify the behaviors of the code in a Cucumber feature file:
```Gherkin
Feature: Time of day formatted as spoken words

  Scenario Outline: Check Spoken Formatted

    When the hour is "<hour>"
    And the minute is "<minute>"
    And the second is "<second>"
    And I should see spoken formatting of "<result>"

    Examples:
      |hour|minute|second|result                                 |
      |9   |0     |0     |nine in the morning                    |
      |20  |4     |45    |almost ten after eight in the evening  |
      |10  |11    |12    |ten after ten in the morning           |
      |22  |12    |12    |ten after ten in the evening           |
      |10  |14    |35    |a quarter after ten in the morning     |
      |5   |21    |18    |twenty after five at night             |
      |23  |25    |25    |almost half past eleven in the evening |
      |15  |31    |31    |half past three in the afternoon       |
      |9   |34    |33    |almost twenty before ten in the morning|
      |21  |46    |0     |a quarter of ten in the evening        |
      |11  |51    |10    |ten of twelve in the morning           |
```
Adding in a test runner is next.  We are using Cucumber for Java and JUnit for our code, as below (not shown are the Maven pom.xml dependency management pieces)

```java
package test.com.solutionsiq.timeofday;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(
//      dryRun   = false,
//      strict = true,
//      tags     = "",
        monochrome = false,
        features = { "src/test/java/test/com/solutionsiq/timeofday/" },
        glue     = { "test.com.solutionsiq.timeofday" },
        plugin   = { "pretty", "html:target/cucumber-reports/cucumber-html-report", "json:target/cucumber-reports/cucumber-json-report.json" }
)

public class TimeOfDayServiceTestRunner {
}
```

And finally, we will need some glue to tie the Gherkin feature file into our code - the so called StepDefs.

```java
package test.com.solutionsiq.timeofday;

import com.solutionsiq.timeofday.*;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TimeOfDayServiceTestStepdefs {

    private int hour = -1;
    private int minute = -1;
    private int second = -1;

    @When("^the hour is \"([^\"]*)\"$")
    public void the_hour_is(String hour) throws Throwable {
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

    @When("^I should see spoken formatting of \"([^\"]*)\"$")
    public void i_should_see_spoken_formatting_of(String formattedString) throws Throwable {
        TimeOfDayService timeOfDayService = new TimeOfDayService();
        assertThat(timeOfDayService.getFormattedTimeOfDaySpokenWords(hour, minute, second),is(formattedString));
    }

}
```

Don't forget to look at the pretty cucumber reports located at target/cucumber-pretty-reports/cucumber-html-reports/overview-features.html and target/cucumber-pretty-reports/cucumber-html-reports/overview-steps.html!