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

