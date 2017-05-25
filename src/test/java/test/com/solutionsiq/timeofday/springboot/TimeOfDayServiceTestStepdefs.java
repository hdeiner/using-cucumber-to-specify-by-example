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

}
