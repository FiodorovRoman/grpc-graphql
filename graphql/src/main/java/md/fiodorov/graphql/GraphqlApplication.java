package md.fiodorov.graphql;

import md.fiodorov.graphql.model.ToDo;
import md.fiodorov.graphql.repository.ToDoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.awt.print.Book;

@SpringBootApplication
public class GraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ToDoRepository repository) {
        return (args) -> {
            // Save a couple of books
            repository.save(new ToDo(null, "Drink water", "Drink water at least 1,5 liters per day"));
            repository.save(new ToDo(null, "Hiking", "Walk through forest for 1.5-2h per day"));
        };
    }
}
