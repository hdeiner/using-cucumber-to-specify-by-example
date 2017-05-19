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
