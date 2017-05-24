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
