package test.com.solutionsiq.computers.monadic.springboot.configuration.cuber;

import com.solutionsiq.computers.monadic.springboot.configuration.DIConfiguration;
import com.solutionsiq.computers.monadic.springboot.services.CuberService;
import com.solutionsiq.computers.monadic.springboot.services.definition.ComputerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DIConfiguration.class)
public class DIConfigurationCuber {

    @Bean
    public ComputerService compute(){
        return new CuberService();
    }

}
