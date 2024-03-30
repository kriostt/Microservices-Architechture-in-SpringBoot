package CPRO2501.BookService.repository;

import CPRO2501.BookService.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

// manages Book entities
public interface IBookRepository extends JpaRepository<Book, Long> {
    // provides CRUD operations for Book entity
}
