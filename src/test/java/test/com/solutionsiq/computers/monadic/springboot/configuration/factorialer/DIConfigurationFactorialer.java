package test.com.solutionsiq.computers.monadic.springboot.configuration.factorialer;


import com.solutionsiq.computers.monadic.springboot.configuration.DIConfiguration;
import com.solutionsiq.computers.monadic.springboot.services.FactorialerService;
import com.solutionsiq.computers.monadic.springboot.services.definition.ComputerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DIConfiguration.class)
public class DIConfigurationFactorialer {

    @Bean
    public ComputerService compute(){
        return new FactorialerService();
    }

}
