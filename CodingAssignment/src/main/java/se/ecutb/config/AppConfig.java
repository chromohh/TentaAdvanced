package se.ecutb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import se.ecutb.data.IdSequencerimp;
import se.ecutb.data.IdSequencers;
import se.ecutb.data.PersonRepository;
import se.ecutb.data.PersonRepositoryImp;
import se.ecutb.model.Person;
import se.ecutb.service.CreateTodoService;
import se.ecutb.service.CreateTodoServiceimp;
import se.ecutb.service.TodoDtoConversionImp;
import se.ecutb.service.TodoDtoConversionService;

import java.util.List;

@Configuration
@ComponentScan("se.ecutb")
public class AppConfig {

    @Bean
    public IdSequencers idSequencers() {
        return new IdSequencerimp();
    }


}
