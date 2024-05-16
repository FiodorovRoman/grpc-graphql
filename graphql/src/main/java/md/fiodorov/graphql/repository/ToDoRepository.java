package md.fiodorov.graphql.repository;

import md.fiodorov.graphql.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
}
