package md.fiodorov.graphql.resolver;

import graphql.schema.DataFetchingEnvironment;
import md.fiodorov.graphql.model.ToDo;
import md.fiodorov.graphql.repository.ToDoRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ToDoQueryResolver {
    private final ToDoRepository todoRepository;

    public ToDoQueryResolver(ToDoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }


    @QueryMapping
    public List<ToDo> getToDos() {
        return todoRepository.findAll();
    }

    @MutationMapping
    public ToDo createToDo(@Argument String title, @Argument String description) {
        ToDo toDo = new ToDo();
        toDo.setTitle(title);
        toDo.setDescription(description);
        return todoRepository.save(toDo);
    }
}