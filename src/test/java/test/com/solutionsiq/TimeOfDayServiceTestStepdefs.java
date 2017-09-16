package test.com.solutionsiq;

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
