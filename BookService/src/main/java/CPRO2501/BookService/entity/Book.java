package CPRO2501.BookService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// represents a book
@Entity // annotated as Entity for database persistence
@Data // automatically generates getters and setters
@NoArgsConstructor // automatically generates no argument constructor
@AllArgsConstructor // automatically generates all arguments constructor
public class Book {
    // primary key for Book entity
    @Id
    // define generation strategy for primary key (auto increment the field)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private String author;

    private String genre;

    private String isbn;

    private double price;
}
